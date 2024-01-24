package com.drishto.driver.auth

import android.content.Context
import android.os.Build
import android.provider.Settings
import android.util.Log
import com.drishto.driver.network.AuthNetRepository
import com.drishto.driver.network.DeviceRegistrationDetail
import com.drishto.driver.network.saveAccessToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthManagerImpl @Inject constructor(private val authNetRepo: AuthNetRepository): AuthManager {

    override fun authenticate(
        context: Context,
        firebaseIdToken: String,
        fcmToken: String,
        onAuthenticated: () -> Unit,
        onError: (errorMessage:String) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val authToken = authNetRepo.login(context, firebaseIdToken)
                // Get new FCM registration token
                val deviceName = Build.MANUFACTURER + " " + Build.MODEL
                val deviceIdentifier = Settings.Secure.getString(
                    context.contentResolver,
                    Settings.Secure.ANDROID_ID
                )
                val deviceRegDetail = DeviceRegistrationDetail(
                    fcmToken,
                    deviceIdentifier,
                    deviceName,
                    "ANDROID"
                )
                Log.i("Login", deviceRegDetail.toString())
                authNetRepo.registerDevice(context, authToken, deviceRegDetail)
                saveAccessToken(context, authToken)
                CoroutineScope(Dispatchers.Main).launch {
                    onAuthenticated()
                }
            } catch (e:Exception) {
                CoroutineScope(Dispatchers.Main).launch {
                    onError("" + e.message)
                }
            }

        }
    }
}