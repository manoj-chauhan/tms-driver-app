package com.samrish.driver.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Schedule(
    var locations : List<ScheduleLocation>,
)

@JsonClass(generateAdapter = true)
class ScheduleLocation(
    var placeCode: String,
    var placeName: String,
    var estDistance: Double,
    var order: Int,
    val scheduledArrivalTime :  String ,
    val scheduledDepartureTime :  String ,
    val actualArrivalTime :  String? ,
    val actualDepartureTime :  String?
)