package com.drishto.driver.telemetry

import android.content.Context

interface TelemetryManager {
    suspend fun sendMatrix(telemetry: com.drishto.driver.models.Telemetry)
    suspend fun getTelemetry(): List<com.drishto.driver.database.Telemetry>
    abstract fun isNetworkAvailable(context: Context): Boolean
}