package com.samrish.driver.services.requests

import com.android.volley.NetworkResponse
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.samrish.driver.models.generatedCode
import org.json.JSONObject
import java.nio.charset.Charset

class CodeGeneratorRequest(
                     url: String,
                     headers: MutableMap<String, String>,
                     listener: Response.Listener<generatedCode>,
                     errorListener: Response.ErrorListener
) : GenericRequest<generatedCode>(Method.GET, url, headers, listener, errorListener) {

    override fun transformResponse(response: NetworkResponse?): generatedCode {
        val responseBody =  String(
            response?.data ?: ByteArray(0),
            Charset.forName(HttpHeaderParser.parseCharset(response?.headers)))
        val t = JSONObject(responseBody)
        return generatedCode(
            assignmentCode = t.get("assignmentCode")as String
        )
    }
}
