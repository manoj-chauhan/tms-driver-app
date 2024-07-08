package com.drishto.driver.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SwipeRefresh : ViewModel(){
    private  val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    init{
        loadstuff()
    }

    fun loadstuff() {
        viewModelScope.launch {
            _isLoading.value = true
            delay(3000L)
            _isLoading.value = false
        }
    }


}