package com.drishto.driver.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.drishto.driver.ui.AppNavigationHost

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun DrishtoApp() {
    val navController = rememberNavController()
    AppNavigationHost(
        navController = navController
    )
}