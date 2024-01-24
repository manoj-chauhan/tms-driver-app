package com.drishto.driver.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ActiveStatusDetail(
    var driverId : Int,
    var driverName : String,
    var nextLocationName : String?,
    var estimatedTime: Int?,
    var estimatedDistance: Double?,
    var travelledDistance: Double?,
    var travelTime: Int?,
    val arrivalTime:String?,
    var currentLocationName : String?,
    var actions : MutableSet<String>
)