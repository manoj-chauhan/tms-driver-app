package com.samrish.driver.viewmodels

import android.app.Application
import android.content.Context
import android.provider.Settings
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.extensions.authentication
import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.samrish.driver.R
import com.samrish.driver.services.getAccessToken
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.launch

@JsonClass(generateAdapter = true)
data class TripStartRequest(
    val deviceIdentifier: String, val tripCode: String
)


class ActionButtonViewModel(application: Application) : AndroidViewModel(application) {
    val tripStartResult = MutableLiveData<Result<String>>()
    fun startTrip(tripCode: String, operatorId: Int, context: Context) {
        viewModelScope.launch {
            try {
                val deviceIdentifier = Settings.Secure.getString(
                    getApplication<Application>().contentResolver, Settings.Secure.ANDROID_ID
                )

                val request = TripStartRequest(deviceIdentifier, tripCode)
                val headers = mutableMapOf<String, String>()

                val token = getAccessToken(getApplication())
                token?.let {
                    headers["Authorization"] = "Bearer $it"
                    headers["Company-Id"] = operatorId.toString()
                }

                val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                val jsonAdapter = moshi.adapter(TripStartRequest::class.java)
                val requestBody = jsonAdapter.toJson(request)

                val url = context.resources.getString(R.string.url_trip_start)
                getAccessToken(context)?.let {
                    val fuelManager = FuelManager()
                    val (_, response, result) = fuelManager.post(url).authentication().bearer(it)
                        .header("Company-Id", operatorId.toString()).jsonBody(requestBody)
                        .response()
                }
            }
            catch (e: Exception) {
            }
        }
    }
}
