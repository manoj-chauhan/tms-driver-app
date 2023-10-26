package com.samrish.driver.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import com.samrish.driver.network.LocationService


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
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

    override fun onStart() {
        Log.d("TAG", "onStart: ")
        val i = Intent(this.applicationContext, LocationService::class.java)
        startService(i)
        super.onStart()
    }
}


