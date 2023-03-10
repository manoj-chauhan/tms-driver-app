package com.samrish.driver.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.NavHostController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.*
import com.android.volley.toolbox.Volley
import com.samrish.driver.R
import com.samrish.driver.models.Trip
import com.samrish.driver.services.SessionStorage
import com.samrish.driver.services.TripListRequest

class  CurrentAssignmentsFragment: Fragment() {


    private var tripList: RecyclerView? = null
    private  var tList = mutableStateListOf<Trip>()

    private fun goToLogin() {
//        val navHostFragment = (host as AppCompatActivity).findViewById<FragmentContainerView>(R.id.nav_host_fragment)
//        var hostController = navHostFragment.findNavController() as NavHostController
//        hostController.navigate(R.id.loginFragment)
    }

    private fun goToTripDetail(tripCode: String) {
        var extras = Bundle()
        extras.putString("TRIP_CODE", tripCode)

        val fragContainerView  = (host as AppCompatActivity).findViewById<FragmentContainerView>(R.id.nav_host_frag)
        var hostController = fragContainerView.findNavController() as NavHostController
        hostController.navigate(R.id.navigateToTripDetail,extras)

    }


    private fun onTripSelected(trip: Trip) {
        trip?.code?.let { goToTripDetail(it) }
//        val intent = Intent(this.context, TripDetailFragment()::class.java)
//        intent.putExtra("TRIP_CODE", trip.code)
//        startActivity(intent)
    }

    override fun onStart() {
        if ("" == this.context?.let { SessionStorage().getAccessToken(it) }) {
            goToLogin()
        }else {
            getTrips()
        }
        super.onStart()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            // Dispose the Composition when viewLifecycleOwner is destroyed
            setViewCompositionStrategy(
                ViewCompositionStrategy.DisposeOnLifecycleDestroyed(viewLifecycleOwner)
            )

            setContent {
                AssignmentList(
                    tripList = tList,
                    onAssignmentClick = {
                        onTripSelected(it)
                    }
                )
            }
        }
    }


    private fun getTrips() {
        val queue = Volley.newRequestQueue(this.context)
        val url = resources.getString(R.string.url_trips_list)

        val hdrs: MutableMap<String, String> = mutableMapOf<String, String>()
        val authHeader = this.context?.let { SessionStorage().getAccessToken(it) }
        if(authHeader != null) {
            hdrs["Authorization"] = "Bearer $authHeader"
        }

        val stringRequest = TripListRequest(url, hdrs, { response ->
            tList.addAll(response)
//            tripList?.adapter = TripsAdapter(response) { trip: Trip -> onTripSelected(trip) }
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

@Composable
fun Assignment(trip: Trip, onClick: (trip: Trip) -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable {
                onClick(trip)
            }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "3465464"
                )
                Text(
                    text = "25 Jun 23 03:00",
                    color = Color.Gray
                )
                Text(
                    text = "STARTED",
                    color = Color.Green
                )
            }
            Row() {
                Text(
                    text = trip.name
                )
            }

        }
    }
}


@Composable
fun AssignmentList(tripList: List<Trip>, onAssignmentClick: (trip: Trip) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        tripList.forEach { trip -> Assignment(trip, onAssignmentClick) }
    }
}

@Preview
@Composable
fun AssignmentListPreview() {
    AssignmentList(tripList = listOf(
        Trip("BH4-BH5-BH6","34456456", "STARTED"),
        Trip("BH4-BH5-BH6","34456457", "NOT STARTED"),
    ), onAssignmentClick = {})
}