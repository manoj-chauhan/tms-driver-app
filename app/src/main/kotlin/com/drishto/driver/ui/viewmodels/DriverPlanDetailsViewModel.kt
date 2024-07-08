package com.drishto.driver.ui.viewmodels

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.drishto.driver.DriverPlan.DriverPlanManager
import com.drishto.driver.errormgmt.ErrManager
import com.drishto.driver.models.ChildrenList
import com.drishto.driver.models.childrenAddPlan
import com.drishto.driver.models.childrenEditPlan
import com.drishto.driver.models.scheduleList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DriverPlanDetailsViewModel @Inject constructor(
    private val errorManager: ErrManager,
    application: Application,
    private val driverPlan: DriverPlanManager
) : AndroidViewModel(application) {
    private val _childrenList: MutableStateFlow<List<ChildrenList>?> = MutableStateFlow(null)
    val childrenList: StateFlow<List<ChildrenList>?> = _childrenList.asStateFlow()


    private val _planList: MutableStateFlow<scheduleList?> = MutableStateFlow(null)
    val planList: StateFlow<scheduleList?> = _planList.asStateFlow()

    fun getChildrenList(context: Context, operatorId: Int, planId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val tripList = driverPlan.getChildrenList(operatorId, planId)
            _childrenList.update { _ ->
                tripList
            }
            Log.d("TAG", "fetchParentTrip: $tripList")
        }

    }


    fun fetchSchedule(context: Context, operatorId: Int, planId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val planList = driverPlan.getTripSchedule(operatorId, planId)
            _planList.update { _ ->
                planList
            }
            Log.d("TAG", "fetchParentTrip: $planList")
        }

    }

    fun addStudentInPlan(
        name: String,
        guardianName: String,
        selectedDate: String,
        selectedText: String,
        standard: String,
        schoolName: String,
        schoolAddress: String,
        primarynumber: String,
        secondarynumber: String,
        boardingPlanScheduleId: Int,
        deboardingPlanScheduleId: Int,
        planId: Int,
        operatorId: Int,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val studentRequest = childrenAddPlan(name, schoolName,  primarynumber, standard, selectedText, secondarynumber, selectedDate, guardianName, schoolAddress, planId, boardingPlanScheduleId, deboardingPlanScheduleId)
                Log.d("hiiii", "addStudent: $studentRequest")
                driverPlan.addStudent(
                    name,
                    schoolName,
                    primarynumber,
                    standard,
                    selectedText,
                    secondarynumber,
                    selectedDate,
                    guardianName,
                    schoolAddress,
                    planId,
                    boardingPlanScheduleId,
                    deboardingPlanScheduleId,
                    operatorId
                )
            } catch (e: Exception) {

            }
        }


    }


    fun editStudent(
        children: childrenEditPlan,
        operatorId: Int,
        studentId: Int
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                driverPlan.editStudent(
                    children,
                    operatorId,
                    studentId
                )
            } catch (e: Exception) {

            }
        }
    }
}