package driver.ui

import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.drishto.driver.PhoneNumberActivity
import com.drishto.driver.network.getAccessToken
import com.drishto.driver.ui.pages.userProfileView
import driver.models.Event
import driver.models.PostsFeed
import driver.ui.components.AddNoticeEvent
import driver.ui.components.CommentPost
import driver.ui.components.EventDetail
import driver.ui.components.addEventPage
import driver.ui.components.pastTrips
import driver.ui.pages.AccountsProfile
import driver.ui.pages.AddInstitute
import driver.ui.pages.AddProfileScreen
import driver.ui.pages.GoogleMapView
import driver.ui.pages.HomeScreen
import driver.ui.pages.HomeScreenNavigation
import driver.ui.pages.MainScreen
import driver.ui.pages.MapsActivityContent
import driver.ui.pages.PastActivityContent
import driver.ui.pages.PostItem
import driver.ui.pages.Profile
import driver.ui.pages.SavedNoticesPage
import driver.ui.pages.notificationScreen
import driver.ui.pages.schoolProfile


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigationHost(
    navController: NavHostController
) {

    val context = LocalContext.current

    var eventDetail by remember { mutableStateOf<Event?>(null) }
    var selectedAssignmentCode by remember { mutableStateOf("") }
    var operatorId by remember { mutableIntStateOf(0) }
    var passengerTripId by remember { mutableIntStateOf(0) }
    var profileId by remember { mutableStateOf("") }
    var postDetails by remember { mutableStateOf<PostsFeed?>(null) }


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
            val activity = LocalContext.current as? ComponentActivity
            MapsActivityContent(navController, passengerTripId, selectedAssignmentCode, operatorId,activity = activity ?: return@composable)
        }
        composable("past-assignment-detail") {
            val activity = LocalContext.current as? ComponentActivity

            PastActivityContent(navController, 1, passengerTripId, selectedAssignmentCode,activity = activity ?: return@composable)
        }
        composable("user-profile") {
            userProfileView(navController)
        }
        composable("home") {
            navController.navigate("profilesList")
        }

        composable("profilesList"){
            AccountsProfile(navController ,onProfileSelected = {
                profileId = it
                navController.navigate("newHomeScreen")
            })
        }

        composable("add-Profile"){
            AddProfileScreen(navController)
        }

        composable("add-Event-Form"){
            addEventPage(profileId)
        }

        composable("event-details/{eventId}",arguments = listOf(
                navArgument("eventId"){
                    type = NavType.StringType
                }
                )) {

            val eventId = it.arguments!!.getString("eventId")
            EventDetail(eventId, navController)
        }

        composable("notice-details/{noticeId}",arguments = listOf(
            navArgument("eventId"){
                type = NavType.StringType
            }
        )) {

            val noticeId = it.arguments!!.getString("eventId")
            EventDetail(noticeId, navController)
        }

        composable("school-profile"){
            schoolProfile()
        }

        composable("notice_lists"){
            SavedNoticesPage(
            navController= navController)

        }
        composable("add-Notice-Form"){
            AddNoticeEvent()
        }

        composable("post_page"){
            PostItem(profileId,navController = navController)
        }
        
        composable("user_profile"){
            Profile(navController)
        }

        composable("home-screen"){
            HomeScreenNavigation(profileId,navController = navController) {
                postDetails = it
                navController.navigate("add_comment")
                Log.d("TAG", "AppNavigationHost: $postDetails")
            }
        }

        composable("add-Institute"){
            AddInstitute(navController)
        }

        composable("add_comment/{postId}", arguments = listOf(
            navArgument("postId"){
                type = NavType.StringType
            }
        )){
            val postId = it.arguments!!.getString("postId")
            CommentPost(navController,postId)
        }
        composable("MainScreen"){
            val activity = LocalContext.current as? ComponentActivity
            HomeScreen(
                navController = navController,
                onTripSelected = {
                    selectedAssignmentCode = it.tripCode
                    passengerTripId = it.passengerTripId
                    operatorId = it.companyId
                    deBoardingPlaceId = "MPS"
                    boardingPlaceId = "WYC"
                    navController.navigate("current-assignment-detail")
                },
                onPastTripSelected = {
                    selectedAssignmentCode = it.tripCode
                    operatorId = 1
                    passengerTripId = it.passengerTripId
                    navController.navigate("past-assignment-detail")
                },
                activity = activity ?: return@composable
            )
        }
        composable("login") {

        }

        composable("newHomeScreen"){
            MainScreen(
                profileId,
                navController,
                onCommentClick = {},
                onTripsClick = { /*TODO*/ },
                onEventsClick = { /*TODO*/ },
                onHomeClick = { /*TODO*/ },
                onNoticesClick = { /*TODO*/ }) {
            }
        }

        composable("map-screen") {
            val activity = LocalContext.current as? ComponentActivity

            GoogleMapView(
                modifier = Modifier.fillMaxWidth(),
                passengerTripId = passengerTripId,
                tripCode = selectedAssignmentCode,
                navController,
                onMapLoaded = {},
                activity = activity ?: return@composable
            )
        }

        composable("past-trips-list") {
            pastTrips(navHostController = navController, screen = "app",
                onTripSelected = {
                    selectedAssignmentCode = it.tripCode
                    passengerTripId = it.passengerTripId
                    operatorId = 1
                    deBoardingPlaceId = "MPS"
                    boardingPlaceId = "WYC"
                    navController.navigate("past-assignment-detail")
                })

        }

        composable(
            "notification/{userId}", arguments = listOf(
                navArgument("userId") { type = NavType.IntType },
            )
        ) { backStackEntry ->
            val idUser = backStackEntry.arguments?.getInt("userId") ?: 0
            notificationScreen(idUser, navController)
        }
    }
}