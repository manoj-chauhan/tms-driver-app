package com.samrish.driver.services.requests

import com.android.volley.NetworkResponse
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.samrish.driver.models.Profile
import org.json.JSONObject
import java.nio.charset.Charset

class ProfileRequest(
    url: String,
    headers: MutableMap<String, String>,
    listener: Response.Listener<Profile>,
    errorListener: Response.ErrorListener
) : GenericRequest<Profile>(Method.GET, url, headers, listener, errorListener) {
    override fun transformResponse(response: NetworkResponse?): Profile {
        val responseBody = String(
            response?.data ?: ByteArray(0),
            Charset.forName(HttpHeaderParser.parseCharset(response?.headers))
        )
        val t = JSONObject(responseBody)
        return Profile(
            driverId = t.getInt("id"),
            driverName = t.getString("name"),
            primaryContact = t.getString("primaryContact"),
            secondaryContact = t.getString("secondaryContact"),
            createdById = t.getInt("createdBy")
        )
    }
}