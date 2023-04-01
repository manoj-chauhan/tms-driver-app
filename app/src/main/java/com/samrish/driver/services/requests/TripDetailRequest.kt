package com.samrish.driver.services.requests

import com.android.volley.NetworkResponse
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.samrish.driver.models.AssignedDriver
import com.samrish.driver.models.AssignedVehicle
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


        var vehicle: JSONObject? = null;
        if (t.has("vehicleAssignment")) {
            vehicle = t.getJSONObject("vehicleAssignment");
        }
        var driver: JSONObject? = null;
        if (t.has("driverAssignment")) {
            driver = t.getJSONObject("driverAssignment");
        }

        var assignedVehicle: AssignedVehicle? = null;
        var assignedDriver: AssignedDriver? = null;

        vehicle?.let {
            assignedVehicle = AssignedVehicle(
                (it as JSONObject)["vehicleNumber"] as String,
                "MiniTruck"
            )
        }
        driver?.let {
            assignedDriver = AssignedDriver(
                (it as JSONObject)["driverId"] as Int,
                (it as JSONObject)["driverName"] as String
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