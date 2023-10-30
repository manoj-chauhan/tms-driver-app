package com.samrish.driver.telemetry

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class TelemetryMgmtModule {
    @Binds
    abstract fun providesTelemetryManager(telemetryManager: TelemetryManagerImpl): TelemetryManager
}