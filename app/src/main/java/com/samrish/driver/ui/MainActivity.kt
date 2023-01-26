package com.samrish.driver.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.Volley
import com.samrish.driver.R
import com.samrish.driver.models.Trip
import com.samrish.driver.services.SessionStorage
import com.samrish.driver.services.TripListRequest

class MainActivity : AppCompatActivity() {

    private var tripList: RecyclerView? = null

    private fun goToLogin() {
        val changePage = Intent(this.applicationContext, LoginActivity::class.java)
        startActivity(changePage)
    }


    private fun onTripSelected(trip: Trip) {
        val intent = Intent(this, TripDetailActivity()::class.java)
        intent.putExtra("TRIP_CODE", trip.code)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trip_list)
    }

    override fun onStart() {

        if ("" == SessionStorage().getAccessToken(this)) {
            goToLogin()
        }
        super.onResume()
        tripList = findViewById<RecyclerView>(R.id.trip_list)
        getTrips()
        super.onStart()
    }

    private fun getTrips() {
        val queue = Volley.newRequestQueue(this)
        val url = resources.getString(R.string.url_trips_list)

        val hdrs: MutableMap<String, String> = mutableMapOf<String, String>()
        val authHeader = SessionStorage().getAccessToken(this)
        if(authHeader != null) {
            hdrs?.put("Authorization", "Bearer $authHeader")
        }

        val stringRequest = TripListRequest(url, hdrs, { response ->
            tripList?.adapter = TripsAdapter(response) { trip: Trip -> onTripSelected(trip) }
        }, { error ->
            run {
                tripList?.adapter = null
                Log.i("TripList", "Request Failed with Error: $error")
            }
        })
        queue.add(stringRequest)
    }

}