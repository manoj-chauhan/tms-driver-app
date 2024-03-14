package com.drishto.driver.network

import android.content.Context
import android.util.Log
import com.drishto.driver.R
import com.drishto.driver.errormgmt.ErrManager
import com.drishto.driver.models.DriverPlans
import com.github.kittinunf.fuel.core.extensions.authentication
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.moshi.moshiDeserializerOf
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import dagger.hilt.android.qualifiers.ApplicationContext
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

@JsonClass(generateAdapter = true)
data class AssignmentCodeResponse(
    var assignmentCode:String
)


class VehicleNetRepository @Inject constructor(@ApplicationContext private val context: Context,  private val errorManager: ErrManager) {

    fun generateAssignmentCode(): String? {
        val vehicleAssignmentUrl = context.resources.getString(R.string.url_driver_code)
        getAccessToken(context)?.let {
            val (request1, response1, result) = vehicleAssignmentUrl.httpGet()
                .authentication().bearer(it)
                .responseObject(moshiDeserializerOf(AssignmentCodeResponse::class.java))
            return result.get().assignmentCode
        }

        return null
    }

    fun getDriverPlans():List<DriverPlans>?{
        val assignedTripType =
            Types.newParameterizedType(List::class.java, DriverPlans::class.java)
        val adapter: JsonAdapter<List<DriverPlans>> =
            Moshi.Builder().build().adapter(assignedTripType)

        val tripAssignmentUrl = context.resources.getString(R.string.url_driver_trip_plan)

        return try {
            getAccessToken(context)?.let {
                val (_, _, result) = tripAssignmentUrl.httpGet()
                    .authentication().bearer(it)
                    .responseObject(moshiDeserializerOf(adapter))
                result.fold(
                    {
                    },
                    {error->
                        Log.d("Error", "fetchActiveTrips: $error")
                        EventBus.getDefault().post("AUTH_FAILED")
                        if (error.response.statusCode == 401 ) {
                            errorManager.getErrorDescription(context)
                        }

                        val errorResponse = error.response.data.toString(Charsets.UTF_8)

                        if (error.response.statusCode == 403 ) {
                            errorManager.getErrorDescription403(context, errorResponse)
                        }

                        if (error.response.statusCode == 404 ) {
                            errorManager.getErrorDescription404(context, "No url found")
                        }

                        if(error.response.statusCode == 500){
                            errorManager.getErrorDescription500(context, "Something Went Wrong")
                        }
                    }
                )

                result.get()
            }
        } catch (e: Exception) {
            null
        }
    }
}