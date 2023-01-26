package com.samrish.driver

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private var tripList: RecyclerView? = null

    private fun goToLogin() {
        val changePage = Intent(this.applicationContext, LoginActivity::class.java)
        startActivity(changePage)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if ("" == SessionStorage().getAccessToken(this)) {
            goToLogin()
        }
        setContentView(R.layout.activity_trip_list)
        tripList = findViewById<RecyclerView>(R.id.trip_list)
        getTrips()
    }

    private fun getTrips() {
        val queue = Volley.newRequestQueue(this)
        val url = resources.getString(R.string.url_trips_list)

        val hdrs: MutableMap<String, String> = mutableMapOf<String, String>()
        val authHeader = SessionStorage().getAccessToken(this)
        if(authHeader != null) {
            hdrs?.put("Authorization", "Bearer $authHeader")
        }

        val stringRequest = TripsRequest<String>(url, hdrs, { response ->
            var trips = JSONArray(response)

            val mutableList = mutableListOf<Trip>()
            for (i in 0 until trips.length()){
                var t:JSONObject = trips.getJSONObject(i)
                var tr:Trip = Trip(t.get("tripName") as String?, t.get("tripCode") as String?)
                mutableList.add(tr)
            }
            tripList?.adapter = TripsAdapter(mutableList)

        }, { error ->
            Log.i("TripList", "Request Failed with Error: $error")
        })
        queue.add(stringRequest)
    }

}