package com.drishto.driver.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TripCheckInRequest(
    val placeCode: String,
    val tripCode: String
)