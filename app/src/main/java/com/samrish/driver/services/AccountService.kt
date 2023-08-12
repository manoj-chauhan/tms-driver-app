package com.samrish.driver.services

import android.content.Context
import com.android.volley.toolbox.Volley
import com.samrish.driver.R
import com.samrish.driver.models.Company
import com.samrish.driver.models.Profile
import com.samrish.driver.models.UserProfile
import com.samrish.driver.services.requests.ProfileRequest
import com.samrish.driver.services.requests.UserCompaniesRequest
import com.samrish.driver.services.requests.UserProfileRequest

fun fetchDriverProfile(
    context: Context,
    onProfileFetched: (profile: Profile)->Unit
) {
    val queue = Volley.newRequestQueue(context)
    val url = context.resources.getString(R.string.url_profile)
    val hdrs: MutableMap<String, String> = mutableMapOf<String, String>()
    val authHeader = context.let { SessionStorage().getAccessToken(it.applicationContext) }
    authHeader?.let { hdrs["Authorization"] = "Bearer $authHeader" }

    val stringRequest = context.applicationContext?.let { ctx ->
        ProfileRequest(
            url = url,
            headers = hdrs,
            listener = {
                onProfileFetched(it)
            },
            errorListener = {
                    error -> handleError(ctx, error)
            }
        )
    }
    queue.add(stringRequest)
}

fun getUserProfile(
    context: Context,
    onProfileFetched: (profile: UserProfile)->Unit
) {
    val queue = Volley.newRequestQueue(context)
    val url = context.resources.getString(R.string.url_user_profile)
    val hdrs: MutableMap<String, String> = mutableMapOf<String, String>()
    val authHeader = context.let { SessionStorage().getAccessToken(it.applicationContext) }
    authHeader?.let { hdrs["Authorization"] = "Bearer $authHeader" }

    val stringRequest = context.applicationContext?.let { ctx ->
        UserProfileRequest(
            url = url,
            headers = hdrs,
            listener = {
                onProfileFetched(it)
            },
            errorListener = {
                    error -> handleError(ctx, error)
            }
        )
    }
    queue.add(stringRequest)
}


fun getCompanies(
    context: Context,
    onCompaniesFetched: (companies: List<Company>)->Unit
) {
    val queue = Volley.newRequestQueue(context)
    val url = context.resources.getString(R.string.url_user_companies)
    val hdrs: MutableMap<String, String> = mutableMapOf<String, String>()
    val authHeader = context.let { SessionStorage().getAccessToken(it.applicationContext) }
    authHeader?.let { hdrs["Authorization"] = "Bearer $authHeader" }

    val stringRequest = context.applicationContext?.let { ctx ->
        UserCompaniesRequest(
            url = url,
            headers = hdrs,
            listener = {
                onCompaniesFetched(it)
            },
            errorListener = {
                    error -> handleError(ctx, error)
            }
        )
    }
    queue.add(stringRequest)
}

