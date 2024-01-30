package driver.network

import android.content.Context
import android.util.Log
import com.drishto.driver.R
import com.drishto.driver.errormgmt.ErrManager
import com.drishto.driver.network.getAccessToken
import com.github.kittinunf.fuel.core.extensions.authentication
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.moshi.moshiDeserializerOf
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import dagger.hilt.android.qualifiers.ApplicationContext
import driver.models.ParentTrip
import driver.models.ProcessedPoints
import driver.models.point
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

class ParentTripNetRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val errorManager: ErrManager
) {
    fun fetchActiveTrips(): List<ParentTrip>? {
        val assignedTripType =
            Types.newParameterizedType(List::class.java, ParentTrip::class.java)
        val adapter: JsonAdapter<List<ParentTrip>> =
            Moshi.Builder().build().adapter(assignedTripType)

        val tripAssignmentUrl = context.resources.getString(R.string.url_parent_trip)

        return try {
            getAccessToken(context)?.let {
                Log.d("TAG", "fetchActiveTrips: $it")
                val (_, _, result) = tripAssignmentUrl.httpGet()
                    .authentication().bearer(it)
                    .responseObject(moshiDeserializerOf(adapter))
                Log.d("TAG", "fetchActiveTrips: $result")
                result.fold(
                    {
                    },
                    {error->
                        EventBus.getDefault().post("AUTH_FAILED")
                        if (error.response.statusCode == 401) {
                            errorManager.getErrorDescription(context)
                        }

                        val errorResponse = error.response.data.toString(Charsets.UTF_8)
                        CoroutineScope(Dispatchers.IO).launch(Dispatchers.Main) {
                            errorManager.handleErrorResponse(context, errorResponse)
                        }
                    }
                )

                result.get()
            }
        } catch (e: Exception) {
            null
        }
    }
    fun fetchTripRouteCoor(operatorId: Int, tripCode: String): List<point> {
        val assignedTripType = Types.newParameterizedType(List::class.java, point::class.java)
        val adapter: JsonAdapter<List<point>> = Moshi.Builder().build().adapter(assignedTripType)

        val tripAssignmentUrl =
            context.resources.getString(R.string.url_trip_lat_lon) + tripCode

        return try {
            getAccessToken(context)?.let {
                Log.d("TAG", "fetchActiveTrips: $it")
                val (_, _, result) = tripAssignmentUrl.httpGet()
                    .authentication().bearer(it)
                    .header("Company-Id", operatorId)
                    .responseObject(moshiDeserializerOf(adapter))
                Log.d("TAG", "getTripRoute: $result")
                result.fold(
                    {
                        it ?: emptyList() // Return empty list if the result is null
                    },
                    {error ->
                        EventBus.getDefault().post("AUTH_FAILED")
                        if (error.response.statusCode == 401) {
                            errorManager.getErrorDescription(context)
                        }

                        val errorResponse = error.response.data.toString(Charsets.UTF_8)
//                        Log.d("Error", "fetchAssignmentDetail: $errorResponse")
                        CoroutineScope(Dispatchers.IO).launch(Dispatchers.Main) {
                            errorManager.handleErrorResponse(context, errorResponse)
                        }
                        emptyList()
                    }
                )
            } ?: emptyList() // Return empty list if access token is null
        } catch (e: Exception) {
            emptyList() // Return empty list in case of any exception
        }
    }

    fun fetchTripProcessed(operatorId: Int, tripCode: String): List<ProcessedPoints> {
        val tripCoor = Types.newParameterizedType(List::class.java, ProcessedPoints::class.java)
        val adapter: JsonAdapter<List<ProcessedPoints>> = Moshi.Builder().build().adapter(tripCoor)

        val tripProcessedCoordinates =
            context.resources.getString(R.string.url_trip_processed) + tripCode

        return try {
            getAccessToken(context)?.let {
                Log.d("TAG", "fetchActiveTrips: $it")
                val (_, _, result) = tripProcessedCoordinates.httpGet()
                    .authentication().bearer(it)
                    .header("Company-Id", operatorId)
                    .responseObject(moshiDeserializerOf(adapter))
                Log.d("TAG", "getTripRoute: $result")
                result.fold(
                    {
                        it ?: emptyList()
                    },
                    {error->
                        EventBus.getDefault().post("AUTH_FAILED")
                        if (error.response.statusCode == 401) {
                            errorManager.getErrorDescription(context)
                        }

                        val errorResponse = error.response.data.toString(Charsets.UTF_8)
//                        Log.d("Error", "fetchAssignmentDetail: $errorResponse")
                        CoroutineScope(Dispatchers.IO).launch(Dispatchers.Main) {
                            errorManager.handleErrorResponse(context, errorResponse)
                        }
                        emptyList()
                    }
                )
            } ?: emptyList() // Return empty list if access token is null
        } catch (e: Exception) {
            emptyList() // Return empty list in case of any exception
        }
    }
}