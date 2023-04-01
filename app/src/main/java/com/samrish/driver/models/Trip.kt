package com.samrish.driver.models

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
