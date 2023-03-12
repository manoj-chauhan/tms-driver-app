package com.samrish.driver.ui.composition

import android.app.Activity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable


@Composable
fun AppNavigationHost(
    navController: NavHostController
) {
    val activity = (LocalContext.current as Activity)
    NavHost(navController = navController, startDestination = "login") {
        composable("home") {
            TabScreen()
        }
        composable(
            "login"
        ) {
            Login()
        }
    }
}


@Composable
fun TabScreen() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        var selectedTab by remember {
            mutableStateOf(1)
        }

        when(selectedTab) {
            1 -> {
                Assignments()
            }
            2 -> {
                History()
            }
        }

        NavigationBar(
            modifier = Modifier.align(Alignment.BottomEnd)
        ) {
            NavigationBarItem(selected = (selectedTab == 1), onClick = {
                selectedTab = 1
            }, icon = {}, label = { Text(text = "Assignments") })
            NavigationBarItem(selected = (selectedTab == 2), onClick = {
                selectedTab = 2
            }, icon = {}, label = { Text(text = "History") })
        }
    }
}
