package com.samrish.driver

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.samrish.driver.ui.DrishtoApp
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {

        Log.d("TAG", "onStart: ")
        super.onCreate(savedInstanceState)

                setContent {
                    DrishtoApp()
                    val permissionState =
                            rememberPermissionState(permission = android.Manifest.permission.POST_NOTIFICATIONS)

                    LaunchedEffect(key1 = true) {
                        Log.d("TAG", "onCreate: Laungh")
                        permissionState.launchPermissionRequest()
                    }
                    if(permissionState.status.isGranted){
                        Column() {
                            Text(text = "Notification permission granted")
                        }
                    }else {
                        Column() {
                            if(permissionState.status.shouldShowRationale){
                                Text(text = "Permission required")
                            }else{
                                Text(text = "permission not granted open setting")
                            }
                        }
                    }
                }
        }

    }




