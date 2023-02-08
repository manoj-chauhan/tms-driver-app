package com.samrish.driver.services
import android.Manifest
import android.R
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
import android.os.Build
import android.os.IBinder
import android.os.PowerManager
import android.os.PowerManager.WakeLock
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.android.volley.*
import com.android.volley.toolbox.HttpHeaderParser
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject
import java.io.UnsupportedEncodingException
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
        /////////////////////////
        wakeLock =
            (getSystemService(Context.POWER_SERVICE) as PowerManager).run {
                newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "DriverApp::LocationService").apply {
                    acquire()
                }
            }
        //////////////////////////
//        powerManager = getSystemService(POWER_SERVICE) as PowerManager
//        this.wakeLock = powerManager!!.newWakeLock(
//            PowerManager.PARTIAL_WAKE_LOCK,
//            "TechieTracker::LocationService"
//        )
//        this.wakeLock.acquire()
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

//    private fun publishCoordinates(lat: Double, lng: Double) {
//        try {
//            val requestQueue = Volley.newRequestQueue(this)
//            val URL = "http://35.154.239.61:81/record/geo_coordinates"
//            val jsonBody = JSONObject()
//            jsonBody.put("latitude", lat)
//            jsonBody.put("longitude", lng)
//            jsonBody.put("time", LocalDateTime.now())
//            val requestBody = jsonBody.toString()
//            val stringRequest: StringRequest = object : StringRequest(
//                Method.POST, URL,
//                Response.Listener { response -> Log.i("VOLLEY", response!!) },
//                Response.ErrorListener { error -> Log.e("VOLLEY", error.toString()) }) {
//                override fun getBodyContentType(): String {
//                    return "application/json; charset=utf-8"
//                }
//
//                @Throws(AuthFailureError::class)
//                override fun getBody(): ByteArray {
//                    return try {
//                        if (requestBody == null) null else requestBody.toByteArray(charset("utf-8"))
//                    } catch (uee: UnsupportedEncodingException) {
//                        VolleyLog.wtf(
//                            "Unsupported Encoding while trying to get the bytes of %s using %s",
//                            requestBody,
//                            "utf-8"
//                        )
//                        null
//                    }
//                }
//
//                override fun parseNetworkResponse(response: NetworkResponse): Response<String> {
//                    var responseString = ""
//                    if (response != null) {
//                        responseString = response.statusCode.toString()
//                        // can get more details such as response.headers
//                    }
//                    return Response.success(
//                        responseString,
//                        HttpHeaderParser.parseCacheHeaders(response)
//                    )
//                }
//            }
//            requestQueue.add(stringRequest)
//        } catch (e: JSONException) {
//            e.printStackTrace()
//        }
//    }

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
//        val intent = Intent("location-update")
//        intent.putExtra("message", msg)
//        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
//        publishCoordinates(lat, lng)
    }

    companion object {
        private const val CHANNEL_ID = "techie_tracker"
    }
}
