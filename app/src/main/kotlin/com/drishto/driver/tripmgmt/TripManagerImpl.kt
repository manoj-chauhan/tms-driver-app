package com.drishto.driver.tripmgmt

import android.content.Context
import androidx.navigation.NavHostController
import com.drishto.driver.auth.AuthManager
import com.drishto.driver.database.TripRepository
import com.drishto.driver.models.ActiveStatusDetail
import com.drishto.driver.models.Documents
import com.drishto.driver.models.History
import com.drishto.driver.models.Schedule
import com.drishto.driver.models.TripDetail
import com.drishto.driver.network.TripNetRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import com.drishto.driver.ui.viewmodels.TripsAssigned
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

    override fun startTrip(tripCode: String, operatorId: Int, deviceIdentifier: String) {
        return tripNetRepository.startTrip(tripCode, operatorId, deviceIdentifier)
    }

    override fun checkInTrip(placeCode: String, tripCode: String, operatorId: Int) {
        return tripNetRepository.checkInTrip(placeCode, tripCode, operatorId)
    }

    override fun departTrip(tripCode: String, operatorId: Int) {
        return tripNetRepository.departTrip(tripCode, operatorId)
    }

    override fun cancelTrip(tripCode: String, operatorId: Int, navController: NavHostController) {
        return tripNetRepository.cancelTrip(tripCode, operatorId, navController)
    }

    override fun endTrip(tripCode: String, operatorId: Int, navController: NavHostController) {
        return tripNetRepository.endTrip(tripCode, operatorId, navController)
    }
}