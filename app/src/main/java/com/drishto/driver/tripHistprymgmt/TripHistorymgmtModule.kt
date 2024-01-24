package com.drishto.driver.tripHistprymgmt

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class TripHistoryMgmtModule {
    @Binds
    abstract fun tripHistory(tripHistoryManager: TripHistoryManagerImpl): TripHistoryManager
}