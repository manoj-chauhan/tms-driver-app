package driver.network

import android.R
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.WorkerThread
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MyFirebaseMessagingService: FirebaseMessagingService(){

    @WorkerThread
    override fun onNewToken(token: String) {
        //Todo: Need to be implemented
        super.onNewToken(token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d("TAG", "onMessageReceived: location service")

        val data = remoteMessage.getData()

        sendNotification(applicationContext, "","", data.get("deeplink"))

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
    fun sendNotification(context: Context, title: String?, message: String?, deepLink: String?) {
        val notificationManager =
            context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= 26) {
            val notificationChannel = NotificationChannel(
                "any_default_id", "any_channel_name",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationChannel.description = "Any description can be given!"
            notificationManager.createNotificationChannel(notificationChannel)
        }
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder: NotificationCompat.Builder = NotificationCompat.Builder(context)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setSmallIcon(R.drawable.ic_notification_overlay)
            .setPriority(Notification.PRIORITY_MAX)
            .setDefaults(Notification.DEFAULT_ALL)

        val intent = Intent()
        intent.setAction(Intent.ACTION_VIEW)
        intent.setData(Uri.parse(deepLink))
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent =
            PendingIntent.getActivity(context, 0, intent,
                PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE)
        notificationBuilder
            .setContentTitle(title)
            .setContentText(message)
            .setContentIntent(pendingIntent)
        notificationManager.notify(0, notificationBuilder.build())
    }

    companion object {
        private const val CHANNEL_ID = "main"
        const val CUSTOM_BROADCAST_ACTION = "com.example.app.CUSTOM_BROADCAST_ACTION"

    }

}


