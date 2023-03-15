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

class TripListRequest(
    url: String,
    headers: MutableMap<String, String>,
    listener: Response.Listener<List<Trip>>,
    errorListener: Response.ErrorListener
) : GenericRequest<List<Trip>>(Method.GET, url, headers, listener, errorListener) {
    override fun transformResponse(response: NetworkResponse?): List<Trip> {

        val responseBody = String(
            response?.data ?: ByteArray(0),
            Charset.forName(HttpHeaderParser.parseCharset(response?.headers))
        )

        val trips = JSONArray(responseBody)
        val mutableList = mutableListOf<Trip>()
        for (i in 0 until trips.length()) {
            val t: JSONObject = trips.getJSONObject(i)
            val vehicle = t["vehicleAssignment"] as JSONObject
            val driver = t["driverAssignment"] as JSONObject

            val assignedVehicle = AssignedVehicle(
                vehicle["vehicleNumber"] as String,
                "MiniTruck"
            )
            val assignedDriver = AssignedDriver(
                driver["driverId"] as Int,
                driver["driverName"] as String
            )

            val schs = t["schedules"] as JSONArray
            val schedules = mutableListOf<Schedule>()

            for (i in 0 until schs.length()) {
                val s = schs.get(i) as JSONObject
                schedules.add(
                    Schedule(
                        s["placeCode"] as String,
                        s["order"] as Int,
                        s["scheduledArrivalTime"] as String,
                        s["scheduledDepartureTime"] as String
                    )
                )
            }
            mutableList.add(Trip(
                t.get("tripName") as String,
                t.get("tripCode") as String,
                t.get("status") as String,
                assignedDriver,
                assignedVehicle,
                schedules
            ))

        }
        return mutableList
    }
}