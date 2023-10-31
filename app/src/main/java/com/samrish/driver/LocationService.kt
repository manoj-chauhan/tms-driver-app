package com.samrish.driver

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.IBinder
import android.os.PowerManager
import android.os.PowerManager.WakeLock
import android.provider.Settings
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.samrish.driver.models.Telemetry
import com.samrish.driver.telemetry.TelemetryManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@AndroidEntryPoint
class LocationService : Service(), LocationListener {
    private var locationManager: LocationManager? = null
    private var provider: String? = null
    private var powerManager: PowerManager? = null
    private var wakeLock: WakeLock? = null

    @Inject
    lateinit var telemetryManager: TelemetryManager

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val startedByReceiver = intent?.getBooleanExtra("started_by_receiver", false)

        if (startedByReceiver == true) {
            Log.d("TAG", "onStartCommand: ")
        } else {
            // The service was started by other means
        }

        // Rest of your onStartCommand logic

        return START_STICKY
    }

    private fun createNotificationChannel() {
        Log.d("create notification", "createNotificationChannel: ")
            val name: CharSequence = "Location Sharing"
            val description = "Location sharing notification"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance)
            channel.description = description
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
    }

    private fun showNotification() {

        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = PendingIntent.getActivity(applicationContext, 0, intent,
            PendingIntent.FLAG_IMMUTABLE)

        Log.d("show notification", "showNotification: ")
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("You are operating a trip")
            .setSmallIcon(R.drawable.notification)
            .setContentIntent(pendingIntent)
            .setContentText("Your location is being shared ")
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        startForeground(1001, builder.build())
    }

    override fun onCreate() {
            super.onCreate()
        Log.i("TRACKER", "Service Started")
        val oPowerManager = applicationContext.getSystemService(POWER_SERVICE) as PowerManager
        val packageName = applicationContext.packageName
        Log.i(
            "PowerMode: ",
            "IsIgnoringBatteryOptimisation:::::: " + oPowerManager.isIgnoringBatteryOptimizations(
                packageName
            )
        )
        createNotificationChannel()
        showNotification()

        wakeLock =
            (getSystemService(Context.POWER_SERVICE) as PowerManager).run {
                newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "DriverApp::LocationService").apply {
                    acquire()
                }
            }

        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        val criteria = Criteria()
        provider = locationManager!!.getBestProvider(criteria, false)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        this.locationManager!!.requestLocationUpdates(provider!!, 0, 0.0f, this)
    }

    override fun onDestroy() {
        locationManager!!.removeUpdates(this)
        wakeLock!!.release()
        super.onDestroy()
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onLocationChanged(location: Location) {
        val lat = location.latitude
        val lng = location.longitude

        Log.d("Latitude", "onLocationChanged: Latitude $lat $lng")
        val formater = DateTimeFormatter.ISO_LOCAL_DATE_TIME
        val msg =
            "Time: " + LocalDateTime.now().format(formater) + "   Location: " + lat +    "," + lng
        Log.i("TRACKER", "Location: $lat,$lng")

        val context: Context = this

        CoroutineScope(Dispatchers.IO).launch {
            val deviceIdentifier = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)

            telemetryManager.sendMatrix(Telemetry(
                deviceIdentifier,
                lat,
                lng
            ))
        }
    }
    companion object {
        private const val CHANNEL_ID = "main"
    }
    }
