package com.drishto.driver.DriverPlan

import com.drishto.driver.models.ChildrenList
import com.drishto.driver.models.scheduleList

interface DriverPlanManager {
    fun getChildrenList(operatorId:Int, planId:Int): List<ChildrenList>?

    fun getTripSchedule(operatorId: Int, planId: Int):scheduleList
}