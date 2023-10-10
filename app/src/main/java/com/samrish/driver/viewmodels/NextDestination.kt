package com.samrish.driver.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.samrish.driver.models.Locations
import com.samrish.driver.services.getTripActions
import com.samrish.driver.services.getTripSchedule
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class TripActions(
    var actions: List<String>,
    var nextLocationName : String,
    var estimatedTime: Int,
    var estimatedDistance: Double,
    var travelledDistance: Double,
    var travelTime: Int

)




class TripNextDestination : ViewModel() {

    // Expose screen UI state
    private val _currentAssignment: MutableStateFlow<TripActions?> = MutableStateFlow(null)
    val tripNextDestinationActions: StateFlow<TripActions?> = _currentAssignment.asStateFlow()

    // Handle business logic
    fun getTripActions(context: Context,  tripId:Int, operatorId: Int) {

//        getTripSchedule(
//            context = context,
//            tripCode = selectedCode,
//            operatorId = operatorId,
//            onTripScheduleFetched = {
//                _currentAssignment.update { assignment ->
//                    Schedule(it.totalDistance, it.totalEstimatedDistance, it.totalTime, it.totalEstimatedTime, it.locations)
//                }
//            }
//        )
        getTripActions(
            context = context,
            tripId = tripId,
            operatorId = operatorId,
            onTripActionsFetched = {
                _currentAssignment.update { assignment ->
                    TripActions(
                        it.actions,
                        it.nextLocationName,
                        it.estimatedTime,
                        it.estimatedDistance,
                        it.travelledDistance,
                        it.travelTime
                    )
                }
            }
        )

    }
}