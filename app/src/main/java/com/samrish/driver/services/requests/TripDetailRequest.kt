package com.samrish.driver.services.requests

import com.android.volley.NetworkResponse
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.samrish.driver.models.AssignedDriver
import com.samrish.driver.models.AssignedVehicle
import com.samrish.driver.models.CurrentAssignmentDetail
import org.json.JSONObject
import java.nio.charset.Charset

class TripDetailRequest(
    url: String,
    headers: MutableMap<String, String>,
    listener: Response.Listener<CurrentAssignmentDetail>,
    errorListener: Response.ErrorListener
) : GenericRequest<CurrentAssignmentDetail>(Method.GET, url, headers, listener, errorListener) {
    override fun transformResponse(response: NetworkResponse?): CurrentAssignmentDetail {
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

//        var actions = mutableListOf<String>();
//        val actionList = t.getJSONArray("availableActions");
//        for (i in 0 until actionList.length()) {
//            actions.add(actionList.get(i) as String)
//        }

        return CurrentAssignmentDetail(
            t.get("tripName") as String,
            t.get("tripCode") as String,
            t.get("status") as String,
//            actions,
            t.get("tripDate") as String,
            t.get("operatorName") as String,
            t.get("tripId" )as Int
        )
    }
}