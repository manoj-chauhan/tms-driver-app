package driver.ui

import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.drishto.driver.PhoneNumberActivity
import com.drishto.driver.network.getAccessToken
import com.drishto.driver.ui.pages.userProfileView
import driver.ui.components.pastTrips
import driver.ui.pages.HomeScreen
import driver.ui.pages.MapsActivityContent
import driver.ui.pages.PastActivityContent
import driver.ui.pages.notificationScreen


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
    if (startScreen == "login") {
        val myIntent = Intent(LocalContext.current, PhoneNumberActivity::class.java)
        myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        LocalContext.current.startActivity(myIntent)
    }

    NavHost(navController = navController, startDestination = startScreen) {
        composable("current-assignment-detail") {
            MapsActivityContent(navController, operatorId, selectedAssignmentCode,deBoardingPlaceId, boardingPlaceId)
        }
        composable("past-assignment-detail") {
            PastActivityContent(navController, operatorId, selectedAssignmentCode)
        }
        composable("user-profile") {
            userProfileView(navController)
        }
        composable("home") {
            HomeScreen(
                navController = navController,
                onTripSelected = {
                    selectedAssignmentCode = it.tripCode
                    tripId = it.tripId
                    operatorId = it.companyId
                    deBoardingPlaceId= "MPS"
                    boardingPlaceId = "WYC"
                    navController.navigate("current-assignment-detail")
                },
                onPastTripSelected = {
                    selectedAssignmentCode = it.tripCode
                    operatorId = 20
                    navController.navigate("past-assignment-detail")
                }
            )
        }
        composable("login") {

        }

        composable("past-trips-list") {
            pastTrips(navHostController = navController, screen = "app",
                onTripSelected = {
                selectedAssignmentCode = it.tripCode
                tripId = it.tripId
                operatorId = 20
                deBoardingPlaceId= "MPS"
                boardingPlaceId = "WYC"
                navController.navigate("current-assignment-detail")
            })

        }
        composable("notification"){
            notificationScreen()
        }
    }
}