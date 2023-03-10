package com.samrish.driver.services

import com.android.volley.NetworkResponse
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.samrish.driver.models.Trip
import org.json.JSONObject
import java.nio.charset.Charset

class TripDetailRequest(url: String,
                        headers: MutableMap<String, String>,
                        listener: Response.Listener<Trip>,
                        errorListener: Response.ErrorListener
) : GenericRequest<Trip>(Method.GET, url, headers, listener, errorListener) {
    override fun transformResponse(response: NetworkResponse?): Trip {
        val responseBody = String(
            response?.data ?: ByteArray(0),
            Charset.forName(HttpHeaderParser.parseCharset(response?.headers)))

        var t = JSONObject(responseBody)
        return Trip(t.get("tripName") as String, t.get("tripCode") as String, t.get("status") as String)
    }
}