package com.samrish.driver.services

import android.content.Context
import android.os.Build
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.samrish.driver.R
import com.samrish.driver.services.requests.RegisterDeviceRequest
import org.json.JSONObject


fun authenticate(
    context: Context,
    firebaseIdToken: String,
    onLoginSuccess: ()->Unit,
    onLoginFailure:()->Unit
) {
    val queue = Volley.newRequestQueue(context)
    val url = context.resources.getString(R.string.url_authenticate) + "?firebaseIdToken="+firebaseIdToken

    context.applicationContext?.let {
        val stringRequest =
            JsonObjectRequest(Request.Method.GET, url, null, { response ->
                run {
                    saveAccessToken(
                        it,
                        response.getString("authToken")
                    )
                    Toast.makeText(context, "Login Successful!", Toast.LENGTH_LONG).show()

                    val deviceName = Build.MANUFACTURER + " " + Build.MODEL
                    val deviceIdentifier =
                        Settings.Secure.getString(it.contentResolver, Settings.Secure.ANDROID_ID)

                    val devRegUrl = context.resources.getString(R.string.url_device_registration)

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
                                onProfileFetched = {
                                        profile -> saveAccessDriverId(it, profile.id)
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

fun attemptLogin(
    context: Context,
    username: String,
    password: String,
    onLoginSuccess: ()->Unit,
    onLoginFailure:()->Unit
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
                        Settings.Secure.getString(it.contentResolver, Settings.Secure.ANDROID_ID)

                    val devRegUrl = context.resources.getString(R.string.url_device_registration)

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
                                onProfileFetched = {
                                        profile -> saveAccessDriverId(it, profile.id)
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

