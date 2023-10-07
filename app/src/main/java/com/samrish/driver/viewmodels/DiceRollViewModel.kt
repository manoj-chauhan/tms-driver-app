package com.samrish.driver.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import com.samrish.driver.services.getTripDetail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.random.Random

data class CurrentAssignment(
    val routeName: String? = null,
    val tripCode: String? = null
)

class CurrentAssignmentViewModel : ViewModel() {

    // Expose screen UI state
    private val _currentAssignment:MutableStateFlow<CurrentAssignment?> = MutableStateFlow(null)
    val currentAssignment: StateFlow<CurrentAssignment?> = _currentAssignment.asStateFlow()

    // Handle business logic
    fun fetchAssignmentDetail(context:Context) {
        getTripDetail(
            context = context,
            tripCode = "6656",
            operatorId = 160,
            onTripDetailFetched = {
                _currentAssignment.update { assignment ->
                    CurrentAssignment(it.name, it.code)
                }
            }
        )
    }
}