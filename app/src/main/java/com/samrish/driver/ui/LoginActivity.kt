package com.samrish.driver.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import com.android.volley.*
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.samrish.driver.R
import com.samrish.driver.models.Trip
import com.samrish.driver.services.RegisterDeviceRequest
import com.samrish.driver.services.SessionStorage
import org.json.JSONObject

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var editTextUsername: AppCompatEditText;
    private lateinit var editTextPassword: AppCompatEditText;

    private fun goToMain() {
        val changePage = Intent(this.applicationContext, MainActivity::class.java)
        changePage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(changePage)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        editTextUsername = findViewById(R.id.login_input_username)
        editTextPassword = findViewById(R.id.login_input_password);

        findViewById<AppCompatButton>(R.id.login_btn_login).setOnClickListener(this);
    }

    override fun onClick(p0: View?) {
        val username: String = editTextUsername.text.toString()
        val password: String = editTextPassword.text.toString()
        Log.i("Login", "Username: $username  Password: $password");

        val queue = Volley.newRequestQueue(this)
        val url = resources.getString(R.string.url_login)

        val jsonRequest: JSONObject = JSONObject()
        jsonRequest.put("username", username)
        jsonRequest.put("password", password)

        val stringRequest = JsonObjectRequest(Request.Method.POST, url, jsonRequest, { response ->
            run {
                SessionStorage().saveAccessToken(this, response.getString("authToken"))
                Toast.makeText(applicationContext, "Login Successful!", Toast.LENGTH_LONG).show();

                val deviceName = android.os.Build.MANUFACTURER + " " + android.os.Build.MODEL
                val deviceIdentifier = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)

                val devRegUrl = resources.getString(R.string.url_device_registration)

                val hdrs: MutableMap<String, String> = mutableMapOf<String, String>()
                val authHeader = SessionStorage().getAccessToken(this)
                if(authHeader != null) {
                    hdrs?.put("Authorization", "Bearer $authHeader")
                }

                val deviceRegRequest = RegisterDeviceRequest(deviceIdentifier, deviceName, devRegUrl, hdrs, { response ->
                    Log.i("Device", "Device registered successfully!")
                    goToMain()
                }, { error -> Log.i("Device", "Device registered successfully!")
                })
                queue.add(deviceRegRequest)
            }
        }, { error ->
            run {
                Log.i("Login", "Request Failed with Error: $error")
                Toast.makeText(applicationContext, "Login Failed!", Toast.LENGTH_LONG).show();
            }
        }
        )

        // Add the request to the RequestQueue.
        queue.add(stringRequest)
        Log.i("AuthStorage", "Token:" + SessionStorage().getAccessToken(this))
    }

}