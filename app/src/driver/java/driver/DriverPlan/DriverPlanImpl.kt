package com.drishto.driver.DriverPlan

import android.content.Context
import com.drishto.driver.auth.AuthManager
import com.drishto.driver.models.ChildrenList
import com.drishto.driver.models.childrenEditPlan
import com.drishto.driver.models.scheduleList
import com.drishto.driver.network.DriverNetRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DriverPlanImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val authManager: AuthManager,
    private val planNetRepository: DriverNetRepository,
) : DriverPlanManager {
    override fun getChildrenList(operatorId: Int, planId: Int):List<ChildrenList>? {
        return planNetRepository.fetchPlanChildren(planId, operatorId)
    }

    override fun getTripSchedule(operatorId: Int, planId: String): scheduleList {
        return planNetRepository.fetchSchedules(planId, operatorId)
    }

    override fun addStudent(
        name: String,
        schoolName: String,
        primarynumber: String,
        standard: String,
        selectedText: String,
        secondarynumber: String,
        selectedDate: String,
        guardianName: String,
        schoolAddress: String,
        planId: String,
        boardingPlanScheduleId: Int,
        deboardingPlanScheduleId: Int,
        operatorId: Int,
    ) {
        return planNetRepository.addStudent(name,schoolName,  primarynumber,standard,selectedText, secondarynumber, selectedDate, guardianName, schoolAddress, planId, boardingPlanScheduleId, deboardingPlanScheduleId, operatorId)
    }

    override fun editStudent(
        children: childrenEditPlan,
        operatorId: Int,
        studentId: Int
    ) {
        return planNetRepository.editStudent(children, operatorId, studentId)
    }

}