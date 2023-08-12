package com.samrish.driver.services.requests

import com.android.volley.NetworkResponse
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.samrish.driver.models.Company
import com.samrish.driver.models.Trip
import com.samrish.driver.models.UserProfile
import org.json.JSONArray
import org.json.JSONObject
import java.nio.charset.Charset

class UserProfileRequest(
    url: String,
    headers: MutableMap<String, String>,
    listener: Response.Listener<UserProfile>,
    errorListener: Response.ErrorListener
) : GenericRequest<UserProfile>(Method.GET, url, headers, listener, errorListener) {
    override fun transformResponse(response: NetworkResponse?): UserProfile {
        val responseBody = String(
            response?.data ?: ByteArray(0),
            Charset.forName(HttpHeaderParser.parseCharset(response?.headers))
        )
        val t = JSONObject(responseBody)
        return UserProfile(
            id = t.getInt("id"),
            name = t.getString("name"),
            authProvider = t.optString("primaryContact",""),
            userName = t.optString("secondaryContact", "")
        )

    }

}