package com.samrish.driver.tripmgmt

import android.content.Context
import com.samrish.driver.auth.AuthManager
import com.samrish.driver.database.TripRepository
import com.samrish.driver.models.ActiveStatusDetail
import com.samrish.driver.models.Documents
import com.samrish.driver.models.History
import com.samrish.driver.models.Schedule
import com.samrish.driver.models.TripDetail
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
        return tripNetRepository.fetchActiveTrips()
    }

    override fun getPastTrips(pageNumber: Int): List<History>? {
        return tripNetRepository.fetchPastTrips(pageNumber)
    }
    override fun getDocuments(tripId: Int, operatorId:Int): MutableList<Documents>?{
        return tripNetRepository.fetchTripDocuments(tripId, operatorId)
    }

    override fun getTripDetail(tripCode: String, operatorId: Int): TripDetail {
        return tripNetRepository.fetchTripDetail(tripCode, operatorId)
    }

    override fun getTripStatus(tripId: Int, operatorId: Int): ActiveStatusDetail {
        return tripNetRepository.fetchTripStatus(tripId, operatorId)
    }

    override fun getTripSchedule(tripCode: String, operatorId: Int):Schedule {
        return tripNetRepository.fetchTripSchedule(tripCode, operatorId)
    }
}