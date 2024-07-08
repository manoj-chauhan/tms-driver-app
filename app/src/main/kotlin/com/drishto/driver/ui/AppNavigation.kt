package com.drishto.driver.ui

import android.content.Intent
import android.os.Build
import androidx.activity.ComponentActivity
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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.drishto.driver.PhoneNumberActivity
import com.drishto.driver.models.ChildrenList
import com.drishto.driver.network.getAccessToken
import com.drishto.driver.ui.pages.HistoryScreen
import com.drishto.driver.ui.pages.UserProfile
//import com.samrish.driver.ui.pages.PastAssignmentDetailScreen
import com.drishto.driver.ui.components.EditChildrenDetails
import com.drishto.driver.ui.components.StudentInPlan
import com.drishto.driver.ui.pages.AssignmentDetailScreen
import com.drishto.driver.ui.pages.ChildrenPlanDetail
import com.drishto.driver.ui.pages.History
import com.drishto.driver.ui.pages.HomeScreen
import com.drishto.driver.ui.pages.MatrixLog
import com.drishto.driver.ui.pages.NewAssignmentDetailScreen
import com.drishto.driver.ui.pages.PastAssignmentDetailScreen


const val MY_ARG= "message"
const val MY_URI = "https://trip-details"
const val trip_Id :Int=0
const val operatorI:Int = 1




@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigationHost(
    navController: NavHostController
) {

    val context = LocalContext.current

    var selectedAssignmentCode by remember {
        mutableStateOf("")
    }

    var operatorId by remember {
        mutableIntStateOf(0)
    }

    var tripId by remember {
        mutableIntStateOf(0)
    }

    var planCode by remember {
        mutableStateOf("")
    }

    var planId by remember {
        mutableStateOf(0)
    }

    var childrenList : ChildrenList? = null

    var startScreen: String by remember {
        mutableStateOf(
            if (getAccessToken(context) != null) "home" else "login"
        )
    }

//    if (startScreen == "login") {
//        val myIntent = Intent(LocalContext.current, PhoneNumberActivity::class.java)
//        myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
//        LocalContext.current.startActivity(myIntent)
//    }

    NavHost(navController = navController, startDestination = startScreen) {
        composable("current-assignment-detail") {
            val activity = LocalContext.current as? ComponentActivity

//            AssignmentDetailScreen(
//                navController = navController,
//                selectedAssignment = selectedAssignmentCode,
//                operatorId = operatorId,
//                tripId = tripId,
//                tripCode = selectedAssignmentCode,
//                activity = activity ?: return@composable
//            )



            NewAssignmentDetailScreen(
            navController = navController,
            onTripSelected = {
                selectedAssignmentCode = it.tripCode
                operatorId = it.operatorCompanyId
                tripId = it.tripId
                navController.navigate("current-assignment-detail")
            },
            onAssignedPlansSelected = {
                operatorId = it.companyId
                planCode = it.planCode
                planId = it.id
                navController.navigate("driver-plans-details")
            },
            activity = activity ?: return@composable
            )

//            composable(
//                "home"
//            ) {
//                val activity = LocalContext.current as? ComponentActivity
//
//                HomeScreen(
//                    navController = navController,
//                    onTripSelected = {
//                        selectedAssignmentCode = it.tripCode
//                        operatorId = it.operatorCompanyId
//                        tripId = it.tripId
//                        navController.navigate("current-assignment-detail")
//                    },
//                    onAssignedPlansSelected = {
//                        operatorId = it.companyId
//                        planCode = it.planCode
//                        planId = it.id
//                        navController.navigate("driver-plans-details")
//                    },
//                    activity = activity ?: return@composable
//                )
//            }


        }

        composable(
            "locations-screen"
        ) {
            val activity = LocalContext.current as? ComponentActivity

            MatrixLog(activity = activity ?: return@composable)
        }

        composable("history") {
            val activity = LocalContext.current as? ComponentActivity

            HistoryScreen(onTripSelected = {
                selectedAssignmentCode = it.tripCode
                operatorId = it.operatorCompanyId
                tripId = it.tripId
                navController.navigate("past-assignment-detail")
            },activity = activity ?: return@composable)
        }

        composable("user-profile") {
            val activity = LocalContext.current as? ComponentActivity

            UserProfile(activity = activity ?: return@composable)
        }

        composable("past-assignment-detail"){
            val activity = LocalContext.current as? ComponentActivity

            PastAssignmentDetailScreen(navController = navController, operatorId = operatorId, tripId = tripId, tripCode = selectedAssignmentCode,activity = activity ?: return@composable)
        }

        composable(
            "home"
        ) {
            val activity = LocalContext.current as? ComponentActivity

            HomeScreen(
                navController = navController,
                onTripSelected = {
                    selectedAssignmentCode = it.tripCode
                    operatorId = it.operatorCompanyId
                    tripId = it.tripId
                    navController.navigate("current-assignment-detail")
                },
                onAssignedPlansSelected = {
                    operatorId = it.companyId
                    planCode = it.planCode
                    planId = it.id
                    navController.navigate("driver-plans-details")
                },
                activity = activity ?: return@composable
            )
        }

        composable("student-addition/{operatorId}/{planCode}", arguments = listOf(
            navArgument("operatorId") { type = NavType.IntType},
            navArgument("planCode") { type = NavType.StringType}
        )){entry ->
            val activity = LocalContext.current as? ComponentActivity

            val operator = entry.arguments?.getInt("operatorId") ?: 0
            val plan = entry.arguments?.getString("planCode") ?: ""
            StudentInPlan(operator,plan,planId,navController, activity = activity ?: return@composable)
        }
        composable("driver-plans-details"){
            val activity = LocalContext.current as? ComponentActivity

            ChildrenPlanDetail(operatorId, planId,planCode, navController, onStudentSelected = {
                childrenList = it
                navController.navigate("children-details")
            }, activity = activity ?: return@composable)
        }

        composable("children-details"){
            val activity = LocalContext.current as? ComponentActivity

            childrenList?.let { it1 -> EditChildrenDetails(it1, operatorId, planCode, navController, activity = activity ?: return@composable) }
        }

        composable("history_detail"){
            val activity = LocalContext.current as? ComponentActivity

            History(navController = navController, selectedAssignmentCode, operatorId, activity = activity ?: return@composable)
        }

        composable("login"){

        }

//        composable("assignment", arguments = listOf(
//            navArgument(MY_ARG) { type = NavType.StringType } ,
//            navArgument("$trip_Id") { type = NavType.IntType },
//            navArgument("$operatorI") { type = NavType.IntType },
//            ),
//            deepLinks = listOf(navDeepLink {
//                uriPattern = "$MY_URI/$MY_ARG={$MY_ARG}/$trip_Id={$trip_Id}&$operatorI={$operatorI}"
//            })
//        ){
//            val activity = LocalContext.current as? ComponentActivity
//
//            val argument = it.arguments
//            if (argument != null) {
//                    val myArgValue = argument.getString(MY_ARG)
//                    val h = argument.getInt("$trip_Id")
//                    val o = argument.getInt("$operatorI")
//
//                    if (myArgValue != null) {
//                        DetailsScree(myArgValue, h,o, navController, activity = activity ?: return@composable)
//                    }
//            }
//        }
    }
}

@Composable
fun DetailsScree(message: String,tripId:Int,operatorI:Int,navController: NavHostController,     activity: ComponentActivity) {
    AssignmentDetailScreen(
        navController = navController,
        selectedAssignment = message,
        operatorId = operatorI,
        tripId = tripId,
        tripCode = message,
        activity
    )



}
