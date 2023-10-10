package com.samrish.driver.models

class Trip(
    var name: String,
    var code: String,
    var status: String,
//    var actions: List<String>,
    var tripDate: String,
    var operatorName: String,
    var totalDistanceCovered: Double,
    var totalTimeTravelled: Int,
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

class TripsAssigned(
    var tripCode: String,
    var tripName:String,
    var status: String,
    var label: String,
    var companyName: String,
    var companyCode: String,
    var operatorCompanyName: String,
    var operatorCompanyCode: String,
    var operatorCompanyId: Int,
    var tripDate: String,
    var tripId: Int
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
    var nextLocationName : String,
    var estimatedTime: Double,
    var estimatedDistance: Double,
    var travelledDistance: Double,
    var travelTime: Double

)