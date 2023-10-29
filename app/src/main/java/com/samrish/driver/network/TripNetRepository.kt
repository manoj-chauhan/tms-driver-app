package com.samrish.driver.network

import android.content.Context
import android.util.Log
import com.github.kittinunf.fuel.core.extensions.authentication
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.moshi.moshiDeserializerOf
import com.samrish.driver.R
import com.samrish.driver.viewmodels.TripsAssigned
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import org.greenrobot.eventbus.EventBus

class TripNetRepository {
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
                Log.d("TAG", "tripList:$result ")
                result.fold(
                    {

                    },
                    {
                        EventBus.getDefault().post("AUTH_FAILED")
                    }
                )

                result.get()
            }
        } catch (e: Exception) {
            null
        }
    }


    companion object {
        private var INSTANCE: TripNetRepository? = null
        fun getInstance(): TripNetRepository {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = TripNetRepository()
                }
            }
            return INSTANCE!!
        }
    }

}