package com.samrish.driver.network.requests

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
            id = t.getInt("id"),
            name = t.getString("name"),
            username = t.getString("username")
        )
    }
}