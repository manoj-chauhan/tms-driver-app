package com.samrish.driver.models

import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class TripRequest(
    val tripCode: String,
    val operatorId: Int
)
