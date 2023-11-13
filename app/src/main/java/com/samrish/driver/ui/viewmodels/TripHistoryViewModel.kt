package com.samrish.driver.ui.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samrish.driver.tripHistprymgmt.TripHistoryManager
import com.squareup.moshi.JsonClass
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@JsonClass(generateAdapter = true)
data class TripHistory (
    val state: String,
    val description: String,
    val time: String
)


@HiltViewModel
class TripHistoryViewModel @Inject constructor(private val tripHistoryManager: TripHistoryManager) : ViewModel() {
    private val  _historyDetail: MutableStateFlow<List<TripHistory>?> = MutableStateFlow(null)
    val tripHistory:  StateFlow<List<TripHistory>?> = _historyDetail.asStateFlow()

    fun fetchTripHistoryDetail(context: Context, tripCode:String){

        viewModelScope.launch(Dispatchers.IO) {
            val historyList = tripHistoryManager.getTripHistory(tripCode)
            _historyDetail.update { _ ->
                historyList
            }
            Log.d("TAG", "fetchTripHistoryDetail: $historyList ")
        }

    }
    }


