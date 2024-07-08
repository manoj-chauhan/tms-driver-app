package com.drishto.driver.ui.viewmodels

//import com.drishto.driver.database.Matrix
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drishto.driver.database.TelemetryRepository
import com.drishto.driver.telemetry.TelemetryManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MatrixLogViewModel @Inject constructor(private val telemetryManager: TelemetryManager, private val telemetryRepository: TelemetryRepository) : ViewModel() {

    private val _matrixList: MutableStateFlow<List<com.drishto.driver.database.Telemetry>?> = MutableStateFlow(null)
    val matrixList: StateFlow<List<com.drishto.driver.database.Telemetry>?> = _matrixList.asStateFlow()

    fun loadMatrixLog(context: Context) {

        viewModelScope.launch {
            val matList = telemetryManager.getTelemetry()
            _matrixList.update { _ ->
                matList
            }
        }
    }

     fun deleteMatrix(context: Context){
         viewModelScope.launch {
             withContext(Dispatchers.IO) {
                 telemetryRepository.deleteAllTelemetry()
             }
         }
        loadMatrixLog(context)
    }
}


