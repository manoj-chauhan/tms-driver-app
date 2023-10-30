package com.samrish.driver.network

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.samrish.driver.R

class MyFirebaseMessagingService: FirebaseMessagingService(){


    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage);
        val broadCastIntent = Intent(CUSTOM_BROADCAST_ACTION)
        sendBroadcast(broadCastIntent)

        Log.d("TAG", "onMessageReceived: location service")
        fetchLocalData()

            val title = remoteMessage.notification?.title
            val body = remoteMessage.notification?.body

            val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.notification)
                .setContentTitle(title)
                .setContentText(body)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channelId = "your_channel_id"
                val channelName = "Your Channel Name"
                val importance = NotificationManager.IMPORTANCE_DEFAULT
                val notificationChannel = NotificationChannel(channelId, channelName, importance)
                notificationManager.createNotificationChannel(notificationChannel)
            }

            // Show the notification
            notificationManager.notify(0, notificationBuilder.build())


    }


    private fun fetchLocalData(){
    }

    private fun createNotificationChannelIfNeeded(channelId: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelName = "Default Channel Name" // Change to your desired channel name.
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, channelName, importance)
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    companion object {
        private const val CHANNEL_ID = "main"
        const val CUSTOM_BROADCAST_ACTION = "com.example.app.CUSTOM_BROADCAST_ACTION"

    }

}


