package driver.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ParentTrip(
    var tripCode: String,
    var tripName: String,
    var passengerTripId:Int,
    var driverName: String?,
    var vehicleNumber: String?,
    var status: String,
    var companyId: Int = 1 ,
    var tripDate: String,
    var routeName: String?,
    var childName: String,
    val childSchool:String,
    val childStandard: String,
    val currentLocation: String?,
    val lastUpdateTime: String,
    val deBoardingPlaceName:String,
    val deBoardingPlaceTime: String,
    val boardingPlaceName: String,
    val boardingPlaceTime: String,
    val boardingPlaceId:String ,
    val deBoardingPlaceId:String,
    val tripTime:String,
    val routeId:Int?,
    val delay: Int,
    val estDistance: Int,
    val estTime: Int
)




