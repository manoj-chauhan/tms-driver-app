package com.samrish.driver.services

import com.android.volley.NetworkResponse
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.samrish.driver.models.Trip
import org.json.JSONArray
import org.json.JSONObject
import java.nio.charset.Charset

class TripListRequest(url: String,
                      headers: MutableMap<String, String>,
                      listener: Response.Listener<List<Trip>>,
                      errorListener: Response.ErrorListener
) : GenericRequest<List<Trip>>(url, headers, listener, errorListener) {
    override fun transformResponse(response: NetworkResponse?): List<Trip> {
        val responseBody = String(
            response?.data ?: ByteArray(0),
            Charset.forName(HttpHeaderParser.parseCharset(response?.headers)))

        var trips = JSONArray(responseBody)
        val mutableList = mutableListOf<Trip>()
        for (i in 0 until trips.length()){
            var t: JSONObject = trips.getJSONObject(i)
            var tr: Trip = Trip(t.get("tripName") as String?, t.get("tripCode") as String?)
            mutableList.add(tr)
        }
        return mutableList
    }
}