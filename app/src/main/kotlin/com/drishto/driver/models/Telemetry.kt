package com.drishto.driver.models

import java.time.LocalDateTime

data class Telemetry(
    val deviceIdentifier: String,
    val latitude: Double,
    val longitude: Double,
    val time:LocalDateTime = LocalDateTime.now()
)