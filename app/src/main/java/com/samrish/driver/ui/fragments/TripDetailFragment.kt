package com.samrish.driver.ui.fragments

import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import com.android.volley.*
import com.android.volley.toolbox.Volley
import com.samrish.driver.R
import com.samrish.driver.models.Trip
import com.samrish.driver.services.*

class TripDetailFragment : Fragment(R.layout.fragment_trip_detail), View.OnClickListener {

    private var startButton: AppCompatButton? = null
    private var checkInButton: AppCompatButton? = null
    private var departButton: AppCompatButton? = null
    private var endButton: AppCompatButton? = null
    private var cancelButton: AppCompatButton? = null

    private var currentTripCode: String = ""

    private fun goToLogin() {
//        val changePage = Intent(this.applicationContext, LoginActivity::class.java)
//        startActivity(changePage)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentTripCode = arguments?.getString("TRIP_CODE").toString()
        startButton = view.findViewById<AppCompatButton>(R.id.trip_detail_btn_start)
        checkInButton = view.findViewById<AppCompatButton>(R.id.trip_detail_btn_check_in)
        departButton = view.findViewById<AppCompatButton>(R.id.trip_detail_btn_depart)
        endButton = view.findViewById<AppCompatButton>(R.id.trip_detail_btn_end)
        cancelButton = view.findViewById<AppCompatButton>(R.id.trip_detail_btn_cancel)

        startButton?.setOnClickListener(this)
        checkInButton?.setOnClickListener(this)
        departButton?.setOnClickListener(this)
        endButton?.setOnClickListener(this)
        cancelButton?.setOnClickListener(this)


    }

    override fun onStart() {
        getTripDetail()
        super.onStart()
    }

    private fun getTripDetail() {
        val queue = Volley.newRequestQueue(context)
        val url = resources.getString(R.string.url_trips_detail) + currentTripCode

        val hdrs: MutableMap<String, String> = mutableMapOf<String, String>()
        context?.applicationContext?.let {
            val authHeader = SessionStorage().getAccessToken(it)
            authHeader?.let { hdrs["Authorization"] = "Bearer $authHeader" }

            val stringRequest = TripDetailRequest(url, hdrs, { response ->
                Log.i("TripDetail", "Trip Detail: $response")
                showDetails(response)
            }, { error -> handleError(error) })
            queue.add(stringRequest)
        }
    }

    private fun checkIn() {
        val queue = Volley.newRequestQueue(context)
        val url = resources.getString(R.string.url_trip_check_in)

        val hdrs: MutableMap<String, String> = mutableMapOf<String, String>()
        context?.applicationContext.let {
            val authHeader = it?.let { it1 -> SessionStorage().getAccessToken(it1) }
            authHeader.let{ hdrs["Authorization"] = "Bearer $authHeader" }

            val stringRequest = TripCheckInRequest("AMBI", currentTripCode, url, hdrs, { response ->
                Log.i("TripDetail", "Trip Check-In: $response")
                getTripDetail()
            }, { error -> handleError(error) })
            queue.add(stringRequest)
        }
    }

    private fun start(tripCode: String) {
        val queue = Volley.newRequestQueue(context)
        val url = resources.getString(R.string.url_trip_start)

        val hdrs: MutableMap<String, String> = mutableMapOf<String, String>()
        context?.applicationContext?.let {
            val authHeader = it?.let { it1 -> SessionStorage().getAccessToken(it1) }
            authHeader?.let {hdrs["Authorization"] = "Bearer $authHeader"}
            val deviceIdentifier =
                Settings.Secure.getString(it.contentResolver, Settings.Secure.ANDROID_ID)
            val stringRequest =
                TripStartRequest(tripCode, deviceIdentifier, url, hdrs, { response ->
                    Log.i("TripDetail", "Trip Check-In: $response")
                    getTripDetail()
                }, { error -> handleError(error) })
            queue.add(stringRequest)
        }
    }

    private fun depart() {
        val queue = Volley.newRequestQueue(context)
        val url = resources.getString(R.string.url_trip_depart)

        val hdrs: MutableMap<String, String> = mutableMapOf<String, String>()
        val authHeader = context?.let { SessionStorage().getAccessToken(it.applicationContext) }
        authHeader?.let {hdrs["Authorization"] = "Bearer $authHeader"}

        val stringRequest = TripDepartRequest(currentTripCode, url, hdrs, { response ->
            Log.i("TripDetail", "Trip Depart: $response")
            getTripDetail()
        }, { error -> handleError(error) })
        queue.add(stringRequest)
    }

