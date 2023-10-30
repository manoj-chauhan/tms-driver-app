package com.samrish.driver.telemetry

import android.content.Context
import com.samrish.driver.auth.AuthManager
import com.samrish.driver.database.TelemetryRepository
import com.samrish.driver.models.Telemetry
import com.samrish.driver.network.TelemetryNetRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class TelemetryManagerImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val authManager: AuthManager,
    private val telemetryNetRepository: TelemetryNetRepository,
    private val telemetryRepository: TelemetryRepository
): TelemetryManager {
    override suspend fun sendMatrix(telemetry: Telemetry) {
        telemetryNetRepository.sentTelemetry(telemetry)
        val tel = com.samrish.driver.database.Telemetry(
            telemetry.latitude, telemetry.longitude, telemetry.time.toString()
        )
        telemetryRepository.insertLocation(tel)
    }

    override suspend fun getTelemetry(): List<Telemetry> {
        return telemetryRepository.loadMatrices().asSequence().map { t -> Telemetry("", t.latitude, t.longitude) }.toList()
    }
}