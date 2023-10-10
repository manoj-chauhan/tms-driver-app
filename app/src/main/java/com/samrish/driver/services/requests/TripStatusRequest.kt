package com.samrish.driver.services.requests

import com.android.volley.NetworkResponse
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.samrish.driver.models.TripActions
import org.json.JSONObject
import java.nio.charset.Charset
class TripActionsStatusRequest(
    url: String,
    headers: MutableMap<String, String>,
    listener: Response.Listener<TripActions>,
    errorListener: Response.ErrorListener
) : GenericRequest<TripActions>(Method.GET, url, headers, listener, errorListener) {
    override fun transformResponse(response: NetworkResponse?): TripActions {
        val responseBody = String(
            response?.data ?: ByteArray(0),
            Charset.forName(HttpHeaderParser.parseCharset(response?.headers))
        )

        val t = JSONObject(responseBody)

        var actions = mutableListOf<String>();
        val actionList = t.getJSONArray("actions");
        for (i in 0 until actionList.length()) {
            actions.add(actionList.get(i) as String)
        }

        return TripActions(
            actions,
            t.get("nextLocationName") as String,
            t.get("estimatedTime") as Double,
            t.get("estimatedDistance") as Double,
            t.get("travelledDistance") as Double,
            t.get("travelTime") as Double
        )
    }
}

//    var nextLocationName : String,
//    var estimatedTime: Double,
//    var estimatedDistance: Double,
//    var travelledDistance: Double,
//    var travelTime: Double