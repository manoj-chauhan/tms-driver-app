package com.samrish.driver.tripmgmt

import android.content.Context
import com.samrish.driver.auth.AuthManager
import com.samrish.driver.database.TripRepository
import com.samrish.driver.network.TripNetRepository
import com.samrish.driver.ui.viewmodels.TripsAssigned
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class TripManagerImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val authManager: AuthManager,
    private val tripNetRepository: TripNetRepository,
    private val tripRepository: TripRepository
): TripManager {

    override fun getActiveTrips(): List<TripsAssigned>? {
        return tripNetRepository.tripList(context)
    }

    override fun getPastTrips() {
        TODO("getPastTrips() Not yet implemented")
    }
}