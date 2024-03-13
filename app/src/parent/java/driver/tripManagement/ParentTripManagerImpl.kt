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
import driver.network.FeedBackNetRepository
import driver.network.ParentTripNetRepository
import driver.network.PlaceInfoNetRepository
import javax.inject.Inject

class ParentTripManagerImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val tripNetRepository: ParentTripNetRepository,
    private val placeInforepository: PlaceInfoNetRepository,
    private val feedbackrepository: FeedBackNetRepository,

    ): ParentTripManager {

    override fun getActiveTrips(): List<ParentTrip>? {
        return tripNetRepository.fetchActiveTrips()
    }

    override fun getTripLatLon(passengerTripId: Int): List<point>? {
        return tripNetRepository.fetchTripRouteCoor(passengerTripId)
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

    override fun sendFeedback(userId: Int, message: String) {
        return feedbackrepository.sendFeedback(userId, message)
    }
}