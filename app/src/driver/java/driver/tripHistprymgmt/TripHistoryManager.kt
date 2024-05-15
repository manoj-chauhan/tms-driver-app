package com.drishto.driver.tripHistprymgmt

import com.drishto.driver.ui.viewmodels.TripHistory

interface TripHistoryManager {
    fun getTripHistory(tripCode: String, operatorId:Int): List<TripHistory>?
}