package com.samrish.driver.auth

import android.content.Context

interface AuthManager {
    fun authenticate(
        context: Context,
        firebaseIdToken: String,
        fcmToken: String,
        onAuthenticated: () -> Unit,
        onError: (errorMessage:String) -> Unit
    )
}