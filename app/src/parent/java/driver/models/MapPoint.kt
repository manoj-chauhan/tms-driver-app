package driver.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class point(
    val order: Int,
    val latitude: Double,
    val longitude: Double
)