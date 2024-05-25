package driver.ui

import android.content.Intent
import android.os.Build
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
import driver.Destination
import driver.models.Event
import driver.models.PostsFeed
import driver.ui.components.AddNoticeEvent
import driver.ui.components.AddPost
import driver.ui.components.CommentPost
import driver.ui.components.EventDetail
import driver.ui.components.NoticeDetailPage
import driver.ui.components.addEventPage
import driver.ui.pages.AccountsProfile
import driver.ui.pages.AddInstitute
import driver.ui.pages.AddProfileScreen
import driver.ui.pages.GoogleMapView
import driver.ui.pages.MainScreen
import driver.ui.pages.MapsActivityContent
import driver.ui.pages.PastActivityContent
import driver.ui.pages.PastTrip
import driver.ui.pages.Profile
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

    var noticeId by remember { mutableStateOf("") }




    var boardingPlaceId by remember { mutableStateOf("") }
    var deBoardingPlaceId by remember { mutableStateOf("") }


    var startScreen by remember {
        mutableStateOf(
            if (getAccessToken(context) != null) Destination.HomeScreen else Destination.Login
        )
    }

    if (startScreen == Destination.Login) {
        val myIntent = Intent(LocalContext.current, PhoneNumberActivity::class.java)
        myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        LocalContext.current.startActivity(myIntent)
    }


    NavHost(navController = navController, startDestination = startScreen) {
        composable<Destination.CurrentAssignmentDetail> {
            selectedAssignmentCode = it.arguments?.getString("selectedAssignmentCode").toString()
            passengerTripId = it.arguments!!.getInt("passengerTripId")
            operatorId = it.arguments!!.getInt("operatorId")
            val activity = LocalContext.current as? ComponentActivity

            MapsActivityContent(
                navController,
                passengerTripId,
                selectedAssignmentCode,
                operatorId,
                activity = activity ?: return@composable
            )

        }
        composable<Destination.PastAssignmentDetail> {
            selectedAssignmentCode = it.arguments!!.getString("selectedAssignmentCode").toString()
            passengerTripId = it.arguments!!.getInt("passengerTripId")
            val activity = LocalContext.current as? ComponentActivity

            PastActivityContent(
                navController,
                1,
                passengerTripId,
                selectedAssignmentCode,
                activity = activity ?: return@composable
            )
        }

        composable<Destination.HomeScreen> {
            navController.navigate(Destination.ProfilesList)
        }

        composable<Destination.ProfilesList> {
            AccountsProfile(navController::navigate, navController)
        }

        composable<Destination.AddProfile> {
            AddProfileScreen(navController)
        }

        composable("add-Event-Form") {
            addEventPage(profileId)
        }

        composable<Destination.EventDetails> {
            val eventId = it.arguments!!.getString("eventId")
            EventDetail(eventId, navController)
        }

        composable<Destination.SchoolProfile> {
            schoolProfile()
        }

        composable<Destination.AddNoticeForm> {
            AddNoticeEvent()
        }

        composable<Destination.PostPage> {
            AddPost(navController = navController)
        }

        composable<Destination.UserProfile> {
            Profile(navController)
        }

        composable("add-Institute") {
            AddInstitute(navController)
        }

        composable<Destination.AddComment> {
            val postId = it.arguments!!.getString("postId")
            CommentPost(navController, postId)
        }

        composable<Destination.Login> {

        }

        composable<Destination.NewHomeScreen> {
            MainScreen(
                navController,
                onCommentClick = {},
                onTripsClick = { /*TODO*/ },
                onEventsClick = { /*TODO*/ },
                onHomeClick = { /*TODO*/ },
                onNoticesClick = { /*TODO*/ }) {
            }
        }

        composable<Destination.MapScreen> {
            selectedAssignmentCode = it.arguments?.getString("tripCode").toString()
            passengerTripId = it.arguments!!.getInt("passengerTripId")
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

        composable<Destination.PastTripsList> {
            PastTrip(onPastTripSelected = {
                selectedAssignmentCode = it.tripCode
                passengerTripId = it.passengerTripId
                operatorId = 1
                deBoardingPlaceId = "MPS"
                boardingPlaceId = "WYC"
                navController.navigate(Destination.PastAssignmentDetail(selectedAssignmentCode, passengerTripId))
            }, "app", navController)
        }

        composable(
            "notification/{userId}", arguments = listOf(
                navArgument("userId") { type = NavType.IntType },
            )
        ) { backStackEntry ->
            val idUser = backStackEntry.arguments?.getInt("userId") ?: 0
            notificationScreen(idUser, navController)
        }

        composable<Destination.NoticeDetail>{
            val arg = it.arguments
            noticeId = it.arguments?.getString("noticeId").toString()
            NoticeDetailPage(noticeId = noticeId, onReadClick = { /*TODO*/ }) {
                
            }
        }





    }
}