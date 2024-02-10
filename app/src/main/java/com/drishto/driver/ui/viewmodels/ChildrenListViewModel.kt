package com.drishto.driver.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drishto.driver.models.Student
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChildrenListViewModel @Inject constructor(private val userProfileManager: com.drishto.driver.usermgmt.UserManager) : ViewModel(){


    private val  _childrenList: MutableStateFlow<List<Student>?> = MutableStateFlow(null)
    val childrensList: StateFlow<List<Student>?> = _childrenList.asStateFlow()

    fun getChildrenList(){
        viewModelScope.launch(Dispatchers.IO) {
            val childrenList = userProfileManager.childrenList()
            _childrenList.update { _ ->
                childrenList
            }
        }
    }
}