package driver.tripManagement

import androidx.navigation.NavHostController
import driver.models.ParentPastTrip
import driver.models.ParentTrip
import driver.models.ParentTripDetail
import driver.models.PlaceInfo
import driver.models.TripRouteResult
import driver.models.currentDriverLocation

interface ParentTripManager {

    fun getActiveTrips(): List<ParentTrip>?
    fun getTripLatLon(passengerTripId: Int): TripRouteResult

    fun getPastTrips(): List<ParentPastTrip>?

    fun getPlaceLatLng(placeCode:String): PlaceInfo

    fun getTripDetail(passengerTripId:Int, navHostController: NavHostController): ParentTripDetail?

    fun getDriverLoc(passengerTripId: Int):currentDriverLocation?
    fun sendFeedback(userId: Int, message: String)


}