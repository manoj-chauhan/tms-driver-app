package com.drishto.driver

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.IBinder
import android.os.Looper
import android.os.PowerManager
import android.os.PowerManager.WakeLock
import android.provider.Settings
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.net.toUri
import com.drishto.driver.database.TelemetryRepository
import com.drishto.driver.models.Telemetry
import com.drishto.driver.network.TelemetryNetRepository
import com.drishto.driver.telemetry.TelemetryManager
import com.drishto.driver.ui.MY_ARG
import com.drishto.driver.ui.MY_URI
import com.drishto.driver.ui.operatorI
import com.drishto.driver.ui.trip_Id
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
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
    private var isService: Boolean = true

    @Inject
    lateinit var telemetryRepository: TelemetryRepository

    @Inject
    lateinit var telemetryNetRepository: TelemetryNetRepository

    @Inject
    lateinit var telemetryManager: TelemetryManager

    @Volatile
    private var isRunning = true

    private lateinit var locationCallback: LocationCallback
    private lateinit var fusedLocationClient: FusedLocationProviderClient


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

        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = "$MY_URI/$MY_ARG=MNNFD/$trip_Id=78&$operatorI=1".toUri()
            Log.d("TAG", "showNotification: ${data} ")
            setClass(applicationContext, driver.MainActivity::class.java)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = TaskStackBuilder.create(applicationContext).run {
            addNextIntentWithParentStack(intent)
            getPendingIntent(1, PendingIntent.FLAG_IMMUTABLE)
        }

        Log.d("show notification", "showNotification: ")
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("You are operating a trip")
            .setSmallIcon(R.drawable.notification)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        startForeground(1001, builder.build())
    }

    @RequiresApi(Build.VERSION_CODES.S)
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


        locationCallback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                super.onLocationResult(result)
                for (currentLocation in result.locations) {
                    onLocationChanged(currentLocation)
                }
            }
        }
        startLocationUpdates()

        Thread {
            while (isRunning) {
                //get only oldest telemetry entry with false status = t
                //call sendTELEMEETRY TO SERVER
                //if task is successful update the database
                //check for telemetry with false status in database if exist fetch oldest one
                //repeat line 182
                //else wait for few seconds
                //repeat line 182
                //else
                //wait for few seconds
                //repeat line 182

                val telemetry = telemetryRepository.getOldestTelemetryWithFalseStatus(false)
                if (telemetry != null) {
                    val jsonTelemetry = Telemetry(
                        telemetry.deviceIdentifier,
                        telemetry.latitude,
                        telemetry.longitude,
                        telemetry.time
                    )
                    try {
                        Log.d("Data", "saveTelemetryToDatabase: $telemetry ")
                        // send telemetry to the network
                        telemetryNetRepository.sentTelemetry(jsonTelemetry)

                        // If successful, update the database
                        val id = telemetryRepository.getTelemetryId(
                            telemetry.latitude,
                            telemetry.longitude,
                            telemetry.time
                        )
                        telemetryRepository.updateTelemetryStatus(id, true)
                    } catch (e: Exception) {
                        Log.e("TelemetryManagerImpl", "Error sending telemetry", e)
                        Thread.sleep(5000)
                    }
                } else {
                    Thread.sleep(2000)
                }
            }
        }.start()


    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun startLocationUpdates() {
        val lr = com.google.android.gms.location.LocationRequest.Builder(1000L).setMinUpdateIntervalMillis(2000L).setMinUpdateDistanceMeters(
            0.03F
        ).setPriority( Priority.PRIORITY_HIGH_ACCURACY ).setWaitForAccurateLocation(true).build()

         fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.requestLocationUpdates(lr, locationCallback, Looper.getMainLooper())
        }
    }

    override fun onDestroy() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
        wakeLock!!.release()
        isRunning = false

        super.onDestroy()
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onLocationChanged(location: Location) {
        val lat = location.latitude
        val lng = location.longitude

        Log.d("Latitude", "onLocationChanged: Latitude $lat $lng ${location.hasAccuracy()}")
        val formater = DateTimeFormatter.ISO_LOCAL_DATE_TIME
        val msg =
            "Time: " + LocalDateTime.now().format(formater) + "   Location: " + lat + "," + lng
        Log.i("TRACKER", "Location: $lat,$lng")
        val context: Context = this

        CoroutineScope(Dispatchers.IO).launch {
            val deviceIdentifier =
                Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)

            val telemetery = Telemetry(deviceIdentifier, lat, lng)
            telemetryManager.sendMatrix(
                telemetery
            )
        }
    }

    companion object {
        private const val CHANNEL_ID = "main"
    }
}
