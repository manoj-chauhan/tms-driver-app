package com.samrish.driver.viewmodels

import android.app.Application
import android.provider.Settings
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.kittinunf.fuel.core.FuelManager
import com.samrish.driver.R
import com.samrish.driver.services.getAccessToken
import com.squareup.moshi.Json
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class TripStartRequest(
    @Json(name = "deviceIdentifier") val deviceIdentifier: String,
    @Json(name = "tripCode") val tripCode: String
)

class ActionButtonViewModel(application: Application) : AndroidViewModel(application) {
    val tripStartResult = MutableLiveData<Result<String>>()
    fun startTrip(tripCode: String, operatorId: Int) {
        viewModelScope.launch {

            Log.d("TAG", "startTrip: view model scope started")
            try {
                val deviceIdentifier = Settings.Secure.getString(
                    getApplication<Application>().contentResolver,
                    Settings.Secure.ANDROID_ID
                )

                val request = TripStartRequest(deviceIdentifier, tripCode)
                val headers = mutableMapOf<String, String>()

                val token = getAccessToken(getApplication())
                token?.let {
                    headers["Authorization"] = "Bearer $it"
                    headers["Company-Id"] = operatorId.toString()
                }

                val moshi = Moshi.Builder()
                    .add(KotlinJsonAdapterFactory())
                    .build()
                val jsonAdapter = moshi.adapter(TripStartRequest::class.java)
                val requestBody = jsonAdapter.toJson(request)

                Log.d("", "JSON Generated: $requestBody")

                val response = tripStartRequest(
                    getApplication<Application>().resources.getString(R.string.url_trip_start),
                    headers,
                    requestBody
                )
                Log.d("View Model End", "startTrip: View model response added")
                tripStartResult.postValue(Result.success(response))
            } catch (e: Exception) {

            }
        }
    }

    private suspend fun tripStartRequest(
        url: String,
        headers: Map<String, String>,
        body: String
    ): String {
        val fuelManager = FuelManager()

        return withContext(Dispatchers.IO) {
            val (_, response, result) = fuelManager.post(url)
                .header(headers)
                .header("Content-Type" to "application/json")
                .body(body)
                .response()

            Log.d("TAG", "tripStartRequest: $response")
            when (result) {
                is com.github.kittinunf.result.Result.Success -> {
                    return@withContext response.body().toString()
                }

                is com.github.kittinunf.result.Result.Failure -> {
                    throw result.getException()
                }
                else -> {
                    "null"
                }
            }
        }
    }
}
