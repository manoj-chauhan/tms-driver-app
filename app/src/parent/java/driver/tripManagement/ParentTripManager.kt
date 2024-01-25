package driver.tripManagement

import com.drishto.driver.models.ParentTrip

interface ParentTripManager {

    fun getActiveTrips(): List<ParentTrip>?
}