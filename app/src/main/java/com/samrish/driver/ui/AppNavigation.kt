package com.samrish.driver.ui

import android.content.Intent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import com.samrish.driver.models.CurrentAssignmentDetail
import com.samrish.driver.services.getAccessToken
import com.samrish.driver.ui.components.CompanyDetail
import com.samrish.driver.ui.pages.AssignmentDetailScreen
import com.samrish.driver.ui.pages.History
import com.samrish.driver.ui.pages.Login
import com.samrish.driver.ui.pages.ProfileScreen
import com.samrish.driver.ui.pages.HomeScreen


@Composable
fun AppNavigationHost(
    navController: NavHostController
) {
    var selectedAssignmentCode by remember {
        mutableStateOf("")
    }

    var operatorId by remember {
        mutableIntStateOf(0)
    }

    var tripId by remember {
        mutableIntStateOf(0)
    }


    var startScreen:String = "login"

    getAccessToken(LocalContext.current)?.let {
        startScreen = "home"
    }

    if(startScreen == "login") {
        val myIntent = Intent(LocalContext.current, LoginActivity::class.java)
        LocalContext.current.startActivity(myIntent)
    }


    NavHost(navController = navController, startDestination = startScreen) {
        composable("current-assignment-detail") {
            AssignmentDetailScreen(
                navController = navController,
                selectedAssignment = selectedAssignmentCode,
                operatorId = operatorId,
                tripId = tripId,
                tripCode = selectedAssignmentCode
            )
        }
        composable(
            "home"
        ) {
            HomeScreen(
                navController = navController,
                onTripSelected = {
                    selectedAssignmentCode = it.tripCode
                    operatorId = it.operatorCompanyId
                    tripId = it.tripId
                    navController.navigate("current-assignment-detail")
                }
            )
        }
        composable(
                "login"
                ) {
            Login(navController = navController)

        }
        composable(
            "profile"
        ) {
            ProfileScreen(navController)
        }
    }
}

