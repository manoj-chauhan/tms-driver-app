package com.samrish.driver.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import com.samrish.driver.models.AssignedDriver
import com.samrish.driver.models.AssignedVehicle
import com.samrish.driver.services.getAssignedTrips
import com.samrish.driver.services.getTripDetail
import com.samrish.driver.services.vehicleDetails
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.random.Random

data class Trip(
    val name: String,
    val code: String,
    val status: String,
    val tripDate: String,
    val operatorName: String,
    val totalTimeTravelled:Int,
    val totalDistanceCovered: Double,
    val tripId: Int
)

class TripDetailsViewModel : ViewModel() {

    // Expose screen UI state
    private val _currentAssignment:MutableStateFlow<Trip?> = MutableStateFlow(null)
    val currentTripAssignment: StateFlow<Trip?> = _currentAssignment.asStateFlow()

    // Handle business logic
    fun fetchTripDetails(context:Context, selectedCode: String, operatorId:Int) {
//        getAssignedTrips(context, onTripsListFetched = {
//            _currentAssignment.update { assignment ->
//                assignment?.let { it1 -> Trip(it1.name, it1.code,it1.status, it1.tripDate, it1.operatorName,) }
//            }
////        isApiCalled.value = true
//        })
        getTripDetail(
            context = context,
            tripCode = selectedCode,
            operatorId = operatorId,
            onTripDetailFetched = {
                _currentAssignment.update { assignment ->
                    Trip(it.name, it.code, it.status, it.tripDate, it.operatorName, it.totalTimeTravelled,it.totalDistanceCovered, it.tripId)
                }
            }
        )
    }
}

//val context = LocalContext.current
//
//    var tripList = remember {
//        mutableStateListOf<TripsAssigned>()
//    }
//
//    getAssignedTrips(context, onTripsListFetched={
//        tripList.clear()
//        tripList.addAll(it)
//    })