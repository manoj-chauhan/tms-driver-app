package driver.tripManagement

import android.content.Context
import com.drishto.driver.models.ParentTrip
import com.drishto.driver.models.point
import dagger.hilt.android.qualifiers.ApplicationContext
import driver.network.ParentTripNetRepository
import javax.inject.Inject

class ParentTripManagerImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val tripNetRepository: ParentTripNetRepository,
): ParentTripManager {

    override fun getActiveTrips(): List<ParentTrip>? {
        return tripNetRepository.fetchActiveTrips()
    }

    override fun getTripLatLon(operatorId: Int, tripCode: String): List<point> {
        return tripNetRepository.fetchTripRouteCoor(operatorId, tripCode)
    }
}