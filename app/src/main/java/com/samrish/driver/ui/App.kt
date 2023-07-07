package com.samrish.driver.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController

@Composable
fun DrishtoApp() {
    val navController = rememberNavController()
    AppNavigationHost(
        navController = navController
    )
}