package com.samrish.driver.services

import android.content.Context
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import com.android.volley.*
import com.android.volley.toolbox.Volley
import com.samrish.driver.R
import com.samrish.driver.models.OldAssignment
import com.samrish.driver.models.Schedule
import com.samrish.driver.models.Trip
import com.samrish.driver.services.requests.*

fun getTrips(context: Context, onTripsFetched: (trips: List<Trip>) -> Unit) {
    val queue = Volley.newRequestQueue(context)
    val url = context.resources.getString(R.string.url_trips_list)

    val hdrs: MutableMap<String, String> = mutableMapOf<String, String>()
    val authHeader = getAccessToken(context)
    if (authHeader != null) {
        hdrs["Authorization"] = "Bearer $authHeader"
    }

    val stringRequest = TripListRequest(url, hdrs, { response ->
        onTripsFetched(response)
    },
        { error ->
            run {
                Log.i("TripList", "Request Failed with Error: $error")
                if (error is TimeoutError || error is NoConnectionError) {
                    Toast.makeText(context, "Couldn't Connect!", Toast.LENGTH_LONG).show();
                } else if (error is AuthFailureError) {
//                goToLogin();
                } else if (error is ServerError) {
                    Toast.makeText(context, "Server error!", Toast.LENGTH_LONG).show();
                } else if (error is NetworkError) {
                    Toast.makeText(context, "Network error", Toast.LENGTH_LONG).show();
                } else if (error is ParseError) {
                    Toast.makeText(context, "Unable to parse response", Toast.LENGTH_LONG)
                        .show();
                }
            }
        })
    queue.add(stringRequest)
}

fun getOldAssignments(context: Context, driverId: Int , onTripsFetched: (trips: List<OldAssignment>) -> Unit) {
    val queue = Volley.newRequestQueue(context)
    val url = context.resources.getString(R.string.url_old_assignments_list) + "/" + driverId + "/assignmentHistory/?pageno=0"

    val hdrs: MutableMap<String, String> = mutableMapOf<String, String>()
    val authHeader = getAccessToken(context)
    if (authHeader != null) {
        hdrs["Authorization"] = "Bearer $authHeader"
    }

    val stringRequest = OldAssignmentListRequest(url, hdrs, { response ->
        onTripsFetched(response)
    },
        { error ->
            run {
                Log.i("TripList", "Request Failed with Error: $error")
                if (error is TimeoutError || error is NoConnectionError) {
                    Toast.makeText(context, "Couldn't Connect!", Toast.LENGTH_LONG).show();
                } else if (error is AuthFailureError) {
//                goToLogin();
                } else if (error is ServerError) {
                    Toast.makeText(context, "Server error!", Toast.LENGTH_LONG).show();
                } else if (error is NetworkError) {
                    Toast.makeText(context, "Network error", Toast.LENGTH_LONG).show();
                } else if (error is ParseError) {
                    Toast.makeText(context, "Unable to parse response", Toast.LENGTH_LONG)
                        .show();
                }
            }
        })
    queue.add(stringRequest)
}

fun getTripDetail(context: Context, tripCode: String, onTripDetailFetched: (trip: Trip) -> Unit) {
    val queue = Volley.newRequestQueue(context)
    val url = context.resources.getString(R.string.url_trips_detail) + tripCode

    val hdrs: MutableMap<String, String> = mutableMapOf<String, String>()
    context.applicationContext?.let {
        val authHeader = getAccessToken(it)
        authHeader?.let { hdrs["Authorization"] = "Bearer $authHeader" }

        val stringRequest = TripDetailRequest(url, hdrs, { response ->
            Log.i("TripDetail", "Trip Detail: $response")
            onTripDetailFetched(response)
        }, { error -> handleError(context, error) })
        queue.add(stringRequest)
    }
}
fun getTripSchedule(context: Context, tripCode: String, onTripScheduleFetched: (schedule: List<Schedule>) -> Unit) {
    val queue = Volley.newRequestQueue(context)
    val url = context.resources.getString(R.string.url_trip_schedules) + tripCode + "/schedule"

    val hdrs: MutableMap<String, String> = mutableMapOf<String, String>()
    context.applicationContext?.let {
        val authHeader = getAccessToken(it)
        authHeader?.let { hdrs["Authorization"] = "Bearer $authHeader" }

        val stringRequest = TripScheduleRequest(url, hdrs, { response ->
            Log.i("TripDetail", "Trip Schedule: $response")
            onTripScheduleFetched(response)
        }, { error -> handleError(context, error) })
        queue.add(stringRequest)
    }
}


