package com.drishto.driver.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.drishto.driver.ui.AppNavigationHost

@Composable
fun DrishtoApp() {
    val navController = rememberNavController()
    AppNavigationHost(
        navController = navController
    )
}