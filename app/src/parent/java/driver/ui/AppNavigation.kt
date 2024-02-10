package driver.ui

import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
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
import com.drishto.driver.PhoneNumberActivity
import com.drishto.driver.network.clearSession
import com.drishto.driver.network.getAccessToken
import com.drishto.driver.ui.pages.UserProfile
import driver.ui.components.pastTrips
import driver.ui.pages.HomeScreen
import driver.ui.pages.MapsActivityContent


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigationHost(
    navController: NavHostController
) {

    val context = LocalContext.current

    var expander by remember { mutableStateOf(false) }
    var userProfile by remember { mutableStateOf(false) }
    var selectedAssignmentCode by remember { mutableStateOf("") }
    var operatorId by remember { mutableIntStateOf(0) }
    var tripId by remember { mutableIntStateOf(0) }
    var boardingPlaceId by remember { mutableStateOf("") }
    var deBoardingPlaceId by remember { mutableStateOf("") }


    var startScreen: String by remember {
        mutableStateOf(
            if (getAccessToken(context) != null) "home" else "login"
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.padding(end = 13.dp),
                title = { Text(text = "Active Trips") },
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

                        DropdownMenuItem(text = { Text(text = "Log out") },
                            onClick = {
                                val myIntent = Intent(context, PhoneNumberActivity::class.java)
                                clearSession(context)
                                myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                                context.startActivity(myIntent)
                            },
                            leadingIcon = {
                                Icon(imageVector = Icons.Filled.Logout, contentDescription = null)
                            })
                    }
                }
            )
        },
        content = { innerPadding ->
            LazyColumn(
                contentPadding = innerPadding,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

            }
        }
    )
    if (startScreen == "login") {
        val myIntent = Intent(LocalContext.current, PhoneNumberActivity::class.java)
        myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        LocalContext.current.startActivity(myIntent)
    }

    if (userProfile) {
        navController.navigate("user-profile")
        userProfile = false
    }

    NavHost(navController = navController, startDestination = startScreen) {
        composable("current-assignment-detail") {
            MapsActivityContent(navController, operatorId, selectedAssignmentCode)
        }
        composable("user-profile") {
            UserProfile()
        }
        composable("home") {
            HomeScreen(
                navController = navController,
                onTripSelected = {
                    selectedAssignmentCode = it.tripCode
                    tripId = it.tripId
                    operatorId = it.companyId
//                    deBoardingPlaceId= it.deBoardingPlaceId
//                    boardingPlaceId = it.boardingPlaceId
                    navController.navigate("current-assignment-detail")
                }
            )
        }
        composable("login") {

        }

        composable("past-trips-list") {
            pastTrips(navHostController = navController, screen = "app")

        }
    }
}