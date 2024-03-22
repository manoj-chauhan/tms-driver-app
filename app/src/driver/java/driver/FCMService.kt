package com.drishto.driver

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.annotation.WorkerThread
import androidx.core.app.NotificationCompat
import androidx.core.net.toUri
import com.drishto.driver.database.AppDatabase
import com.drishto.driver.database.Trip
import com.drishto.driver.tripmgmt.TripManager
import com.drishto.driver.ui.MY_ARG
import com.drishto.driver.ui.MY_URI
import com.drishto.driver.ui.operatorI
import com.drishto.driver.ui.trip_Id
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import driver.ui.viewmodels.TripsAssigned
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random


@AndroidEntryPoint
class FCMService : FirebaseMessagingService() {

    @Inject
    lateinit var database : AppDatabase

    @Inject
    lateinit var tripManager: TripManager


    @WorkerThread
    override fun onNewToken(token: String) {
        //Todo: Need to be implemented
        super.onNewToken(token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage);
        val message = remoteMessage.data
        Log.d("This is key1", "onMessageReceived: $message")
        sendNotification(applicationContext, "remote Trip", "Hii see the trip",message )
        fetchLocalData()
    }

    private fun createNotificationChannel() {
        Log.d("create notification", "createNotificationChannel: ")
        val name: CharSequence = "Trip Notification"
        val description = "Trip service notification"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance)
        channel.description = description
        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)

    }
    fun sendNotification(context: Context, title: String?, message: String?, parameters: MutableMap<String, String>) {

        val tripCode = parameters.get("tripCode")
        val tripId = parameters.get("tripId")?.toInt()
        val operatorId = parameters.get("operatorId")?.toInt()
        val notificationManager = getSystemService(NotificationManager::class.java)

        createNotificationChannel()

        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = "$MY_URI/$MY_ARG=${tripCode}/$trip_Id=${tripId}&$operatorI=${operatorId}".toUri()
            Log.d("TAG", "showNotification: ${data} ")
            setClass(applicationContext, driver.MainActivity::class.java)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = TaskStackBuilder.create(applicationContext).run {
            addNextIntentWithParentStack(intent)
            getPendingIntent(1, PendingIntent.FLAG_IMMUTABLE)
        }

        val notificationBuilder=NotificationCompat.Builder(context,
            NOTIFICATION_CHANNEL_ID
        )
            .setSmallIcon(R.drawable.notification)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setContentTitle(title)
            .setContentText(message)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        val id= Random(System.currentTimeMillis()).nextInt(1000)

        notificationManager.notify(id, notificationBuilder.build())
    }



    private fun fetchLocalData() {
        CoroutineScope(Dispatchers.IO).launch {
            //Fetched from Server
            val fetchedAssignedTrips: List<TripsAssigned>? = tripManager.getActiveTrips()

            //Retrieved from Local Database
            val tripNetRepo = database.tripRepository()

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

    companion object {
        private const val NOTIFICATION_CHANNEL_ID = "firebase_notification"
    }
}



