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
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@JsonClass(generateAdapter = true)
data class TripStartRequest(
    val deviceIdentifier: String, val tripCode: String
)

@JsonClass(generateAdapter = true)
data class TripCheckInRequest(
    val placeCode: String,
    val tripCode: String
)

@JsonClass(generateAdapter = true)
data class TripRequest(
    val tripCode: String,
    val operatorId: Int
)


class ActionButtonViewModel(application: Application) : AndroidViewModel(application) {
    val tripStartResult = MutableLiveData<Result<String>>()
    fun startTrip(tripCode: String, operatorId: Int, context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val deviceIdentifier = Settings.Secure.getString(
                    getApplication<Application>().contentResolver, Settings.Secure.ANDROID_ID
                )

                val request = TripStartRequest(deviceIdentifier, tripCode)

                val moshi = Moshi.Builder().build()
                val jsonAdapter: JsonAdapter<TripStartRequest> =
                    moshi.adapter(TripStartRequest::class.java)
                val requestBody: String = jsonAdapter.toJson(request)


                val url = context.resources.getString(R.string.url_trip_start)
                getAccessToken(context)?.let {
                    val fuelManager = FuelManager()
                    val (_, response, result) = fuelManager.post(url).authentication().bearer(it)
                        .header("Company-Id", operatorId.toString()).jsonBody(requestBody)
                        .response()
                }
            } catch (e: Exception) {
            }
        }
    }

    fun checkInTrip(context: Context, tripCode: String, operatorId: Int, placeCode: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val checkInRequest = TripCheckInRequest(placeCode, tripCode)

                val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                val jsonAdapter: JsonAdapter<TripCheckInRequest> =
                    moshi.adapter(TripCheckInRequest::class.java)
                val requestBody = jsonAdapter.toJson(checkInRequest)

                val url = context.resources.getString(R.string.url_trip_check_in)

                getAccessToken(context)?.let {
                    val fuelManager = FuelManager()
                    val (_, response, result) = fuelManager.post(url).authentication().bearer(it)
                        .header("Company-Id", operatorId.toString()).jsonBody(requestBody)
                        .response()
                }

            } catch (e: Exception) {

            }
        }
    }

    fun departTrip(context: Context, tripCode: String, operatorId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val departRequest = TripRequest(tripCode, operatorId)
                val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                val jsonAdapter: JsonAdapter<TripRequest> = moshi.adapter(TripRequest::class.java)
                val requestBody = jsonAdapter.toJson(departRequest)


                val url = context.resources.getString(R.string.url_trip_depart)

                getAccessToken(context)?.let {
                    val fuelManager = FuelManager()
                    val (_, response, result) = fuelManager.post(url).authentication().bearer(it)
                        .header("Company-Id", operatorId.toString()).jsonBody(requestBody)
                        .response()
                }
            } catch (e: Exception) {

            }

        }
    }

    fun cancelTrip(context: Context, tripCode: String, operatorId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val cancelRequest = TripRequest(tripCode, operatorId)
                val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                val jsonAdapter: JsonAdapter<TripRequest> = moshi.adapter(TripRequest::class.java)
                val requestBody = jsonAdapter.toJson(cancelRequest)

                val url = context.resources.getString(R.string.url_trip_cancel)

                getAccessToken(context)?.let {
                    val fuelManager = FuelManager()
                    val (_, response, result) = fuelManager.post(url).authentication().bearer(it)
                        .header("Company-Id", operatorId.toString()).jsonBody(requestBody)
                        .response()
                }

            } catch (e: Exception) {

            }
        }
    }

    fun endTrip(context: Context, tripCode: String, operatorId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val endRequest = TripRequest(tripCode, operatorId)
                val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                val jsonAdapter: JsonAdapter<TripRequest> = moshi.adapter(TripRequest::class.java)
                val requestBody = jsonAdapter.toJson(endRequest)

                val url = context.resources.getString(R.string.url_trip_end)

                getAccessToken(context)?.let {
                    val fuelManager = FuelManager()
                    val (_, response, result) = fuelManager.post(url).authentication().bearer(it)
                        .header("Company-Id", operatorId.toString()).jsonBody(requestBody)
                        .response()
                }

            } catch(e:Exception){

            }
        }
    }
}
