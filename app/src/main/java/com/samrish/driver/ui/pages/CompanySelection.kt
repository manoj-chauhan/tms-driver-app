package com.samrish.driver.ui.pages

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.samrish.driver.services.getUserProfile

@Composable
fun CompanySelection() {
    val context = LocalContext.current
    getUserProfile(context) { profile ->
        run {
            Log.i("profile", profile.toString())
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(text = "Select Company")
    }
}

