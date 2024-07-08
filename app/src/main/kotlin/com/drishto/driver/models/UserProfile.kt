package com.drishto.driver.models

import com.drishto.driver.ui.viewmodels.CompanyPositions
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class UserProfile (
    val name: String,
    val userName: String,
    val companiesList: List<CompanyPositions>,
    var authProvider:String,
    val id:Int
)

@JsonClass(generateAdapter = true)
data class User (
    val name: String,
)