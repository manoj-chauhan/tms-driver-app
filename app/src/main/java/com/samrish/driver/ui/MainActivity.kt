package com.samrish.driver.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import com.samrish.driver.network.LocationService
import com.samrish.driver.network.MyFirebaseMessagingService


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("TAG", "onStart: ")
        super.onCreate(savedInstanceState)
        setContent {
            DrishtoApp()
        }

        FirebaseApp.initializeApp(this)
        // Retrieve FCM token
        FirebaseMessaging.getInstance().token
            .addOnSuccessListener { token ->
                Log.d("FCM Token", token)
    }

    }

    private val customBroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {

            val serviceIntent = Intent(context, LocationService::class.java)

            serviceIntent.putExtra("started_by_receiver", true)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Log.d("TAG", "onReceive: ")
                context?.startForegroundService(serviceIntent)
            } else {
                context?.startService(serviceIntent)
            }
        }
    }
    override fun onResume() {
        super.onResume()
        val intentFilter = IntentFilter(MyFirebaseMessagingService.CUSTOM_BROADCAST_ACTION)
        registerReceiver(customBroadcastReceiver, intentFilter)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(customBroadcastReceiver)
    }

    override fun onStart() {
        Log.d("TAG", "onStart: ")
        val i = Intent(this.applicationContext, LocationService::class.java)
        startService(i)
        super.onStart()
    }
}


