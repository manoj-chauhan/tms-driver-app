package com.drishto.driver.vehiclemgmt

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class VehicleMgmtModule {
    @Binds
    abstract fun providesVehicleManager(vehicleManager: VehicleManagerImpl): VehicleManager
}