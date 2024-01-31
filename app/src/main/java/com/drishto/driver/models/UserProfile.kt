package com.drishto.driver.models

import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class UserProfile (
    val userName: String
)