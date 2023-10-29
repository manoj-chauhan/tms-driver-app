package com.samrish.driver.ui.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.kittinunf.fuel.core.extensions.authentication
import com.github.kittinunf.fuel.core.response
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.moshi.moshiDeserializerOf
import com.samrish.driver.R
import com.samrish.driver.models.AssignmentHistory
import com.samrish.driver.models.History
import com.samrish.driver.network.getAccessToken
import com.samrish.driver.tripmgmt.TripManager
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
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
