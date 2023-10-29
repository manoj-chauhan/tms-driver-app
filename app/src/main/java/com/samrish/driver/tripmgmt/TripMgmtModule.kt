package com.samrish.driver.tripmgmt

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class TripMgmtModule {
    @Binds
    abstract fun providesTripManager(tripManager: TripManagerImpl): TripManager
}