package com.samrish.driver.services.requests

import android.util.Log
import com.android.volley.NetworkResponse
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.samrish.driver.models.TripActions
import org.json.JSONObject
import java.nio.charset.Charset
import kotlin.math.log

class TripActionsStatusRequest (
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

        var currentLocationName: String? = null

        if (t.has("currentLocationName")) {
             currentLocationName = t["currentLocationName"] as? String
        }
        val nextLocationName: String? = t["nextLocationName"] as? String


        Log.d("TAG", "transformResponse: $nextLocationName")
        if (currentLocationName != null) {

            return TripActions(actions, null, null, null, null, null, currentLocationName)
        }


         else if(nextLocationName!= null) {
            println("HI")
            return TripActions(
                actions,
                nextLocationName,
                t.get("estimatedTime") as Int?,
                t.get("estimatedDistance") as Double?,
                t.get("travelledDistance") as Double?,
                t.get("travelTime") as Int?,
                null
            )
        }

        else
        {
            return TripActions(actions, null, null, null, null, null, null)
        }
    }
}

//    var nextLocationName : String,
//    var estimatedTime: Double,
//    var estimatedDistance: Double,
//    var travelledDistance: Double,
//    var travelTime: Double