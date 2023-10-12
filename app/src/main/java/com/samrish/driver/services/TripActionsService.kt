package com.samrish.driver.services

import android.content.Context
import android.util.Log
import com.android.volley.toolbox.Volley
import com.samrish.driver.R
import com.samrish.driver.models.TripActions
import com.samrish.driver.services.requests.TripActionsStatusRequest
import com.samrish.driver.services.requests.TripDetailRequest

    fun getTripActions(context: Context, operatorId:Int ,tripId: Int, onTripActionsFetched: (trip: TripActions) -> Unit) {
        val queue = Volley.newRequestQueue(context)
        val url = context.resources.getString(R.string.url_trip_actions) + tripId +"/activeStatus"

        val hdrs: MutableMap<String, String> = mutableMapOf<String, String>()
        context.applicationContext?.let {
            getAccessToken(it)?.let {
                hdrs["Authorization"] = "Bearer $it"
                hdrs["Company-Id"] = operatorId.toString()
            }

            val stringRequest = TripActionsStatusRequest(url, hdrs, { response ->
                Log.i("TripActions", "Trip Actions are : ${response.nextLocationName}")
                onTripActionsFetched(response)
            }, { error -> handleError(context, error) })
            queue.add(stringRequest)
        }
    }