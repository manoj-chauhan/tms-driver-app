package com.samrish.driver.vehiclemgmt

import android.content.Context
import com.samrish.driver.network.VehicleNetRepository
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

}