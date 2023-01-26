package com.samrish.driver.services

import com.android.volley.NetworkResponse
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import org.json.JSONObject
import java.nio.charset.Charset

class TripCancelRequest(tripCode: String,
                        url: String,
                        headers: MutableMap<String, String>,
                        listener: Response.Listener<String>,
                        errorListener: Response.ErrorListener
) : GenericRequest<String>(Method.POST, url, headers, listener, errorListener) {

    private var tripCode: String? = null

    init {
        this.tripCode = tripCode
    }

    override fun transformResponse(response: NetworkResponse?): String {
        return String(
            response?.data ?: ByteArray(0),
            Charset.forName(HttpHeaderParser.parseCharset(response?.headers)))
    }

    override fun getBodyContentType(): String {
        return "application/json; charset=$paramsEncoding"
    }

    override fun getBody(): ByteArray? {
        var body: JSONObject = JSONObject()
        body.put("tripCode", tripCode)
        return body.toString().encodeToByteArray()
    }
}