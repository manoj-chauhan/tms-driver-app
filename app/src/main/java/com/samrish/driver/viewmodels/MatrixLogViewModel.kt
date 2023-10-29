package com.samrish.driver.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samrish.driver.database.AppDatabase
import com.samrish.driver.database.Matrix
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MatrixLogViewModel @Inject constructor(database : AppDatabase) : ViewModel() {

    val database : AppDatabase;
    init {
        this.database = database
    }

    private val _matrixList: MutableStateFlow<List<Matrix>?> = MutableStateFlow(null)
    val matrixList: StateFlow<List<Matrix>?> = _matrixList.asStateFlow()

    fun loadMatrixLog(context: Context) {

        viewModelScope.launch {
            val matrixRepository = database.matrixRepository()
            val matList = matrixRepository.loadMatrices()

            _matrixList.update { _ ->
                matList
            }
        }
    }
}


