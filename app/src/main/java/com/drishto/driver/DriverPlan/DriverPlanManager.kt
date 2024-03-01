package com.drishto.driver.DriverPlan

import com.drishto.driver.models.ChildrenList

interface DriverPlanManager {
    fun getChildrenList(operatorId:Int, planId:Int): List<ChildrenList>?
}