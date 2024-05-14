package com.drishto.driver.network

import android.content.Context
import android.util.Log
import com.drishto.driver.R
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.extensions.authentication
import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.moshi.moshiDeserializerOf
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import javax.inject.Inject

@JsonClass(generateAdapter = true)
data class AuthResponse(
    var authToken: String
)


@JsonClass(generateAdapter = true)
data class DeviceRegistrationDetail(
    val fcmToken: String,
    val deviceIdentifier: String,
    val deviceName: String,
    val platform: String,
    val appName: String,
    val appVersion: String,
    val platformVersion: String
)


class AuthNetRepository @Inject constructor() {

    fun login(context: Context, firebaseIdToken: String): String {
        val url =
            context.resources.getString(R.string.url_authenticate) + "?firebaseIdToken=" + firebaseIdToken
        val (_, _, result) = url.httpGet()
            .responseObject(moshiDeserializerOf(AuthResponse::class.java))
        Log.d("Login", "Auth Response: $result ")

        result.fold(
            { authResponse ->
                Log.d("Login", authResponse.toString())
            },
            { error ->
                Log.d("Login", error.toString())
                throw Exception("Authentication Failed")
            })

        return result.get().authToken;
    }

    fun registerDevice(
        context: Context,
        accessToken: String,
        appType: String,
        deviceRegDetail: DeviceRegistrationDetail
    ) {

        val moshi = Moshi.Builder().build()
        val jsonAdapter: JsonAdapter<DeviceRegistrationDetail> =
            moshi.adapter(DeviceRegistrationDetail::class.java)
        val requestBody: String = jsonAdapter.toJson(deviceRegDetail)
        var url:String = ""

        if(appType == "driverApp"){
             url = context.resources.getString(R.string.url_device_registration)
        }else{
            url = context.resources.getString(R.string.url_device_registration)
        }

        val fuelManager = FuelManager()
        val (_, response, result) = fuelManager.post(url).authentication().bearer(accessToken)
            .jsonBody(requestBody)
            .response()

        result.fold(
            { successResponse ->
                Log.d("Login","Device Registration Successful")
            },
            { error ->
                Log.d("Login","Device Registration Failed $error")
                throw Exception("Device Registration Failed");
            })

    }


}

