package driver.tripManagement

import com.drishto.driver.models.ParentTrip
import com.drishto.driver.models.point

interface ParentTripManager {

    fun getActiveTrips(): List<ParentTrip>?
    fun getTripLatLon(operatorId: Int, tripCode: String): List<point>
}