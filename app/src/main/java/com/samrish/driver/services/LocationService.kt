package com.samrish.driver.services
import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
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
import com.android.volley.*
import com.android.volley.toolbox.Volley
import com.samrish.driver.services.requests.SendDeviceMatrixRequest
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class LocationService : Service(), LocationListener {
    private var locationManager: LocationManager? = null
    private var provider: String? = null
    private var powerManager: PowerManager? = null
    private var wakeLock: WakeLock? = null

    private fun createNotificationChannel() {
            val name: CharSequence = "Default"
            val description = "Common Channel"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance)
            channel.description = description
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
    }

    private fun showNotification() {
        val builder: NotificationCompat.Builder = NotificationCompat.Builder(this, CHANNEL_ID)
//            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("My notification")
            .setContentText("Much longer text that cannot fit one line...")
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("Much longer text that cannot fit one line...")
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        val notificationManager = NotificationManagerCompat.from(this)

// notificationId is a unique int for each notification that you must define
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
        this.locationManager!!.requestLocationUpdates(provider!!, 100, .5f, this)
    }

    override fun onDestroy() {
        locationManager!!.removeUpdates(this)
        wakeLock!!.release()
        super.onDestroy()
    }

    private fun sendMatrix(latitude: Double, longitude: Double) {
        val queue = Volley.newRequestQueue(this)
        val url = resources.getString(com.samrish.driver.R.string.url_device_matrix)

        val hdrs: MutableMap<String, String> = mutableMapOf<String, String>()
        val authHeader = SessionStorage().getAccessToken(this)
        if(authHeader != null) {
            hdrs?.put("Authorization", "Bearer $authHeader")
        }

        val deviceIdentifier = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)

        queue.add(SendDeviceMatrixRequest(
            deviceIdentifier,
            latitude,
            longitude,
            url,
            hdrs,
            { response ->
                Log.i("LocationService", "Device matrix sent successfully!!")
            },
            { error -> handleError(error) }
        ))
    }


    private fun handleError(error: VolleyError) {
        Log.i("LocationService", "Request Failed with Error: $error")
        when (error) {
            is TimeoutError, is NoConnectionError -> {
                Log.i("LocationService", "Couldn't Connect!")
            }
            is AuthFailureError -> {
                stopSelf();
            }
            is ServerError -> {
                Log.i("LocationService", "Server Error!")
            }
            is NetworkError -> {
                Log.i("LocationService", "Network Error!")
            }
            is ParseError -> {
                Log.i("LocationService", "Unable to parse response!")
            }
        }
    }


    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onLocationChanged(location: Location) {
        val lat = location.latitude
        val lng = location.longitude
        val formater = DateTimeFormatter.ISO_LOCAL_TIME
        val msg =
            "Time: " + LocalDateTime.now().format(formater) + "   Location: " + lat + "," + lng
        Log.i("TRACKER", "Location: $lat,$lng")
        sendMatrix(lat, lng)
//        val intent = Intent("location-update")
//        intent.putExtra("message", msg)
//        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
//        publishCoordinates(lat, lng)
    }

    companion object {
        private const val CHANNEL_ID = "techie_tracker"
    }
}
