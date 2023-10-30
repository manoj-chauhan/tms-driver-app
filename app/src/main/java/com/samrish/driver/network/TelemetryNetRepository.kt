package com.samrish.driver.network

import android.content.Context
import com.samrish.driver.models.Telemetry
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class TelemetryNetRepository @Inject constructor(@ApplicationContext private val context: Context)  {
    fun sentTelemetry(telemetry: Telemetry) {
        
    }
}