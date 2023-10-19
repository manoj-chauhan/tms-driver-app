package com.samrish.driver.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Documents(
    val tripId: Int,
    val type: String,
    val name: String,
    val fileName: String,
    val  createdBy : Int,
    val  createdAt :  String ,
    val  updatedBy : Int,
    val  lastUpdatedAt :  String
)