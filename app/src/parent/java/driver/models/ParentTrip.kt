package driver.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ParentTrip(
    var tripCode: String,
    var tripName: String,
    var driverName: String,
    var tripId: Int,
    var vehicleNumber: String,
    var status: String,
    var companyId: Int = 1 ,
    var tripDate: String,
    var routeName: String?,
    var childName: String,
    val childSchool:String,
    val childStandard: String,
    val currentLocation: String?,
    val lastUpdateTime: String
)
