package com.samrish.driver

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private fun goToLogin () {
        val changePage = Intent(this.applicationContext, LoginActivity::class.java)
        startActivity(changePage)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if("" == SessionStorage().getAccessToken(this)) {
            goToLogin()
        }
        setContentView(R.layout.activity_trip_list)
        var tripList = findViewById<RecyclerView>(R.id.trip_list)
        tripList.adapter = TripsAdapter()
    }
}