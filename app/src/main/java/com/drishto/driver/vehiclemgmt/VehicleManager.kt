package com.drishto.driver.vehiclemgmt

import android.content.Context
import com.drishto.driver.models.DriverPlans

interface VehicleManager {
    fun getAssignedVehicle()
    fun getAssignmentCode(): String

    fun getDriverPlan(context: Context):List<DriverPlans>?
}