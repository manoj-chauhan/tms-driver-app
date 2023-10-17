package com.samrish.driver.ui

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Divider
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.samrish.driver.services.getAccessToken
import com.samrish.driver.ui.pages.AssignmentDetailScreen
import com.samrish.driver.ui.pages.HomeScreen
import com.samrish.driver.ui.pages.Login
import com.samrish.driver.ui.pages.ProfileScreen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigationHost(
    navController: NavHostController
) {

    var expander by remember {
        mutableStateOf(false)
    }
    Scaffold(topBar = {
        TopAppBar(modifier = Modifier.padding(end= 13.dp),title = { Text(text = "Assigned Trips") }, navigationIcon = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Filled.Menu, contentDescription = null)
            }
        }, actions = {
            IconButton(onClick = { expander = true }) {
                Icon(imageVector = Icons.Filled.MoreVert, contentDescription = null)
            }
            
            DropdownMenu(expanded = expander, onDismissRequest = { expander = false }) {
                DropdownMenuItem(text = { Text(text = "User Profile") }, onClick = { /*TODO*/ }, 
                    leadingIcon = {
                        Icon(imageVector = Icons.Filled.Person, contentDescription = null) })
                Divider(color = Color.Blue, thickness = 1.dp)
                DropdownMenuItem(text = { Text(text = "Locations") }, onClick = { /*TODO*/ },
                    leadingIcon = {
                        Icon(imageVector = Icons.Filled.Place, contentDescription = null) })
            }
        }
        )
    }, content = {
        innnerpadding ->
        LazyColumn(contentPadding = innnerpadding, verticalArrangement = Arrangement.spacedBy(8.dp)){

        }
    }

    )

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

