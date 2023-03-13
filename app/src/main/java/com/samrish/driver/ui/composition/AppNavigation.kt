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
import com.samrish.driver.models.Trip
import com.samrish.driver.services.SessionStorage


@Composable
fun AppNavigationHost(
    navController: NavHostController
) {
    var selectedAssignmentCode by remember {
        mutableStateOf("")
    }

    var startScreen:String = "login"

    SessionStorage().getAccessToken(LocalContext.current)?.let {
        startScreen = "home"
    }

    NavHost(navController = navController, startDestination = startScreen) {
        composable("home") {
            TabScreen(
                navController = navController,
                onAssignmentSelected = {
                    selectedAssignmentCode = it.code
                    navController.navigate("assignments/detail")
                }
            )
        }
        composable(
            "login"
        ) {
            Login(navController = navController)
        }
        composable(
            "assignments/detail"
        ) {
            AssignmentDetail(
                assignmentCode = selectedAssignmentCode
            )
        }
    }
}


@Composable
fun TabScreen(
    navController: NavHostController,
    onAssignmentSelected: (assignment: Trip) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        var selectedTab by remember {
            mutableStateOf(1)
        }

        when(selectedTab) {
            1 -> {
                Assignments(
                    navController = navController,
                    onAssignmentSelected = onAssignmentSelected
                )
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
