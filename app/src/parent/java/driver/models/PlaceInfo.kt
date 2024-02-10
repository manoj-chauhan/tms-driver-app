package driver.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PlaceInfo (
    val latitude:Double,
    val longitude:Double
)