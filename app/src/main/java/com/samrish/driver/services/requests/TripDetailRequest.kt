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

class TripDetailRequest(
    url: String,
    headers: MutableMap<String, String>,
    listener: Response.Listener<Trip>,
    errorListener: Response.ErrorListener
) : GenericRequest<Trip>(Method.GET, url, headers, listener, errorListener) {
    override fun transformResponse(response: NetworkResponse?): Trip {
        val responseBody = String(
            response?.data ?: ByteArray(0),
            Charset.forName(HttpHeaderParser.parseCharset(response?.headers))
        )

        val t = JSONObject(responseBody)

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
        return Trip(
            t.get("tripName") as String,
            t.get("tripCode") as String,
            t.get("status") as String,
            assignedDriver,
            assignedVehicle
        )
    }
}