package com.samrish.driver

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.samrish.driver.ui.DrishtoApp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("TAG", "onStart: ")
        super.onCreate(savedInstanceState)
        setContent {
            DrishtoApp()
        }
    }

}


