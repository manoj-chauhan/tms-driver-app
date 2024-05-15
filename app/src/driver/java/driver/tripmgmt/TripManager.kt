package com.drishto.driver.tripmgmt

import androidx.navigation.NavHostController
import com.drishto.driver.models.ActiveStatusDetail
import com.drishto.driver.models.Documents
import com.drishto.driver.models.History
import com.drishto.driver.models.Schedule
import com.drishto.driver.models.TripDetail
import driver.ui.viewmodels.TripsAssigned

interface TripManager {
    fun getActiveTrips(): List<TripsAssigned>?
    fun getPastTrips(pageNumber: Int): List<History>?
    fun getDocuments(tripId:Int, operatorId:Int): MutableList<Documents>?
    fun getTripDetail(tripCode:String, operatorId:Int): TripDetail
    fun getTripStatus(tripId: Int, operatorId: Int): ActiveStatusDetail
    fun getTripSchedule(tripCode: String, operatorId: Int): Schedule
    fun startTrip(tripCode: String, operatorId: Int, deviceIdentifier: String)
    fun checkInTrip(placeCode:String, tripCode:String, operatorId: Int)
    fun departTrip(tripCode:String, operatorId: Int)
    fun cancelTrip(tripCode: String, operatorId: Int, navController: NavHostController)
    fun endTrip(tripCode: String, operatorId: Int, navController: NavHostController)
}