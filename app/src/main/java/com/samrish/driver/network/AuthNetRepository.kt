package com.samrish.driver.network

import android.app.Application
import android.content.Context
import android.os.Build
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.extensions.authentication
import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.moshi.moshiDeserializerOf
import com.samrish.driver.R
import com.samrish.driver.database.AppDatabase
import com.samrish.driver.models.TripStartRequest
import com.samrish.driver.network.requests.RegisterDeviceRequest
import com.samrish.driver.viewmodels.TripsAssigned
import com.samrish.driver.viewmodels.VehicleAssignment
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

@JsonClass(generateAdapter = true)
data class AuthResponse(
    var authToken: String
)


@JsonClass(generateAdapter = true)
data class DeviceRegistrationDetail(
    val fcmToken: String,
    val deviceIdentifier: String,
    val deviceName: String,
    val typeCode: String
)


class AuthNetRepository {

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

    fun registerDevice(context: Context, accessToken:String, deviceRegDetail:DeviceRegistrationDetail) {

        val moshi = Moshi.Builder().build()
        val jsonAdapter: JsonAdapter<DeviceRegistrationDetail> =
            moshi.adapter(DeviceRegistrationDetail::class.java)
        val requestBody: String = jsonAdapter.toJson(deviceRegDetail)


        val url = context.resources.getString(R.string.url_device_registration)

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


    fun authenticate(
        context: Context,
        firebaseIdToken: String,
        onLoginSuccess: () -> Unit,
        onLoginFailure: () -> Unit
    ) {
        val queue = Volley.newRequestQueue(context)
        val url =
            context.resources.getString(R.string.url_authenticate) + "?firebaseIdToken=" + firebaseIdToken


        context.applicationContext?.let {
            val stringRequest =
                JsonObjectRequest(Request.Method.GET, url, null, { response ->
                    run {
                        saveAccessToken(
                            it,
                            response.getString("authToken")
                        )


                        val deviceName = Build.MANUFACTURER + " " + Build.MODEL
                        val deviceIdentifier =
                            Settings.Secure.getString(
                                it.contentResolver,
                                Settings.Secure.ANDROID_ID
                            )

                        val devRegUrl =
                            context.resources.getString(R.string.url_device_registration)

                        val hdrs = mutableMapOf<String, String>()
                        val authHeader = getAccessToken(it)
                        if (authHeader != null) {
                            hdrs["Authorization"] = "Bearer $authHeader"
                        }

                        val deviceRegRequest = RegisterDeviceRequest(
                            deviceIdentifier,
                            deviceName,
                            devRegUrl,
                            hdrs,
                            { _ ->
                                Toast.makeText(context, "Login Successful!", Toast.LENGTH_LONG)
                                    .show()
                                fetchDriverProfile(
                                    context = it,
                                    onProfileFetched = { profile ->
                                        saveAccessDriverId(it, profile.id)
                                    }
                                )

                                onLoginSuccess()
                            },
                            { _ ->
                                Toast.makeText(context, "Login Failed!", Toast.LENGTH_LONG).show()
                                onLoginFailure()
                            })
                        queue.add(deviceRegRequest)
                    }
                }, { error ->
                    run {
                        Log.i("Login", "Request Failed with Error: $error")
                        Toast.makeText(it, "Login Failed!", Toast.LENGTH_LONG).show()
                    }
                }
                )

            // Add the request to the RequestQueue.
            queue.add(stringRequest)
            Log.i(
                "AuthStorage",
                "Token:" + getAccessToken(it)
            )
        }
    }

    fun attemptLogin(
        context: Context,
        username: String,
        password: String,
        onLoginSuccess: () -> Unit,
        onLoginFailure: () -> Unit
    ) {

        val queue = Volley.newRequestQueue(context)
        val url = context.resources.getString(R.string.url_login)

        val jsonRequest = JSONObject()
        jsonRequest.put("username", username)
        jsonRequest.put("password", password)

        context.applicationContext?.let {
            val stringRequest =
                JsonObjectRequest(Request.Method.POST, url, jsonRequest, { response ->
                    run {
                        saveAccessToken(
                            it,
                            response.getString("authToken")
                        )
                        Toast.makeText(context, "Login Successful!", Toast.LENGTH_LONG).show()

                        val deviceName = Build.MANUFACTURER + " " + Build.MODEL
                        val deviceIdentifier =
                            Settings.Secure.getString(
                                it.contentResolver,
                                Settings.Secure.ANDROID_ID
                            )

                        val devRegUrl =
                            context.resources.getString(R.string.url_device_registration)

                        val hdrs = mutableMapOf<String, String>()
                        val authHeader = getAccessToken(it)
                        if (authHeader != null) {
                            hdrs["Authorization"] = "Bearer $authHeader"
                        }

                        val deviceRegRequest = RegisterDeviceRequest(
                            deviceIdentifier,
                            deviceName,
                            devRegUrl,
                            hdrs,
                            { _ ->
                                Log.i("Device", "Device registered successfully!")
                                fetchDriverProfile(
                                    context = it,
                                    onProfileFetched = { profile ->
                                        saveAccessDriverId(it, profile.id)
                                    }
                                )
                                onLoginSuccess()
                            },
                            { _ ->
                                Log.i("Device", "Device registered successfully!")
                                onLoginFailure()
                            })
                        queue.add(deviceRegRequest)
                    }
                }, { error ->
                    run {
                        Log.i("Login", "Request Failed with Error: $error")
                        Toast.makeText(it, "Login Failed!", Toast.LENGTH_LONG).show()
                    }
                }
                )

            // Add the request to the RequestQueue.
            queue.add(stringRequest)
            Log.i(
                "AuthStorage",
                "Token:" + getAccessToken(it)
            )
        }

    }


    companion object {
        private var INSTANCE: AuthNetRepository? = null
        fun getInstance(): AuthNetRepository {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = AuthNetRepository()
                }
            }
            return INSTANCE!!
        }
    }

}

