package driver.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ParentTripDetail(
    val passengerName: String,
    val parentName: String,
    val dateOfBirth: String,
    val boardingPlaceId: Int,
    val boardingPlaceName: String,
    val deBoardingPlaceId: Int,
    val deBoardingPlaceName: String,
    val boardingTime: String,
    val deBoardingTime: String,
    val status: String,
    val tripDate: String,
    val tripTime: String,
    val driverId: Int?,
    val driverName: String?,
    val vehicleNumber: String?,
    val estDistance: Double,
    val estTime: Double,
    val distanceCovered: Double,
    val travelTime: Double,
    val travelDistance: Double,
    val delay: Int
 )