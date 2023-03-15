package com.samrish.driver.models

class Trip(var name: String, var code: String, var status: String, assignedDriver: AssignedDriver?, assignedVehicle: AssignedVehicle?, schedules: List<Schedule>?)
class AssignedDriver(driverId: Int, driverName: String)
class AssignedVehicle(vehicleNumber: String, typeName: String)
class Schedule(placeCode: String, order: Int, sta: String, std: String)