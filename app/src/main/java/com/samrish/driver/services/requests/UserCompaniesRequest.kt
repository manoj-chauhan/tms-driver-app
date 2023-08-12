package com.samrish.driver.services.requests

import com.android.volley.NetworkResponse
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.samrish.driver.models.AssignedDriver
import com.samrish.driver.models.AssignedVehicle
import com.samrish.driver.models.Company
import org.json.JSONArray
import org.json.JSONObject
import java.nio.charset.Charset

class UserCompaniesRequest(
    url: String,
    headers: MutableMap<String, String>,
    listener: Response.Listener<List<Company>>,
    errorListener: Response.ErrorListener
) : GenericRequest<List<Company>>(Method.GET, url, headers, listener, errorListener) {
    override fun transformResponse(response: NetworkResponse?): List<Company> {

        val responseBody = String(
            response?.data ?: ByteArray(0),
            Charset.forName(HttpHeaderParser.parseCharset(response?.headers))
        )

        val trips = JSONArray(responseBody)
        val mutableList = mutableListOf<Company>()
        for (i in 0 until trips.length()) {
            val t: JSONObject = trips.getJSONObject(i)
            mutableList.add(
                Company(
                    t.get("name") as String,
                    t.get("code") as String,
                    t.get("address") as String
                )
            )
        }
        return mutableList
    }
}