package com.samrish.driver

import com.android.volley.NetworkResponse
import com.android.volley.ParseError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset

abstract class GenericRequest<T>(
    url: String,
    private val headers: MutableMap<String, String>,
    private val listener: Response.Listener<T>,
    errorListener: Response.ErrorListener
) : Request<T>(Method.GET, url, errorListener) {

    override fun getHeaders(): MutableMap<String, String>{
        return headers;
    }

    override fun deliverResponse(response: T) {
        listener.onResponse(response)
    }

    abstract fun transformResponse(response: NetworkResponse?):T

    override fun parseNetworkResponse(response: NetworkResponse?): Response<T> {
        return try {
            Response.success(transformResponse(response), HttpHeaderParser.parseCacheHeaders(response))
        } catch (e: UnsupportedEncodingException) {
            Response.error(ParseError(e))
        }
    }
}