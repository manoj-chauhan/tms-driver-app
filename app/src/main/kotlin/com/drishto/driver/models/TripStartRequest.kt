package com.drishto.driver.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TripStartRequest(
    val deviceIdentifier: String, val tripCode: String
)