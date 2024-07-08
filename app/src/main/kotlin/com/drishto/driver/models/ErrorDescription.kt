package com.drishto.driver.models

import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class errorDescription(
    val  errorCode :  String,
    val errorDescription:String,
    val errorShortDescription: String
)

@JsonClass(generateAdapter = true)
data class errorDescription500(
    val message:String
)
