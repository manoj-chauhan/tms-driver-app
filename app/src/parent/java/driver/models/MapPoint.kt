package driver.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class point(
    val latitude: Double,
    val longitude: Double
)