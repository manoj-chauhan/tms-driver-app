package com.samrish.driver.tripmgmt

import com.samrish.driver.models.History
import com.samrish.driver.ui.viewmodels.TripsAssigned

interface TripManager {
    fun getActiveTrips(): List<TripsAssigned>?
    fun getPastTrips(pageNumber: Int): List<History>?
}