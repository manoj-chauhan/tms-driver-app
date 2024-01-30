package com.drishto.driver.models

import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
class Student(
    val studentName: String,
    val standard: Int
)