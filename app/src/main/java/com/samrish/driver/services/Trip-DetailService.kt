package com.samrish.driver.services
import android.content.Context
import android.util.Log
import com.android.volley.toolbox.Volley
import com.samrish.driver.R
import com.samrish.driver.services.requests.VehicleListRequest

fun click(context: Context) {

    val queue = Volley.newRequestQueue(context)
    val url = context.resources.getString(R.string.url_vehicle_list) + "?pageno=0"+ "&filter="

    val hdrs: MutableMap<String, String> = mutableMapOf<String, String>()
    val authHeader = getAccessToken(context)

    authHeader?.let {
        hdrs["Authorization"] = "Bearer $it"
        hdrs["Company-Id"] = getSelectedCompanyId(context).toString()
    }

    val stringRequest = VehicleListRequest(url, hdrs, { response ->
                Log.i("TripDetail", "Trip Schedule: $response")
    },
        { error ->

        })
    queue.add(stringRequest)

}