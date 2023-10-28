package com.samrish.driver

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
import com.samrish.driver.ui.DrishtoApp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("TAG", "onStart: ")
        super.onCreate(savedInstanceState)
        setContent {
            DrishtoApp()
        }
    }

    override fun onStart() {
        Log.d("TAG", "onStart: ")
        val i = Intent(this.applicationContext, LocationService::class.java)
        startService(i)
        super.onStart()
    }
}


