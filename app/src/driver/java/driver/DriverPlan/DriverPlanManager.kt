package com.drishto.driver.DriverPlan

import com.drishto.driver.models.ChildrenList
import com.drishto.driver.models.childrenEditPlan
import com.drishto.driver.models.scheduleList

interface DriverPlanManager {
    fun getChildrenList(operatorId: Int, planId: Int): List<ChildrenList>?

    fun getTripSchedule(operatorId: Int, planId: Int): scheduleList
    fun addStudent(
        name: String,
        schoolName: String,
        primarynumber: String,
        standard: String,
        selectedText: String,
        secondarynumber: String,
        selectedDate: String,
        guardianName: String,
        schoolAddress: String,
        planId: Int,
        boardingPlaceId: Int,
        deboardingPlaceId: Int,
        operatorId: Int,
    )

    fun editStudent(
        children: childrenEditPlan,
        operatorId: Int,
        studentId: Int
    )
}