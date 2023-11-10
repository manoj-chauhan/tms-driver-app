package com.samrish.driver.tripHistprymgmt

import com.samrish.driver.ui.viewmodels.TripHistory

interface TripHistoryManager {
    fun getTripHistory(): List<TripHistory>?
}