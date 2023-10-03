package com.samrish.driver.models

import java.sql.Time

class Trip(
    var name: String,
    var code: String,
    var status: String,
    var actions: List<String>,
    var assignedDriver: AssignedDriver?,
    var assignedVehicle: AssignedVehicle?
    )
class AssignedDriver(
    var driverId: Int,
    var driverName: String
    )
class AssignedVehicle(
    var vehicleNumber: String,
    var typeName: String
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