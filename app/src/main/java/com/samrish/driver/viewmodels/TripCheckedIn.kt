package com.samrish.driver.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import com.samrish.driver.models.Locations
import com.samrish.driver.services.getTripSchedule
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class Schedule(
    var totalDistance: Double,
    var totalEstimatedDistance: Double,
    var totalTime: Int,
    var totalEstimatedTime: Int,
    var locations: List<Locations?>,
)

data class Locations(
    var placeCode: String,
    var placeName: String,
    var estDistance: Double,
    var actualDistance: Double,
    var scheduledArrivalTime: String,
    var scheduledDepartureTime: String
)


class TripCheckedInViewModel : ViewModel() {

    // Expose screen UI state
    private val _currentAssignment: MutableStateFlow<Schedule?> = MutableStateFlow(null)
    val tripCheckedIn: StateFlow<Schedule?> = _currentAssignment.asStateFlow()

    // Handle business logic
    fun getTripLocations(context: Context, selectedCode: String, operatorId:Int) {

        getTripSchedule(
            context = context,
            tripCode = selectedCode,
            operatorId = operatorId,
            onTripScheduleFetched = {
                _currentAssignment.update { assignment ->
                    Schedule(it.totalDistance, it.totalEstimatedDistance, it.totalTime, it.totalEstimatedTime, it.locations)
                }
            }
        )
    }
}