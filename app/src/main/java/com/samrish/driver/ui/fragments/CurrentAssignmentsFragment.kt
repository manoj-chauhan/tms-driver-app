package com.samrish.driver.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.NavHostController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.*
import com.android.volley.toolbox.Volley
import com.samrish.driver.R
import com.samrish.driver.models.Trip
import com.samrish.driver.services.LocationService
import com.samrish.driver.services.SessionStorage
import com.samrish.driver.services.TripListRequest
import com.samrish.driver.ui.TripDetailActivity
import com.samrish.driver.ui.TripsAdapter

class  CurrentAssignmentsFragment: Fragment(R.layout.fragment_current_assignments) {


    private var tripList: RecyclerView? = null

    private fun goToLogin() {
        val navHostFragment = (host as AppCompatActivity).findViewById<FragmentContainerView>(R.id.nav_host_fragment)
        var hostController = navHostFragment.findNavController() as NavHostController
        hostController.navigate(R.id.loginFragment)
    }

    private fun onTripSelected(trip: Trip) {
        val intent = Intent(this.context, TripDetailActivity()::class.java)
        intent.putExtra("TRIP_CODE", trip.code)
        startActivity(intent)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val intent = Intent(this.context, LocationService::class.java)
        intent.data = Uri.parse("package:com.techie.tracker")
        intent.action = Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS
        context?.startForegroundService(intent)
    }

    override fun onStart() {
        if ("" == this.context?.let { SessionStorage().getAccessToken(it) }) {
            goToLogin()
        }
        super.onResume()
        tripList = view?.findViewById<RecyclerView>(R.id.trip_list)
        getTrips()

        super.onStart()
    }
    private fun getTrips() {
        val queue = Volley.newRequestQueue(this.context)
        val url = resources.getString(R.string.url_trips_list)

        val hdrs: MutableMap<String, String> = mutableMapOf<String, String>()
        val authHeader = this.context?.let { SessionStorage().getAccessToken(it) }
        if(authHeader != null) {
            hdrs?.put("Authorization", "Bearer $authHeader")
        }

        val stringRequest = TripListRequest(url, hdrs, { response ->
            tripList?.adapter = TripsAdapter(response) { trip: Trip -> onTripSelected(trip) }
        }, { error ->
            run {
                tripList?.adapter = null
                Log.i("TripList", "Request Failed with Error: $error")
                if (error is TimeoutError || error is NoConnectionError) {
                    Toast.makeText(this.context, "Couldn't Connect!", Toast.LENGTH_LONG).show();
                } else if (error is AuthFailureError) {
                    goToLogin();
                } else if (error is ServerError) {
                    Toast.makeText(this.context, "Server error!", Toast.LENGTH_LONG).show();
                } else if (error is NetworkError) {
                    Toast.makeText(this.context, "Network error", Toast.LENGTH_LONG).show();
                } else if (error is ParseError) {
                    Toast.makeText(this.context, "Unable to parse response", Toast.LENGTH_LONG).show();
                }
            }
        })
        queue.add(stringRequest)
    }
}
