package driver

import android.Manifest
import android.content.Context
import android.content.Intent
import android.location.LocationManager
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.samrish.driver.R
import com.samrish.driver.ui.DrishtoApp
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
            LocationPermissionCheck()
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
                        // Set dismissButton to null to prevent dismissal by clicking outside
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

    @Composable
    fun LocationPermissionCheck() {
        val context = LocalContext.current
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        // Check if location is enabled
        val isLocationEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val locationEnabledState = rememberUpdatedState(isLocationEnabled)

        if (!locationEnabledState.value) {
            // Location is not enabled, show a dialog to prompt the user to enable it
            LocationDisabledDialog()
        } else {
            // Location is enabled, you can continue with your logic here
            // ...
            Log.e("Location", "LocationPermissionCheck: dialog is disabledd ",)
        }
    }

    private @Composable
    fun LocationDisabledDialog() {

        val context = LocalContext.current
        val alertDialogShown = remember { mutableStateOf(true) }

        if (alertDialogShown.value) {
            AlertDialog(
                onDismissRequest = {
                    val locationManager =
                        context.getSystemService(LOCATION_SERVICE) as LocationManager
                    if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                        alertDialogShown.value = false
                    }
                },
                title = { Text("Location is disabled") },
                text = {
                    Column {
                        Text("Please enable location to use this app.")
                        Text("Go to Settings to enable location.")
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            // Open the Settings page to enable location
                            context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                        }
                    ) {
                        Text("Go to Settings")
                    }
                }
            )
        }
    }
}