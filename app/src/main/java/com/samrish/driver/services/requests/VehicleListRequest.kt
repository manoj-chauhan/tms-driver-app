package com.samrish.driver.services.requests

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.android.volley.NetworkResponse
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.samrish.driver.models.VehicleList
import org.json.JSONArray
import org.json.JSONObject
import java.nio.charset.Charset

class VehicleListRequest
    (
    url: String,
    headers: MutableMap<String, String>,
    listener: Response.Listener<List<VehicleList>>,
    errorListener: Response.ErrorListener
) : GenericRequest<List<VehicleList>>(Method.GET, url, headers, listener, errorListener) {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun transformResponse(response: NetworkResponse?): List<VehicleList> {

        val responseBody = String(
            response?.data ?: ByteArray(0),
            Charset.forName(HttpHeaderParser.parseCharset(response?.headers))
        )
        val vehicles = JSONArray(responseBody)
        val mutableList = mutableListOf<VehicleList>()
        Log.d("", mutableList.toString())
        for (i in 0 until vehicles.length()) {
            val t: JSONObject = vehicles.getJSONObject(i)
            Log.i("TAG", t.toString())
            if (t.isNull("distanceInKm")) {
                t.put("distanceInKm", 0.0)
            }
            if (t.isNull("timeInMinutes")) {
                t.put("timeInMinutes", 0)
            }
            mutableList.add(
                VehicleList(
                    t.get("vehicleId") as Int,
                    t.get("vehicleNumber") as String,
                    t.get("size") as Int,
                    t.get("brand") as String,
                    t.get("model") as String,
                    t.get("createdBy") as Int,
                    t.get("status") as String,
                    t.get("fuelType") as String
                )
            )

        }
        return mutableList

    }
}