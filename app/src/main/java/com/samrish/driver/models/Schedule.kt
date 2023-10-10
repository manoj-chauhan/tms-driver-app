package com.samrish.driver.models;

import org.json.JSONArray

class Schedule(
    var totalDistance : Double,
    var totalEstimatedDistance: Double,
    var totalTime: Int,
    var totalEstimatedTime: Int,
    var locations : List<Locations>,
)

class Locations(
    var placeCode: String,
    var placeName: String,
    var estDistance: Double,
    var actualDistance: Double,
    var scheduledArrivalTime: String,
    var scheduledDepartureTime: String
)