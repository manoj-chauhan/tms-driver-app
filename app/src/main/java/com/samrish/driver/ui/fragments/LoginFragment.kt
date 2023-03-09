package com.samrish.driver.ui.fragments

import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavHostController
import androidx.navigation.findNavController
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.samrish.driver.R
import com.samrish.driver.services.RegisterDeviceRequest
import com.samrish.driver.services.SessionStorage
import org.json.JSONObject

class LoginFragment : Fragment(R.layout.fragment_login), View.OnClickListener {

    private lateinit var editTextUsername: AppCompatEditText
    private lateinit var editTextPassword: AppCompatEditText

    private fun goToMain() {
        val ft: FragmentTransaction = (host as AppCompatActivity).supportFragmentManager.beginTransaction()
        ft.replace(R.id.main_view, MainFragment(), "MainFragment")
        ft.commitAllowingStateLoss()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextUsername = view.findViewById(R.id.login_input_username)
        editTextPassword = view.findViewById(R.id.login_input_password)

        view.findViewById<AppCompatButton>(R.id.login_btn_login).setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        val username: String = editTextUsername.text.toString()
        val password: String = editTextPassword.text.toString()
        Log.i("Login", "Username: $username  Password: $password")

        val queue = Volley.newRequestQueue(context)
        val url = resources.getString(R.string.url_login)

        val jsonRequest = JSONObject()
        jsonRequest.put("username", username)
        jsonRequest.put("password", password)

        context?.applicationContext?.let {
            val stringRequest =
                JsonObjectRequest(Request.Method.POST, url, jsonRequest, { response ->
                    run {
                        SessionStorage().saveAccessToken(
                            it,
                            response.getString("authToken")
                        )
                        Toast.makeText(context, "Login Successful!", Toast.LENGTH_LONG).show()

                        val deviceName = Build.MANUFACTURER + " " + Build.MODEL
                        val deviceIdentifier = Settings.Secure.getString(it.contentResolver, Settings.Secure.ANDROID_ID)

                        val devRegUrl = resources.getString(R.string.url_device_registration)

                        val hdrs = mutableMapOf<String, String>()
                        val authHeader = SessionStorage().getAccessToken(it)
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
                                goToMain()
                            },
                            { _ ->
                                Log.i("Device", "Device registered successfully!")
                            })
                        queue.add(deviceRegRequest)
                    }
                }, { error ->
                    run {
                        Log.i("Login", "Request Failed with Error: $error")
                        Toast.makeText(it,"Login Failed!", Toast.LENGTH_LONG).show()
                    }
                }
                )

            // Add the request to the RequestQueue.
            queue.add(stringRequest)
            Log.i(
                "AuthStorage",
                "Token:" + SessionStorage().getAccessToken(it)
            )
        }

    }
}
