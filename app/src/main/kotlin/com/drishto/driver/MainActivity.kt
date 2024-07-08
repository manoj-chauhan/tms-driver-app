package com.drishto.driver

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.PowerManager
import android.provider.Settings
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.drishto.driver.R
import com.drishto.driver.ui.DrishtoApp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {

        Log.d("TAG", "onStart: ")
        super.onCreate(savedInstanceState)
        var batteryOptimizationDialogShown by mutableStateOf(false)

        setTitle(R.string.app_name)
        setContent {
            Log.d("TAG", "onCreate: Drishto is running  ")
            DrishtoApp()
//            LocationPermissionCheck()
            var showBatteryOptimizationDialog by remember { mutableStateOf(true) }


            val isBatteryOptimizationEnabled = isBatteryOptimizationEnabled(applicationContext)
            Log.d("Battery", "onCreate: $isBatteryOptimizationEnabled ")
            if (!isBatteryOptimizationEnabled && showBatteryOptimizationDialog) {
                AlertDialog(onDismissRequest = { showBatteryOptimizationDialog = true },
                    title = { Text("Battery Optimization") },
                    text = {
                        Text(
                            "Battery optimization is currently enabled for this app. " + "To ensure proper functionality, please disable battery optimization."
                        )
                    },
                    confirmButton = {
                        Button(onClick = {
                            showBatteryOptimizationDialog = false
                            openBatteryOptimizationSettings(applicationContext)
                        }) {
                            Text("Go to Settings")
                        }
                    },
                    dismissButton = {
                        null
                    }

                )
            }


            val permissionState =
                rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS)

            LaunchedEffect(key1 = true) {
                Log.d("TAG", "onCreate: Laungh")
                permissionState.launchPermissionRequest()
            }
            if (permissionState.status.isGranted) {
            } else {
                Column() {
                    if (permissionState.status.shouldShowRationale) {
                    } else {
                    }
                }
            }
        }
    }

    private fun isBatteryOptimizationEnabled(context: Context): Boolean {
        val powerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager
        return powerManager.isIgnoringBatteryOptimizations(context.packageName)
    }

    // Function to open battery optimization settings
    private fun openBatteryOptimizationSettings(context: Context) {
        val intent = Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS)
        intent.data = Uri.parse("package:" + context.packageName)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

}