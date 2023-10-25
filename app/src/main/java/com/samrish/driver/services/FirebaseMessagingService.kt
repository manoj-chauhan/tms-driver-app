package com.samrish.driver.services

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.samrish.driver.R

class MyFirebaseMessagingService: FirebaseMessagingService(){
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
//        val deviceId = remoteMessage.data["device_id"]
//        if (isDriverDevice(deviceId)) {
            showNotification(remoteMessage.notification?.title, remoteMessage.notification?.body)
//        }
    }

    private fun isDriverDevice(deviceId: String?): Boolean {
        // Implement your logic to determine if the device is assigned to the driver.
        // Return true if it's the driver's device, otherwise false.
        return true
    }

    private fun showNotification(title: String?, body: String?) {
        val channelId = "default_channel" // Change this to your desired channel ID.
        val notificationId = 1 // Use a unique ID for each notification.

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.notification) // Replace with your notification icon.
            .setContentTitle(title)
            .setContentText(body)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        createNotificationChannelIfNeeded(channelId)

        val notificationManager = NotificationManagerCompat.from(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        notificationManager.notify(notificationId, notificationBuilder.build())
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
}

