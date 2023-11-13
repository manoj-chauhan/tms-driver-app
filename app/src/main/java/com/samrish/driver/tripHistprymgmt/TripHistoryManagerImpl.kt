package com.samrish.driver.tripHistprymgmt

import android.content.Context
import com.samrish.driver.network.TripNetRepository
import com.samrish.driver.ui.viewmodels.TripHistory
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class TripHistoryManagerImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val tripNetRepository: TripNetRepository,
): TripHistoryManager {
    override fun getTripHistory(tripCode:String, operatorId:Int): List<TripHistory>? {
        return tripNetRepository.fetchTripHistory(tripCode, operatorId)
    }


}