    private fun end() {
        val queue = Volley.newRequestQueue(context)
        val url = resources.getString(R.string.url_trip_end)

        val hdrs: MutableMap<String, String> = mutableMapOf<String, String>()
        val authHeader = context?.let { SessionStorage().getAccessToken(it.applicationContext) }
        authHeader?.let { hdrs["Authorization"] = "Bearer $authHeader" }

        val stringRequest = TripEndRequest(currentTripCode, url, hdrs, { response ->
            Log.i("TripDetail", "Trip End: $response")
            getTripDetail()
        }, { error -> handleError(error) })
        queue.add(stringRequest)
    }

    private fun cancel() {
        val queue = Volley.newRequestQueue(context)
        val url = resources.getString(R.string.url_trip_cancel)

        val hdrs: MutableMap<String, String> = mutableMapOf<String, String>()
        val authHeader = context?.let { SessionStorage().getAccessToken(it.applicationContext) }
        authHeader?.let{ hdrs["Authorization"] = "Bearer $authHeader" }

        val stringRequest = TripCancelRequest(currentTripCode, url, hdrs, { response ->
            Log.i("TripDetail", "Trip Cancel: $response")
            getTripDetail()
        }, { error -> handleError(error) })
        queue.add(stringRequest)
    }

    private fun showDetails(trip: Trip) {
        view?.findViewById<TextView>(R.id.trip_detail_name)?.text = trip.name
        view?.findViewById<TextView>(R.id.trip_detail_code)?.text = trip.code
        when (trip.status) {
            "NOT_STARTED" -> {
                view?.findViewById<TextView>(R.id.trip_detail_status)?.text = "NOT STARTED"
                startButton?.visibility = View.VISIBLE
                checkInButton?.visibility = View.GONE
                departButton?.visibility = View.GONE
                endButton?.visibility = View.GONE
                cancelButton?.visibility = View.VISIBLE
            }
            "STARTED" -> {
                view?.findViewById<TextView>(R.id.trip_detail_status)?.text = "STARTED"
                startButton?.visibility = View.GONE
                checkInButton?.visibility = View.VISIBLE
                departButton?.visibility = View.GONE
                endButton?.visibility = View.GONE
                cancelButton?.visibility = View.VISIBLE
            }
            "CHECKED_IN" -> {
                view?.findViewById<TextView>(R.id.trip_detail_status)?.text = "CHECKED-IN"
                startButton?.visibility = View.GONE
                checkInButton?.visibility = View.GONE
                departButton?.visibility = View.VISIBLE
                endButton?.visibility = View.VISIBLE
                cancelButton?.visibility = View.VISIBLE
            }
            "IN_TRANSIT" -> {
                view?.findViewById<TextView>(R.id.trip_detail_status)?.text = "IN_TRANSIT"
                startButton?.visibility = View.GONE
                checkInButton?.visibility = View.VISIBLE
                departButton?.visibility = View.GONE
                endButton?.visibility = View.VISIBLE
                cancelButton?.visibility = View.VISIBLE
            }
            "ENDED" -> {
                view?.findViewById<TextView>(R.id.trip_detail_status)?.text = "ENDED"
            }
            "CANCELLED" -> {
                view?.findViewById<TextView>(R.id.trip_detail_status)?.text = "CANCELLED"
            }
            else -> {
                view?.findViewById<TextView>(R.id.trip_detail_status)?.text = "ERROR"
            }
        }
    }

    override fun onClick(btn: View?) {
        if (btn != null) {
            when (btn.id) {
                R.id.trip_detail_btn_start -> {
                    Log.i("TripDetail", "Start clicked")
                    start(currentTripCode)
                }
                R.id.trip_detail_btn_check_in -> {
                    Log.i("TripDetail", "Check In clicked")
                    checkIn()
                }
                R.id.trip_detail_btn_depart -> {
                    Log.i("TripDetail", "Depart clicked")
                    depart()
                }
                R.id.trip_detail_btn_end -> {
                    Log.i("TripDetail", "End clicked")
                    end()
                }
                R.id.trip_detail_btn_cancel -> {
                    Log.i("TripDetail", "Cancel clicked")
                    cancel()
                }
            }
        }
    }

    private fun handleError(error: VolleyError) {
        Log.i("TripDetail", "Request Failed with Error: $error")
        when (error) {
            is TimeoutError, is NoConnectionError -> {
                context?.applicationContext?.let {
                    Toast.makeText(it, "Couldn't Connect!", Toast.LENGTH_LONG).show();
                }
            }
            is AuthFailureError -> {
                goToLogin();
            }
            is ServerError -> {
                context?.applicationContext?.let {
                    Toast.makeText(it, "Server error!", Toast.LENGTH_LONG).show();
                }
            }
            is NetworkError -> {
                context?.applicationContext?.let {
                    Toast.makeText(it, "Network error", Toast.LENGTH_LONG).show();
                }
            }
            is ParseError -> {
                context?.applicationContext?.let {
                    Toast.makeText(it, "Unable to parse response", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}