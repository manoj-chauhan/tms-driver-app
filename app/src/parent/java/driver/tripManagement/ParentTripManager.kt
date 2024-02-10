package driver.tripManagement

import driver.models.ParentPastTrip
import driver.models.ParentTrip
import driver.models.PlaceInfo
import driver.models.ProcessedPoints
import driver.models.point

interface ParentTripManager {

    fun getActiveTrips(): List<ParentTrip>?
    fun getTripLatLon(operatorId: Int, tripCode: String): List<point>

    fun getTripProcessedCoor(operatorId: Int, tripCode: String): List<ProcessedPoints>?
    fun getPastTrips(): List<ParentPastTrip>?

    fun getPlaceLatLng(placeCode:String): PlaceInfo

}