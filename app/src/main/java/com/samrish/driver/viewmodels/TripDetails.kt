package com.samrish.driver.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samrish.driver.models.AssignedDriver
import com.samrish.driver.models.AssignedVehicle
import com.samrish.driver.models.Locations
import com.samrish.driver.services.getAssignedTrips
import com.samrish.driver.services.getTripActions
import com.samrish.driver.services.getTripDetail
import com.samrish.driver.services.vehicleDetails
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlin.random.Random


data class CurrentAssignmentDetail(
    var name: String,
    var code: String,
    var status: String,
    var tripDate: String,
    var operatorName: String,
    var tripId: Int
)

data class Schedule(
    var totalDistance: Double,
    var totalEstimatedDistance: Double,
    var totalTime: Int,
    var totalEstimatedTime: Int,
    var locations: List<Locations>,
)

data class Locations(
    var placeCode: String,
    var placeName: String,
    var estDistance: Double,
    var actualDistance: Double,
    var scheduledArrivalTime: String,
    var scheduledDepartureTime: String
)

data class Documents(
    val name: String,
    val type: String,
    val url: String
)

//data class CurrentStatusDetail(
//    val actions: List<String>,
//    var estDistance: Double?,
//    var actualDistance: Double?,
//    var actualTime: Int?,
//    var actualEstimatedTime: Int?,
//    var currentLocation: String?,
//    var nextLocation: String?,
//    var lastLocation:String?,
//)

data class TripActions(
    var actions: List<String>,
    var nextLocationName : String?,
    var estimatedTime: Int?,
    var estimatedDistance: Double?,
    var travelledDistance: Double?,
    var travelTime: Int?,
    var currentLocation : String?
)
class TripDetailsViewModel : ViewModel() {

    private val _currentAssignment:MutableStateFlow<CurrentAssignmentDetail?> = MutableStateFlow(null)
    val currentTripAssignment: StateFlow<CurrentAssignmentDetail?> = _currentAssignment.asStateFlow()


    private val tripNextDestination: MutableStateFlow<TripActions?> = MutableStateFlow(null)
    val tripNextDestinationActions: StateFlow<TripActions?> = tripNextDestination.asStateFlow()
    fun fetchTripDetails(context:Context, selectedCode: String, operatorId:Int, tripId: Int) {
        val job1 = viewModelScope.launch {
            getTripAssignmentDetails(context, selectedCode, operatorId)
        }

        val job2 =  viewModelScope.launch {
            getTripActions(
                context = context,
                tripId = tripId,
                operatorId = operatorId,
                onTripActionsFetched = {
                    tripNextDestination.update { _ ->
                        var currentLocationName: String? =""
                        val nextLocationName: String? = it.nextLocationName

                        if (it.currentLocationName != null) {
                            currentLocationName = it.currentLocationName
                            TripActions(it.actions, null, null, null, null, null, currentLocationName)

                        }else if(nextLocationName!= null) {
                            TripActions(
                                it.actions,
                                it.nextLocationName,
                                it.estimatedTime,
                                it.estimatedDistance,
                                it.travelledDistance,
                                it.travelTime,
                                null
                            )
                        }else
                        {
                            TripActions(it.actions, null, null, null, null, null, null)
                        }

                    }
                }
            )
        }




    }
    private fun getTripAssignmentDetails(context: Context, selectedCode: String, operatorId: Int){
        getTripDetail(
            context = context,
            tripCode = selectedCode,
            operatorId = operatorId,
            onTripDetailFetched = {
                _currentAssignment.update { assignment ->
                    CurrentAssignmentDetail(it.name, it.code, it.status, it.tripDate, it.operatorName, it.tripId)
                }
            }
        )
    }
}

//    var currentLocation : String?


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