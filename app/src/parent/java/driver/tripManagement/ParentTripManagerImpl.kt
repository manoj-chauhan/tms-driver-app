package driver.tripManagement

import android.content.Context
import androidx.navigation.NavHostController
import dagger.hilt.android.qualifiers.ApplicationContext
import driver.models.ParentPastTrip
import driver.models.ParentTrip
import driver.models.ParentTripDetail
import driver.models.PlaceInfo
import driver.models.ProcessedPoints
import driver.models.currentDriverLocation
import driver.models.point
import driver.network.ParentTripNetRepository
import driver.network.PlaceInfoNetRepository
import javax.inject.Inject

class ParentTripManagerImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val tripNetRepository: ParentTripNetRepository,
    private val placeInforepository: PlaceInfoNetRepository,

    ): ParentTripManager {

    override fun getActiveTrips(): List<ParentTrip>? {
        return tripNetRepository.fetchActiveTrips()
    }

    override fun getTripLatLon(operatorId: Int, tripCode: String): List<point> {
        return tripNetRepository.fetchTripRouteCoor(operatorId, tripCode)
    }

    override fun getTripProcessedCoor(operatorId: Int, tripCode: String): List<ProcessedPoints>? {
        return tripNetRepository.fetchTripProcessed(operatorId, tripCode)
    }

    override fun getPastTrips(): List<ParentPastTrip>? {
        return tripNetRepository.fetchPastTrips()
    }

    override fun getPlaceLatLng(placeCode: String): PlaceInfo {
        return placeInforepository.fetchPlaceLatitudeLongitude(placeCode)
    }

    override fun getTripDetail(passengerTripId:Int, navHostController: NavHostController): ParentTripDetail? {
        return tripNetRepository.fetchParentTripDetail(passengerTripId, navHostController)
    }

    override fun getDriverLoc(passengerTripId: Int): currentDriverLocation? {
        return tripNetRepository.fetchDriverLiveLoc(passengerTripId)
    }
}