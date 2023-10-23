package com.samrish.driver.services

import android.content.Context
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService: FirebaseMessagingService(){
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // Handle the incoming FCM message here
        // Extract trip data from the message
        val tripId = "123"

        // Create a notification using the trip data
        createNotification(tripId, this)
    }
}

private fun createNotification(tripId: String?, context: Context) {
    val json = """
            {
                "to": "YOUR_FCM_DEVICE_TOKEN",
                "data": {
                    "tripId": "$tripId"
                }
            }
        """.trimIndent()

}