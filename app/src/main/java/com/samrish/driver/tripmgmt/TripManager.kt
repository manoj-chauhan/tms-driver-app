package com.samrish.driver.tripmgmt

import androidx.navigation.NavHostController
import com.samrish.driver.models.ActiveStatusDetail
import com.samrish.driver.models.Documents
import com.samrish.driver.models.History
import com.samrish.driver.models.Schedule
import com.samrish.driver.models.TripDetail
import com.samrish.driver.ui.viewmodels.TripsAssigned

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