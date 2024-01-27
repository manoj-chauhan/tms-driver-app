package com.drishto.driver.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Point(
    val order: Int,
    val latitude: Double,
    val longitude: Double
)