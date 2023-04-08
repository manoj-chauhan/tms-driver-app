package com.samrish.driver.services.requests

import com.android.volley.NetworkResponse
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.samrish.driver.models.OldAssignment
import org.json.JSONArray
import org.json.JSONObject
import java.nio.charset.Charset

class OldAssignmentListRequest(
    url: String,
    headers: MutableMap<String, String>,
    listener: Response.Listener<List<OldAssignment>>,
    errorListener: Response.ErrorListener
) : GenericRequest<List<OldAssignment>>(Method.GET, url, headers, listener, errorListener) {
    override fun transformResponse(response: NetworkResponse?): List<OldAssignment> {

        val responseBody = String(
            response?.data ?: ByteArray(0),
            Charset.forName(HttpHeaderParser.parseCharset(response?.headers))
        )

        val trips = JSONArray(responseBody)
        val mutableList = mutableListOf<OldAssignment>()
        for (i in 0 until trips.length()) {
            val t: JSONObject = trips.getJSONObject(i)

            mutableList.add(OldAssignment(
                assignedAt = t.get("assignedAt") as String,
//                assignedTill = t.get("assignedTill") as String?,
                createdBy = t.get("createdBy") as Int,
                tripCode = t.get("tripCode") as String,
                tripName = t.get("tripName") as String,
                vendorCode = t.get("vendorCode") as String,
                vendorName = t.get("vendorName") as String
            ))

        }
        return mutableList
    }
}