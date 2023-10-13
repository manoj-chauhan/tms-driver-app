package com.samrish.driver.models

class CurrentAssignmentDetail(
    var name: String,
    var code: String,
    var status: String,
//    var actions: List<String>,
    var tripDate: String,
    var operatorName: String,
    var tripId: Int
)
class AssignedDriver(
    var driverId: Int,
    var driverName: String
    )
class AssignedVehicle(
    var vehicleNumber: String,
    var typeName: String
)

class generatedCode(
    var assignmentCode: String
)

class VehicleList(
    var vehicleId: Int,
    var vehicleNumber: String,
    var size:Int,
    var brand:String,
    var model: String,
    var createdBy: Int,
    var status: String,
    var fuelType: String
)

class TripActions(
    var actions: List<String>,
    var nextLocationName : String?,
    var estimatedTime: Int?,
    var estimatedDistance: Double?,
    var travelledDistance: Double?,
    var travelTime: Int?,
    var currentLocationName : String?

)