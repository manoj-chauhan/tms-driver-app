package com.samrish.driver.telemetry

import android.content.Context
import android.util.Log
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
        val tel = com.samrish.driver.database.Telemetry(
            telemetry.latitude, telemetry.longitude, telemetry.time, false
        )
        telemetryRepository.insertLocation(tel)
        val telemetryWithFalseStatus = telemetryRepository.getTelemetryWithFalseStatus()
        Log.d("TAG", "sendMatrix: $telemetryWithFalseStatus ")
        if (telemetryWithFalseStatus.isNotEmpty()) {
            telemetryWithFalseStatus.forEach { metry ->
                try {
                    val tele = Telemetry(telemetry.deviceIdentifier, metry.latitude, metry.longitude, metry.time)
                    telemetryNetRepository.sentTelemetry(tele)
                    telemetryRepository.updateTelemetryStatus(metry.id, true)
                }
                catch (e:Exception){
                    Log.d("","")
                }
            }
        }
    }

    override suspend fun getTelemetry(): List<com.samrish.driver.database.Telemetry> {
        return telemetryRepository.loadMatrices().asSequence().map { t -> com.samrish.driver.database.Telemetry( t.latitude, t.longitude, t.time, t.isDataLoaded) }.toList()
    }
}