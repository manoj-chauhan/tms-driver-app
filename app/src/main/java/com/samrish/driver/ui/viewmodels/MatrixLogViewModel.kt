package com.samrish.driver.ui.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samrish.driver.database.AppDatabase
import com.samrish.driver.models.Telemetry
import com.samrish.driver.telemetry.TelemetryManager
//import com.samrish.driver.database.Matrix
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MatrixLogViewModel @Inject constructor(private val telemetryManager: TelemetryManager) : ViewModel() {

    private val _matrixList: MutableStateFlow<List<Telemetry>?> = MutableStateFlow(null)
    val matrixList: StateFlow<List<Telemetry>?> = _matrixList.asStateFlow()

    fun loadMatrixLog(context: Context) {

        viewModelScope.launch {
            val matList = telemetryManager.getTelemetry()
            _matrixList.update { _ ->
                matList
            }
        }
    }
}


