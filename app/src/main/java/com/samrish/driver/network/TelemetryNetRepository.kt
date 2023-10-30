package com.samrish.driver.network

import android.content.Context
import android.util.Log
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.extensions.authentication
import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.samrish.driver.R
import com.samrish.driver.models.Telemetry
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class TelemetryNetRepository @Inject constructor(@ApplicationContext private val context: Context) {
    fun sentTelemetry(telemetry: Telemetry) {
        val moshi = Moshi.Builder().build()
        val jsonAdapter: JsonAdapter<Telemetry> =
            moshi.adapter(Telemetry::class.java)
        val requestBody: String = jsonAdapter.toJson(telemetry)


        val url = context.resources.getString(R.string.url_device_matrix)

        val fuelManager = FuelManager()
        getAccessToken(context)?.let {
            {
                val (_, response, result) = fuelManager.post(url).authentication()
                    .bearer(it)
                    .jsonBody(requestBody)
                    .response()

                result.fold(
                    { successResponse ->
                        Log.d("Device Matrix", "Device Matrix Sent Successful")
                    },
                    { error ->
                        Log.d("Device Matrix", "Matrix sending Failed $error")
                        throw Exception("Device Matrix Failed");
                    })
            }
        }
    }
}