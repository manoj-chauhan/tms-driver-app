package com.samrish.driver.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samrish.driver.database.AppDatabase
import com.samrish.driver.database.Matrix
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MatrixLogViewModel : ViewModel() {

    private val _matrixList: MutableStateFlow<List<Matrix>?> = MutableStateFlow(null)
    val matrixList: StateFlow<List<Matrix>?> = _matrixList.asStateFlow()

    fun loadMatrixLog(context: Context) {

        viewModelScope.launch {
            val db = AppDatabase.getDatabase(context)
            val matrixRepository = db.matrixRepository()
            val matList = matrixRepository.loadMatrices()

            _matrixList.update { _ ->
                matList
            }
        }
    }
}


