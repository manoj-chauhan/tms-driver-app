package com.samrish.driver.telemetry

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import com.samrish.driver.auth.AuthManager
import com.samrish.driver.database.TelemetryRepository
import com.samrish.driver.models.Telemetry
import com.samrish.driver.network.TelemetryNetRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TelemetryManagerImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val authManager: AuthManager,
    private val telemetryNetRepository: TelemetryNetRepository,
    private val telemetryRepository: TelemetryRepository
) : TelemetryManager {
    override suspend fun sendMatrix(telemetry: Telemetry) {
        val tel = com.samrish.driver.database.Telemetry(
            telemetry.latitude, telemetry.longitude, telemetry.time, false
        )
        telemetryRepository.insertLocation(tel)
        Log.d(
            "Size Of New List",
            "sentTelemetry: ${telemetryRepository.getTelemetryWithFalseStatus().size} "
        )
        if (isNetworkAvailable(context)) {
            withContext(Dispatchers.IO) {
                var telemetryWithFalseStatus: List<com.samrish.driver.database.Telemetry> =
                    telemetryRepository.getTelemetryWithFalseStatus()
                if (telemetryWithFalseStatus.isNotEmpty()) {
                    telemetryWithFalseStatus.forEach { metry ->
                        val tele = Telemetry(
                            telemetry.deviceIdentifier,
                            metry.latitude,
                            metry.longitude,
                            metry.time
                        )
                        try {
                            telemetryNetRepository.sentTelemetry(tele)
                            Log.d("Matrix Id", "sendMatrix: ${metry.id}")
                            telemetryRepository.updateTelemetryStatus(metry.id, true)
                            telemetryWithFalseStatus = telemetryRepository.getTelemetryWithFalseStatus()
                            Log.d(
                                "Telemetry after one false Status ",
                                "sendMatrix: $telemetryWithFalseStatus"
                            )
                        } catch (e: Exception) {
                            Log.d("", "")
                        }
                    }
                }
            }
        } else {
            Log.d("No Internet ", "No sending Matrix: $tel")
        }
    }

    override suspend fun getTelemetry(): List<com.samrish.driver.database.Telemetry> {
        return telemetryRepository.loadMatrices().asSequence().map { t ->
            com.samrish.driver.database.Telemetry(
                t.latitude,
                t.longitude,
                t.time,
                t.isDataLoaded
            )
        }.toList()
    }

    override fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
}