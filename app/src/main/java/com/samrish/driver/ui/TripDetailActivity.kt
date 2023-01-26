package com.samrish.driver.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import com.android.volley.toolbox.Volley
import com.samrish.driver.R
import com.samrish.driver.models.Trip
import com.samrish.driver.services.SessionStorage
import com.samrish.driver.services.TripDetailRequest

class TripDetailActivity : AppCompatActivity(), View.OnClickListener {

    private var checkInButton: AppCompatButton? = null
    private var departButton: AppCompatButton? = null
    private var endButton: AppCompatButton? = null
    private var cancelButton: AppCompatButton? = null

    var currentTripCode: String? = null

    private fun goToLogin() {
        val changePage = Intent(this.applicationContext, LoginActivity::class.java)
        startActivity(changePage)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            currentTripCode = bundle.getString("TRIP_CODE")
        }
        setContentView(R.layout.activity_trip_detail)
        checkInButton = findViewById<AppCompatButton>(R.id.trip_detail_btn_check_in)
        departButton = findViewById<AppCompatButton>(R.id.trip_detail_btn_depart)
        endButton = findViewById<AppCompatButton>(R.id.trip_detail_btn_end)
        cancelButton = findViewById<AppCompatButton>(R.id.trip_detail_btn_cancel)

        checkInButton?.setOnClickListener(this)
        departButton?.setOnClickListener(this)
        endButton?.setOnClickListener(this)
        cancelButton?.setOnClickListener(this)


    }

    override fun onStart() {
        if ("" == SessionStorage().getAccessToken(this)) {
            goToLogin()
        }
        getTripDetail()
        super.onStart()
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
            showDetails(response)
        }, { error ->
            Log.i("TripDetail", "Request Failed with Error: $error")
        })
        queue.add(stringRequest)
    }

    private fun showDetails(trip: Trip){
        findViewById<TextView>(R.id.trip_detail_name).text = trip.name
        findViewById<TextView>(R.id.trip_detail_code).text = trip.code
        when(trip.status) {
            0 -> {
                findViewById<TextView>(R.id.trip_detail_status).text = "NOT STARTED"
                checkInButton?.visibility = View.VISIBLE
                departButton?.visibility = View.GONE
                endButton?.visibility = View.GONE
                cancelButton?.visibility = View.VISIBLE
            }
            1 -> {
                findViewById<TextView>(R.id.trip_detail_status).text = "CHECKED-IN"
                checkInButton?.visibility = View.GONE
                departButton?.visibility = View.VISIBLE
                endButton?.visibility = View.VISIBLE
                cancelButton?.visibility = View.VISIBLE
            }
            2 -> {
                findViewById<TextView>(R.id.trip_detail_status).text = "DEPARTED"
                checkInButton?.visibility = View.VISIBLE
                departButton?.visibility = View.GONE
                endButton?.visibility = View.VISIBLE
                cancelButton?.visibility = View.VISIBLE
            }
            3 -> {
                findViewById<TextView>(R.id.trip_detail_status).text = "ENDED"
            }
            4 -> {
                findViewById<TextView>(R.id.trip_detail_status).text = "CANCELLED"
            }
            else -> {
                findViewById<TextView>(R.id.trip_detail_status).text = "ERROR"
            }
        }
    }

    override fun onClick(btn: View?) {
        if (btn != null) {
            when(btn.id){
                R.id.trip_detail_btn_check_in -> {
                    Log.i("TripDetail", "Check In clicked")
                }
                R.id.trip_detail_btn_depart -> {
                    Log.i("TripDetail", "Depart clicked")
                }
                R.id.trip_detail_btn_end -> {
                    Log.i("TripDetail", "End clicked")
                }
                R.id.trip_detail_btn_cancel -> {
                    Log.i("TripDetail", "Cancel clicked")
                }
            }
        }
    }
}