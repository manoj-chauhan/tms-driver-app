package com.samrish.driver.services

import android.content.Context
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

