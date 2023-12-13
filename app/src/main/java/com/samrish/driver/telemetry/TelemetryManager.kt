package com.samrish.driver.telemetry

import android.content.Context

interface TelemetryManager {
    suspend fun sendMatrix(telemetry: com.samrish.driver.models.Telemetry)
    suspend fun getTelemetry(): List<com.samrish.driver.database.Telemetry>
    abstract fun isNetworkAvailable(context: Context): Boolean
    fun startTelemetryConsumer(list: List<com.samrish.driver.database.Telemetry>, onConsumerStarted:()->Unit)
    fun stopTelemetryConsumer()
}