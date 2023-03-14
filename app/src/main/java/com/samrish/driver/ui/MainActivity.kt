package com.samrish.driver.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.samrish.driver.services.LocationService
import com.samrish.driver.ui.composition.DrishtoApp


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DrishtoApp()
        }
    }

    override fun onStart() {
        val i = Intent(this.applicationContext, LocationService::class.java)
        startService(i)
        super.onStart()
    }
}


