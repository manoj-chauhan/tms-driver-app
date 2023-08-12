package com.samrish.driver.services.requests

import com.android.volley.NetworkResponse
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.samrish.driver.models.Company
import org.json.JSONObject
import java.nio.charset.Charset

class CompanyDetailRequest(
    url: String,
    headers: MutableMap<String, String>,
    listener: Response.Listener<Company>,
    errorListener: Response.ErrorListener
) : GenericRequest<Company>(Method.GET, url, headers, listener, errorListener) {
    override fun transformResponse(response: NetworkResponse?): Company {
        val responseBody = String(
            response?.data ?: ByteArray(0),
            Charset.forName(HttpHeaderParser.parseCharset(response?.headers))
        )
        val t = JSONObject(responseBody)
        return Company(
            id = t.getInt("id"),
            code = t.optString("code"),
            name = t.getString("name"),
            address = t.optString("address")
        )
    }
}