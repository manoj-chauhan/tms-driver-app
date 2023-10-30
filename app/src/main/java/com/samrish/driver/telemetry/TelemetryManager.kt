package com.samrish.driver.telemetry

import com.samrish.driver.models.Telemetry

interface TelemetryManager {
    suspend fun sendMatrix(telemetry: Telemetry)
    suspend fun getTelemetry(): List<Telemetry>
}