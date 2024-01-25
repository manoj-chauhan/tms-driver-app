package driver.tripManagement

import android.content.Context
import com.drishto.driver.models.ParentTrip
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

}