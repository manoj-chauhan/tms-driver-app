package com.drishto.driver.ui.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drishto.driver.models.AssignmentHistory
import com.drishto.driver.tripmgmt.TripManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(private val tripManager: TripManager)  : ViewModel() {
    private val  _historyDetail: MutableStateFlow<AssignmentHistory?> = MutableStateFlow(null)
    val assignmentDetail: StateFlow<AssignmentHistory?> =  _historyDetail.asStateFlow()

    private var currentPage: Int = 0

    //ToDo: Page Number Handling need to be done, page number should be part of state.
    fun incrementPage() {
        currentPage++
    }


    fun fetchHistoryDetail(context: Context){

        viewModelScope.launch(Dispatchers.IO) {
            val historyDetail = tripManager.getPastTrips(currentPage)
            _historyDetail.update { _ ->
                AssignmentHistory(
                    historyDetail
                )
            }
        }

    }
}
