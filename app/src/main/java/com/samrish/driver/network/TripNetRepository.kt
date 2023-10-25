package com.samrish.driver.network

import android.content.Context
import com.github.kittinunf.fuel.core.extensions.authentication
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.moshi.moshiDeserializerOf
import com.samrish.driver.R
import com.samrish.driver.viewmodels.TripsAssigned
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types


fun tripList(context: Context): List<TripsAssigned>? {
    val assignedTripType =
        Types.newParameterizedType(MutableList::class.java, TripsAssigned::class.java)
    val adapter: JsonAdapter<MutableList<TripsAssigned>> =
        Moshi.Builder().build().adapter(assignedTripType)

    val tripAssignmentUrl = context.resources.getString(R.string.url_trips_list)

    return try {
        getAccessToken(context)?.let {
            val (_, _, result) = tripAssignmentUrl.httpGet()
                .authentication().bearer(it)
                .responseObject(moshiDeserializerOf(adapter))
            result.get()
        }
    } catch (e: Exception) {
        null
    }


}