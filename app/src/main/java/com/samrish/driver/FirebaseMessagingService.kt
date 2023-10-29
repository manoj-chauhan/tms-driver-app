package com.samrish.driver

import android.content.Intent
import android.util.Log
import androidx.annotation.WorkerThread
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.samrish.driver.database.AppDatabase
import com.samrish.driver.database.Trip
import com.samrish.driver.network.TripNetRepository
import com.samrish.driver.viewmodels.TripsAssigned
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MyFirebaseMessagingService : FirebaseMessagingService() {

    @WorkerThread
    override fun onNewToken(token: String) {
        //Todo: Need to be implemented
        super.onNewToken(token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage);
        fetchLocalData()
    }


    private fun fetchLocalData() {
        CoroutineScope(Dispatchers.IO).launch {
            //Fetched from Server
            val fetchedAssignedTrips: List<TripsAssigned>? = TripNetRepository.getInstance().tripList(applicationContext)

            //Retrieved from Local Database
            val db = AppDatabase.getDatabase(applicationContext)
            val tripNetRepo = db.tripRepository()

            fetchedAssignedTrips?.let { tripList ->
                run {
                    Log.i("FirebaseMessagingService", "Assigned Trips Fetched: ${tripList.count()}")

                    //Clearing Cache in DB
                    tripNetRepo.clearAllTrips()

                    var isAnyTripStarted = false;

                    //Saving the updates in local DB
                    tripList.forEach { trip ->
                        val tripInfo = Trip(
                            trip.tripCode,
                            trip.tripName,
                            trip.status,
                            trip.label,
                            trip.companyName,
                            trip.companyCode,
                            trip.operatorCompanyName,
                            trip.operatorCompanyCode,
                            trip.operatorCompanyId,
                            trip.tripDate,
                            trip.tripId
                        )
                        tripNetRepo.insertTrip(tripInfo)

                        // Checking for any started trip in list
                        if (!isAnyTripStarted) {
                            if (trip.status != "TRIP_CREATED") {
                                isAnyTripStarted = true;
                            }
                        }

                    }
                    if (isAnyTripStarted) {
                        applicationContext.startService(
                            Intent(
                                applicationContext,
                                LocationService::class.java
                            )
                        )
                    }else {
                        applicationContext.stopService(
                            Intent(
                                applicationContext,
                                LocationService::class.java
                            )
                        )
                    }
                }
            }
        }
    }
}



