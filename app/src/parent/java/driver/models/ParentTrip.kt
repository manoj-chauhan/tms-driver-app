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
    var dispatchedPlaceName: String?,
    var lastDepartureTime: String?,
    var label: String,
    var companyId: Int,
    var companyName: String,
    var companyCode: String,
    var operatorCompanyId: String,
    var operatorCompanyName: String,
    var operatorCompanyCode: String,
    var approvalStatus: String,
    var startedAt: String,
    var tripDate: String,
    var routeName: String?
)
