package com.samrish.driver.tripmgmt

import com.samrish.driver.viewmodels.TripsAssigned

interface TripManager {
    fun getActiveTrips(): List<TripsAssigned>?
    fun getPastTrips()
}