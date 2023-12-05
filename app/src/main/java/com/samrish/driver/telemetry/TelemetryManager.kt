package com.samrish.driver.telemetry

import android.content.Context
import com.samrish.driver.models.Telemetry

interface TelemetryManager {
    suspend fun sendMatrix(telemetry: Telemetry)
    suspend fun getTelemetry(): List<com.samrish.driver.database.Telemetry>
    abstract fun isNetworkAvailable(context: Context): Boolean
}