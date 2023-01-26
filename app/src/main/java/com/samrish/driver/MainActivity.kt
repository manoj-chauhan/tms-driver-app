package com.samrish.driver

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    private fun goToLogin () {
        val changePage = Intent(this.applicationContext, LoginActivity::class.java)
        startActivity(changePage)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trip_list)

        if("" == SessionStorage().getAccessToken(this)) {
            goToLogin()
        }
    }
}