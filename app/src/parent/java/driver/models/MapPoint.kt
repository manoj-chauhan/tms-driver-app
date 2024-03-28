package driver.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class point(
    val latitude: Double,
    val longitude: Double
)

sealed class TripRouteResult {
    data class Success(val points: List<point>) : TripRouteResult()
    data class Error(val message: String) : TripRouteResult()
}