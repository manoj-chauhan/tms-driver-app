package com.drishto.driver.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TripDetail(
    var status : String,
    var tripId : Int,
    var tripCode : String,
    var tripName : String,
    var createdBy : String,
    var createdAt : String,
    var updatedBy : String,
    var lastUpdatedAt : String,
    var companyId : Int,
    var companyName : String,
    var operatorId : Int,
    var operatorName : String,
    var approvalStatus : String,
    var label : String,
    var routeId : Int,
    var tripDateTime : String,
)