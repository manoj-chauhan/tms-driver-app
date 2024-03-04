package com.drishto.driver.network

import android.content.Context
import android.util.Log
import com.drishto.driver.R
import com.drishto.driver.errormgmt.ErrManager
import com.drishto.driver.models.ChildrenList
import com.drishto.driver.models.scheduleList
import com.github.kittinunf.fuel.core.extensions.authentication
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.moshi.moshiDeserializerOf
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import dagger.hilt.android.qualifiers.ApplicationContext
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

class DriverNetRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val errorManager: ErrManager
) {
    fun fetchPlanChildren(planId:Int, operatorId: Int): List<ChildrenList>? {
        val assignedTripType =
            Types.newParameterizedType(MutableList::class.java, ChildrenList::class.java)
        val adapter: JsonAdapter<MutableList<ChildrenList>> =
            Moshi.Builder().build().adapter(assignedTripType)

        val tripAssignmentUrl = context.resources.getString(R.string.url_student_list)+ planId +"/passengers?page_no=0"

        return try {
            getAccessToken(context)?.let {
                val (_, _, result) = tripAssignmentUrl.httpGet()
                    .authentication().bearer(it)
                    .header("Company-Id", operatorId)
                    .responseObject(moshiDeserializerOf(adapter))
                Log.d("TAG", "tripList:$result ")
                result.fold(
                    {

                    },
                    {
                        EventBus.getDefault().post("AUTH_FAILED")
                    }
                )

                result.get()
            }
        } catch (e: Exception) {
            null
        }
    }

    fun fetchSchedules(planId: Int, operatorId: Int): scheduleList{
            val tripDetailUrl = context.resources.getString(R.string.url_schedule_plan) + planId

            return try {
                getAccessToken(context)?.let {
                    val (request1, response1, result1) = tripDetailUrl.httpGet()
                        .authentication().bearer(it)
                        .header("Company-Id", operatorId)
                        .responseObject(moshiDeserializerOf(scheduleList::class.java))

                    result1.fold(
                        { schedule ->
                            schedule
                        },
                        { error ->
                            Log.e(
                                "Fuel",
                                "Error $error"
                            )
                            throw Exception("Error fetching trip schedule" )
                        }
                    )
                }
                    ?: throw Exception("Access token is null") // Throw an exception if access token is null
            } catch (e: Exception) {
                Log.e(
                    "Fuel",
                    "Exception $e"
                )
                throw Exception("Exception fetching trip details")
            }
    }

}