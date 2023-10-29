package com.samrish.driver.network

import android.content.Context
import android.util.Log
import com.github.kittinunf.fuel.core.extensions.authentication
import com.github.kittinunf.fuel.core.response
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.moshi.moshiDeserializerOf
import com.samrish.driver.R
import com.samrish.driver.models.History
import com.samrish.driver.ui.viewmodels.TripsAssigned
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import dagger.hilt.android.qualifiers.ApplicationContext
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

class TripNetRepository @Inject constructor(@ApplicationContext private val context: Context)  {
    fun fetchActiveTrips(): List<TripsAssigned>? {
        val assignedTripType =
            Types.newParameterizedType(MutableList::class.java, TripsAssigned::class.java)
        val adapter: JsonAdapter<MutableList<TripsAssigned>> =
            Moshi.Builder().build().adapter(assignedTripType)

        val tripAssignmentUrl = context.resources.getString(R.string.url_trips_list)

        return try {
            getAccessToken(context)?.let {
                val (_, _, result) = tripAssignmentUrl.httpGet()
                    .authentication().bearer(it)
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

    fun fetchPastTrips(currentPage:Int): List<History>? {
        val url = context.resources.getString(R.string.url_trip_history)  + "assignmentHistory?pageno="+ currentPage
        Log.d("TAG", "fetchHistoryDetail:$url ")

        val  historyList = Types.newParameterizedType(MutableList::class.java, History::class.java)
        val history: JsonAdapter<MutableList<History>> = Moshi.Builder().build().adapter( historyList)

        getAccessToken(context)?.let {
            val (request1, response1, result) = url.httpGet()
                .authentication().bearer(it)
                .response(moshiDeserializerOf(history))


            result.fold(
                {
//                        historyDetail -> channel4.send(historyDetail)
                },
                { error ->
                    Log.e(
                        "Fuel",
                        "Error $error"
                    )
                }

            )
            return result.get()
        }
        return null;
    }

}