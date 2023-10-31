package com.samrish.driver.ui

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.samrish.driver.network.clearSession
import com.samrish.driver.network.getAccessToken
import com.samrish.driver.ui.pages.AssignmentDetailScreen
import com.samrish.driver.ui.pages.HistoryScreen
import com.samrish.driver.ui.pages.HomeScreen
import com.samrish.driver.ui.pages.LoginUserNameActivity
import com.samrish.driver.ui.pages.MatrixLog
import com.samrish.driver.ui.pages.UserProfile


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigationHost(
    navController: NavHostController
) {

    var expander by remember {
        mutableStateOf(false)
    }

    var locations by remember {
        mutableStateOf(false)
    }

    var userProfile by remember {
        mutableStateOf(false)
    }

    var logout by remember {
        mutableStateOf(false)
    }

    var history by remember {
        mutableStateOf(false)
    }

    var selectedAssignmentCode by remember {
        mutableStateOf("")
    }

    var operatorId by remember {
        mutableIntStateOf(0)
    }

    var tripId by remember {
        mutableIntStateOf(0)
    }

    Scaffold(topBar = {
        TopAppBar(
            modifier = Modifier.padding(end = 13.dp),
            title = { Text(text = "Assigned Trips") },
            navigationIcon = {
//                IconButton(onClick = { /*TODO*/ }) {
//                    Icon(imageVector = Icons.Filled.Menu, contentDescription = null)
//                }
            },
            actions = {
                IconButton(onClick = { expander = true }) {
                    Icon(imageVector = Icons.Filled.MoreVert, contentDescription = null)
                }

                DropdownMenu(expanded = expander, onDismissRequest = { expander = false }) {
                    DropdownMenuItem(text = { Text(text = "User Profile") },
                        onClick = { userProfile = true; expander = false },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Person, contentDescription = null)
                        })

                    DropdownMenuItem(text = { Text(text = "Locations") },
                        onClick = { locations = true; expander = false },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Place, contentDescription = null)
                        })

                    DropdownMenuItem(text = { Text(text = "History") },
                        onClick = { history = true; expander = false },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.History, contentDescription = null)
                        })

                    DropdownMenuItem(text = { Text(text = "Log out") },
                        onClick = { logout = true; expander = false },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Logout, contentDescription = null)
                        })


                }
            }
        )
    }, content = { innnerpadding ->
        LazyColumn(
            contentPadding = innnerpadding,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

        }
    }

    )

    var startScreen: String = "login"

    getAccessToken(LocalContext.current)?.let {
        startScreen = "home"
    }

    if (startScreen == "login") {
        val myIntent = Intent(LocalContext.current, LoginUserNameActivity::class.java)
        LocalContext.current.startActivity(myIntent)
    }

    if (locations) {
        navController.navigate("locations-screen")
        locations = false
    }

    if (userProfile) {
        navController.navigate("user-profile")
        userProfile = false
    }

    if(history){
        navController.navigate("history")
        history = false
    }

    if (logout) {
        val myIntent = Intent(LocalContext.current, LoginUserNameActivity::class.java)
        myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        LocalContext.current.startActivity(myIntent)
        clearSession(LocalContext.current)
        logout= false
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
            "locations-screen"
        ) {
            MatrixLog()
        }

        composable("history"){
            HistoryScreen()
        }

        composable("user-profile") {
            UserProfile()
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
//        composable(
//            "login"
//        ) {
//            Login(navController = navController)
//
//        }
    }
}

