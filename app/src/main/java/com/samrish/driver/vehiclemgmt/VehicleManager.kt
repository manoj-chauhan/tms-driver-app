package com.samrish.driver.vehiclemgmt

import com.samrish.driver.models.History
import com.samrish.driver.ui.viewmodels.TripsAssigned

interface VehicleManager {
    fun getAssignedVehicle()
    fun getAssignmentCode(): String
}