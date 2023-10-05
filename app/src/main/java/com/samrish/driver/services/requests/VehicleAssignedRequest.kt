package com.samrish.driver.services.requests

import android.util.Log
import com.android.volley.NetworkResponse
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.samrish.driver.models.VehicleAssignment
import org.json.JSONObject
import java.nio.charset.Charset

class VehicleAssignedRequest (
    url: String,
    headers: MutableMap<String, String>,
    listener: Response.Listener<VehicleAssignment>,
    errorListener: Response.ErrorListener
) : GenericRequest<VehicleAssignment>(Method.GET, url, headers, listener, errorListener) {
    override fun transformResponse(response: NetworkResponse?): VehicleAssignment{

        val responseBody = String(
            response?.data ?: ByteArray(0),
            Charset.forName(HttpHeaderParser.parseCharset(response?.headers))
        )

        val assignmentJSON = JSONObject(responseBody)
        Log.d("TAG", "transformResponse: $assignmentJSON")
        
        return VehicleAssignment(
            vehicleId = assignmentJSON.getInt("vehicleId"),
            vehicleNumber = assignmentJSON.getString("vehicleNumber"),
            companyId = assignmentJSON.getInt("companyId"),
            companyName = assignmentJSON.getString("companyName"),
            assignerId = assignmentJSON.getInt("assignerId"),
            assignerName = assignmentJSON.getString("assignerName"),
            assignedAt = assignmentJSON.getString("assignedAt"),
            vehicleSize = assignmentJSON.getInt("vehicleSize"),
            model = assignmentJSON.getString("model"),
            brand = assignmentJSON.getString("brand"),
            fuelType =   assignmentJSON.getString("fuelType")
        )

    }
}