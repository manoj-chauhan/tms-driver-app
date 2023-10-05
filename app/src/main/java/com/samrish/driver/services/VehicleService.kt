package com.samrish.driver.services

import android.content.Context
import android.util.Log
import com.android.volley.toolbox.Volley
import com.samrish.driver.R
import com.samrish.driver.models.VehicleAssignment
import com.samrish.driver.services.requests.VehicleAssignedRequest

fun vehicleDetails(context: Context, onVehicleDetailFetched: (vehicle: VehicleAssignment) -> Unit)
{
    val queue = Volley.newRequestQueue(context)
    val url = context.resources.getString(R.string.url_vehicle_assignment)

    val hdrs: MutableMap<String, String> = mutableMapOf<String, String>()
    context.applicationContext?.let {
        getAccessToken(it)?.let {
            hdrs["Authorization"] = "Bearer $it"
        }

        val stringRequest = VehicleAssignedRequest(url, hdrs, { response ->
            Log.i("VehicleDetail", "Vehicle Detail: $response")
            onVehicleDetailFetched(response)
        }, { error -> handleError(context, error) })
        queue.add(stringRequest)
    }
}