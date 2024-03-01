package com.drishto.driver.vehiclemgmt

import android.content.Context
import com.drishto.driver.models.DriverPlans
import com.drishto.driver.network.VehicleNetRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class VehicleManagerImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val vehicleNetRepository: VehicleNetRepository
): VehicleManager {

    override fun getAssignedVehicle() {
        TODO("Not yet implemented")
    }

    override fun getAssignmentCode(): String {
        return vehicleNetRepository.generateAssignmentCode()!!
    }

    override fun getDriverPlan(context: Context): List<DriverPlans>? {
        return vehicleNetRepository.getDriverPlans()
    }

}