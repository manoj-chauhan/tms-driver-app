package com.samrish.driver

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var editTextUsername: AppCompatEditText;
    private lateinit var editTextPassword: AppCompatEditText;

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
        val url = "https://www.google.com"

        val stringRequest = StringRequest(Request.Method.GET, url, {
                response -> Log.i("Login", "Response: $response")
            }, {
                Log.i("Login", "Request Failed")
            }
        )

        // Add the request to the RequestQueue.
        queue.add(stringRequest)
    }

}