fun checkIn(context: Context, tripCode: String, placeCode: String) {
    val queue = Volley.newRequestQueue(context)
    val url = context.resources.getString(R.string.url_trip_check_in)

    val hdrs: MutableMap<String, String> = mutableMapOf<String, String>()
    context.applicationContext.let {
        val authHeader = it?.let { it1 -> getAccessToken(it1) }
        authHeader.let { hdrs["Authorization"] = "Bearer $authHeader" }

        val stringRequest = TripCheckInRequest(placeCode, tripCode, url, hdrs, { response ->
            Log.i("TripDetail", "Trip Check-In: $response")
//            com.samrish.driver.services.getTripDetail(context, tripCode)
        }, { error -> handleError(context, error) })
        queue.add(stringRequest)
    }
}

fun start(context: Context, tripCode: String) {
    val queue = Volley.newRequestQueue(context)
    val url = context.resources.getString(R.string.url_trip_start)

    val hdrs: MutableMap<String, String> = mutableMapOf<String, String>()
    context.applicationContext?.let {
        val authHeader = getAccessToken(it)
        authHeader?.let { hdrs["Authorization"] = "Bearer $authHeader" }
        val deviceIdentifier =
            Settings.Secure.getString(it.contentResolver, Settings.Secure.ANDROID_ID)
        val stringRequest =
            TripStartRequest(tripCode, deviceIdentifier, url, hdrs, { response ->
                Log.i("TripDetail", "Trip Check-In: $response")
//                com.samrish.driver.services.getTripDetail(context, tripCode)
            }, { error -> handleError(context, error) })
        queue.add(stringRequest)
    }
}

fun depart(context: Context, tripCode: String) {
    val queue = Volley.newRequestQueue(context)
    val url = context.resources.getString(R.string.url_trip_depart)

    val hdrs: MutableMap<String, String> = mutableMapOf<String, String>()
    val authHeader = getAccessToken(context.applicationContext)
    authHeader?.let { hdrs["Authorization"] = "Bearer $authHeader" }

    val stringRequest = TripDepartRequest(tripCode, url, hdrs, { response ->
        Log.i("TripDetail", "Trip Depart: $response")
//        com.samrish.driver.services.getTripDetail(context, tripCode)
    }, { error -> handleError(context, error) })
    queue.add(stringRequest)
}

fun end(context: Context, tripCode: String) {
    val queue = Volley.newRequestQueue(context)
    val url = context.resources.getString(R.string.url_trip_end)

    val hdrs: MutableMap<String, String> = mutableMapOf<String, String>()
    val authHeader = getAccessToken(context.applicationContext)
    authHeader?.let { hdrs["Authorization"] = "Bearer $authHeader" }

    val stringRequest = TripEndRequest(tripCode, url, hdrs, { response ->
        Log.i("TripDetail", "Trip End: $response")
//        com.samrish.driver.services.getTripDetail(context, tripCode)
    }, { error -> handleError(context, error) })
    queue.add(stringRequest)
}

fun cancel(context: Context, tripCode: String) {
    val queue = Volley.newRequestQueue(context)
    val url = context.resources.getString(R.string.url_trip_cancel)

    val hdrs: MutableMap<String, String> = mutableMapOf<String, String>()
    val authHeader = getAccessToken(context.applicationContext)
    authHeader?.let { hdrs["Authorization"] = "Bearer $authHeader" }

    val stringRequest = TripCancelRequest(tripCode, url, hdrs, { response ->
        Log.i("TripDetail", "Trip Cancel: $response")
//        com.samrish.driver.services.getTripDetail(context, tripCode)
    }, { error -> handleError(context, error) })
    queue.add(stringRequest)
}

fun handleError(context: Context, error: VolleyError) {
    Log.i("TripDetail", "Request Failed with Error: $error")
    when (error) {
        is TimeoutError, is NoConnectionError -> {
            context.applicationContext?.let {
                Toast.makeText(it, "Couldn't Connect!", Toast.LENGTH_LONG).show();
            }
        }
        is AuthFailureError -> {
//            goToLogin();
        }
        is ServerError -> {
            context.applicationContext?.let {
                Toast.makeText(it, "Server error!", Toast.LENGTH_LONG).show();
            }
        }
        is NetworkError -> {
            context.applicationContext?.let {
                Toast.makeText(it, "Network error", Toast.LENGTH_LONG).show();
            }
        }
        is ParseError -> {
            context.applicationContext?.let {
                Toast.makeText(it, "Unable to parse response", Toast.LENGTH_LONG).show();
            }
        }
    }
}