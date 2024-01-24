package com.drishto.driver.telemetry

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import com.drishto.driver.auth.AuthManager
import com.drishto.driver.database.TelemetryRepository
import com.drishto.driver.models.Telemetry
import com.drishto.driver.network.TelemetryNetRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class TelemetryManagerImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val authManager: AuthManager,
    private val telemetryNetRepository: TelemetryNetRepository,
    private val telemetryRepository: TelemetryRepository
) : TelemetryManager {
    override suspend fun sendMatrix(telemetry: Telemetry) {
        val json = com.drishto.driver.database.Telemetry(telemetry.latitude, telemetry.longitude, telemetry.time, false, telemetry.deviceIdentifier)
        Log.d("TAG", "sendMatrix:$json ")
        saveTelemetryToDatabase(json)
    }

    override suspend fun getTelemetry(): List<com.drishto.driver.database.Telemetry> {
        return telemetryRepository.loadMatrices().asSequence().map { t ->
            com.drishto.driver.database.Telemetry(
                t.latitude,
                t.longitude,
                t.time,
                t.isDataLoaded,
                t.deviceIdentifier
            )
        }.toList()
    }

    override fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    private fun saveTelemetryToDatabase(telemetry: com.drishto.driver.database.Telemetry) {
        Log.d("Data", "saveTelemetryToDatabase: $telemetry ")
        CoroutineScope(Dispatchers.IO).launch {
            telemetryRepository.insertLocation(
                telemetry
            )
        }
    }

}