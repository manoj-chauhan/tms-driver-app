package com.samrish.driver.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.android.volley.toolbox.Volley
import com.samrish.driver.R
import com.samrish.driver.services.SessionStorage
import com.samrish.driver.services.TripDetailRequest

class TripDetailActivity : AppCompatActivity() {

    var currentTripCode: String? = null

    private fun goToLogin() {
        val changePage = Intent(this.applicationContext, LoginActivity::class.java)
        startActivity(changePage)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if ("" == SessionStorage().getAccessToken(this)) {
            goToLogin()
        }
        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            currentTripCode = bundle.getString("TRIP_CODE")
        }
        setContentView(R.layout.activity_trip_detail)
        getTripDetail()
    }

    private fun getTripDetail() {
        val queue = Volley.newRequestQueue(this)
        val url = resources.getString(R.string.url_trips_detail) + currentTripCode

        val hdrs: MutableMap<String, String> = mutableMapOf<String, String>()
        val authHeader = SessionStorage().getAccessToken(this)
        if(authHeader != null) {
            hdrs?.put("Authorization", "Bearer $authHeader")
        }

        val stringRequest = TripDetailRequest(url, hdrs, { response ->
            Log.i("TripDetail", "Trip Detail: $response")
            findViewById<TextView>(R.id.trip_detail_name).text = response.name
            findViewById<TextView>(R.id.trip_detail_code).text = response.code
        }, { error ->
            Log.i("TripDetail", "Request Failed with Error: $error")
        })
        queue.add(stringRequest)
    }

}