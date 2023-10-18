package com.samrish.driver.services

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.NetworkError
import com.android.volley.NoConnectionError
import com.android.volley.ParseError
import com.android.volley.ServerError
import com.android.volley.TimeoutError
import com.android.volley.VolleyError
import com.android.volley.toolbox.Volley
import com.samrish.driver.R
import com.samrish.driver.models.Profile
import com.samrish.driver.services.requests.ProfileRequest

fun fetchDriverProfile(
    context: Context,
    onProfileFetched: (profile: Profile)->Unit
) {
    val queue = Volley.newRequestQueue(context)
    val url = context.resources.getString(R.string.url_profile)
    val hdrs: MutableMap<String, String> = mutableMapOf<String, String>()
    val authHeader = getAccessToken(context.applicationContext)
    authHeader?.let {
        hdrs["Authorization"] = "Bearer $authHeader"
        hdrs["Company-Id"] = getSelectedCompanyId(context).toString()
    }

    val stringRequest = context.applicationContext?.let { ctx ->
        ProfileRequest(
            url = url,
            headers = hdrs,
            listener = {
                onProfileFetched(it)
            },
            errorListener = {
//                    error -> handleError(ctx, error)
            }
        )
    }
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
