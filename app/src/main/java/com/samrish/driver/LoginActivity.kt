package com.samrish.driver

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText

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
        var username: String = editTextUsername.text.toString()
        var password: String = editTextPassword.text.toString()
        Log.i("Login", "Username: $username  Password: $password");
    }

}