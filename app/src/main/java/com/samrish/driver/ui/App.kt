package com.samrish.driver.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.samrish.driver.ui.pages.AppNavigationHost

@Composable
fun DrishtoApp() {
    val navController = rememberNavController()
    AppNavigationHost(
        navController = navController
    )
}