package com.samrish.driver.services.requests

import com.android.volley.NetworkResponse
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.samrish.driver.models.Locations
import com.samrish.driver.models.Schedule
import org.json.JSONArray
import org.json.JSONObject
import java.nio.charset.Charset

class TripScheduleRequest(
    url: String,
    headers: MutableMap<String, String>,
    listener: Response.Listener<Schedule>,
    errorListener: Response.ErrorListener
) : GenericRequest<Schedule>(Method.GET, url, headers, listener, errorListener) {
    override fun transformResponse(response: NetworkResponse?): Schedule {

        val responseBody = String(
            response?.data ?: ByteArray(0),
            Charset.forName(HttpHeaderParser.parseCharset(response?.headers))
        )

             val t   = JSONObject(responseBody)
            var locationsArray= mutableListOf<Locations>()

            var locations: JSONArray? = null;

            if (t.has("locations")) {
                locations = t.getJSONArray("locations");
            }

            for (i in 0 until locations!!.length()) {
                val t: JSONObject = locations.getJSONObject(i)

                locationsArray.add(Locations (
                    t.get("placeCode") as String,
                    t.get("placeName") as String,
                    t.get("estDistance") as Double,
                    t.get("actualDistance") as Double,
                    t.get("scheduledArrivalTime") as String,
                    t.get("scheduledDepartureTime") as String
                    )
                )
            }
            val totalDistance = t.get("totalDistance") as Double
            val totalEstimatedDistance = t.get("totalEstimatedDistance") as Double
            val totalTime = t.get("totalTime") as Int
            val totalEstimatedTime = t.get("totalEstimatedTime") as Int

            return Schedule(
                totalDistance,
                totalEstimatedDistance,
                totalTime,
                totalEstimatedTime,
                locationsArray
            )

    }
}