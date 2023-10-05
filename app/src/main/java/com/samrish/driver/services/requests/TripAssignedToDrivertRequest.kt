package com.samrish.driver.services.requests

import android.util.Log
import com.android.volley.NetworkResponse
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.samrish.driver.models.AssignedDriver
import com.samrish.driver.models.AssignedVehicle
import com.samrish.driver.models.Trip
import com.samrish.driver.models.TripsAssigned
import org.json.JSONArray
import org.json.JSONObject
import java.nio.charset.Charset

class TripsAssignedToDriverRequest(
    url: String,
    headers: MutableMap<String, String>,
    listener: Response.Listener<List<TripsAssigned>>,
    errorListener: Response.ErrorListener
) : GenericRequest<List<TripsAssigned>>(Method.GET, url, headers, listener, errorListener) {
    override fun transformResponse(response: NetworkResponse?): List<TripsAssigned> {

        val responseBody = String(
            response?.data ?: ByteArray(0),
            Charset.forName(HttpHeaderParser.parseCharset(response?.headers))
        )

        val trips = JSONArray(responseBody)
        val mutableList = mutableListOf<TripsAssigned>()
        for (i in 0 until trips.length()) {
            val t: JSONObject = trips.getJSONObject(i)
            Log.d("TAG", "transformResponse: ")

            mutableList.add(
                TripsAssigned(
                    t.get ("tripCode") as String,
                    t.get ("tripName") as String,
                    t.get ("status") as String,
                    t.get ("label") as String,
                    t.get ("companyName") as String,
                    t.get ("companyCode") as String,
                    t.get ("operatorCompanyName") as String,
                    t.get("operatorCompanyCode") as String,
                    t.get ("tripDate") as String
                )
            )

        }
        return mutableList
    }
}