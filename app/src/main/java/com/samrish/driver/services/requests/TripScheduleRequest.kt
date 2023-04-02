package com.samrish.driver.services.requests

import com.android.volley.NetworkResponse
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.samrish.driver.models.AssignedDriver
import com.samrish.driver.models.AssignedVehicle
import com.samrish.driver.models.Schedule
import com.samrish.driver.models.Trip
import org.json.JSONArray
import org.json.JSONObject
import java.nio.charset.Charset

class TripScheduleRequest(
    url: String,
    headers: MutableMap<String, String>,
    listener: Response.Listener<List<Schedule>>,
    errorListener: Response.ErrorListener
) : GenericRequest<List<Schedule>>(Method.GET, url, headers, listener, errorListener) {
    override fun transformResponse(response: NetworkResponse?): List<Schedule> {

        val responseBody = String(
            response?.data ?: ByteArray(0),
            Charset.forName(HttpHeaderParser.parseCharset(response?.headers))
        )

        val schedules   = JSONArray(responseBody)
        val mutableList = mutableListOf<Schedule>()
        for (i in 0 until schedules.length()) {
            val t: JSONObject = schedules.getJSONObject(i)

            val placeCode = t.get("placeCode") as String
            val placeName = t.get("placeName") as String
            val order = t.get("order") as Int
            val sta = t.get("scheduledArrivalTime") as String
            val std = t.get("scheduledDepartureTime") as String

            mutableList.add(Schedule(
                placeCode,
                placeName,
                order,
                sta,
                std
            ))

        }
        return mutableList
    }
}