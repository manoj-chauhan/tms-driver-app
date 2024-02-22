package driver.ui.viewmodels

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drishto.driver.R
import com.drishto.driver.network.getAccessToken
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.extensions.authentication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File


class DocumentDownloadViewModel : ViewModel() {
    fun downloadDocument(context: Context, name:String, operatorId:Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val downloadDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            val url = context.resources.getString(R.string.url_download_document) + name
            getAccessToken(context)?.let {
                val fuelManager = FuelManager()
                val (_, response, result) = fuelManager.get(url).authentication().bearer(it)
                    .header("Company-Id", operatorId.toString())
                    .response()

                result.fold ({
                    data->
                        val fileName = "${name}"
                        val file = File(downloadDir, fileName)

                        file.writeBytes(data)
                        showDownloadCompleteNotification(context, "Download Complete", "Your download Completed", file.toString())

                    Log.d("Fuel", "File downloaded and saved to: ${file.absolutePath}")

                }

                ){
                    error ->
                    Log.e(
                        "Fuel",
                        "Error $error"
                    )
                }
            }



        }


    }

    private fun showDownloadCompleteNotification(context: Context, title: String, message: String, filePath: String) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "download_channel_id"
            val channelName = "Downloads"
            val channel =
                NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        val openIntent = Intent(Intent.ACTION_VIEW)
        openIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        openIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        openIntent.setDataAndType(Uri.parse(filePath), "application/*")

        val pendingIntent = PendingIntent.getActivity(
            context, 0, openIntent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )


        val builder = NotificationCompat.Builder(context, "download_channel_id")
            .setSmallIcon(android.R.drawable.stat_sys_download_done)
            .setContentTitle(title)
            .setContentText(message)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setDefaults(Notification.DEFAULT_ALL)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .build()

        // Show the notification
        notificationManager.notify(1, builder)

        viewModelScope.launch {
            // Simulate download progress
            for (progress in 0 until 100) {
                // Update progress by creating a new notification
                val newBuilder = NotificationCompat.Builder(context, "download_channel_id")
                    .setSmallIcon(android.R.drawable.stat_sys_download_done)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setAutoCancel(true)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                    .setProgress(100, progress, false)

                // Notify the manager with the updated notification
                notificationManager.notify(1, newBuilder.build())
                delay(1000) // Simulate download delay
            }


        }
    }



}


