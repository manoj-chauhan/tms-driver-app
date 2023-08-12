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
import com.samrish.driver.models.Company
import com.samrish.driver.models.Trip
import com.samrish.driver.services.getAccessToken
import com.samrish.driver.services.getCompanyDetail
import com.samrish.driver.services.getSelectedCompanyCode
import com.samrish.driver.ui.pages.AssignmentDetailScreen
import com.samrish.driver.ui.pages.AssignmentsScreen
import com.samrish.driver.ui.pages.CompanySelection
import com.samrish.driver.ui.pages.History
import com.samrish.driver.ui.pages.Login
import com.samrish.driver.ui.pages.ProfileScreen


@Composable
fun AppNavigationHost(
    navController: NavHostController
) {
    var selectedAssignmentCode by remember {
        mutableStateOf("")
    }

    var startScreen:String = "login"

    getAccessToken(LocalContext.current)?.let {
        startScreen = "companies"
    }

    if(startScreen == "login") {
        val myIntent = Intent(LocalContext.current, LoginActivity::class.java)
        LocalContext.current.startActivity(myIntent)
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
            "companies"
        ) {
            CompanySelection(
                navController = navController,
                onCompanySelected = {
                    navController.navigate("home")
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
            AssignmentDetailScreen(
                assignmentCode = selectedAssignmentCode
            )
        }
        composable(
            "profile"
        ) {
            ProfileScreen(navController)
        }
    }
}


@Composable
fun TabScreen(
    navController: NavHostController,
    onAssignmentSelected: (assignment: Trip) -> Unit
) {
    var selectedTab by remember {
        mutableStateOf(1)
    }

    var selectedCompany by remember {
        mutableStateOf<Company?>(null);
    }

    getCompanyDetail(
        context = LocalContext.current,
        companyCode = getSelectedCompanyCode(LocalContext.current)!!,
        onCompanyDetailFetched = {
            selectedCompany = it
        }
    )

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            selectedCompany?.let { Text(text = it.name) }

            when (selectedTab) {
                1 -> {
                    AssignmentsScreen(
                        navController = navController,
                        onAssignmentSelected = onAssignmentSelected
                    )
                }

                2 -> {
                    History()
                }
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
