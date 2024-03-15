package driver.ui.pages

import android.Manifest
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.location.LocationManager
import android.net.ConnectivityManager
import android.os.Build
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.drishto.driver.LocationService
import com.drishto.driver.PhoneNumberActivity
import com.drishto.driver.R
import com.drishto.driver.models.DriverPlans
import com.drishto.driver.network.clearSession
import com.drishto.driver.ui.viewmodels.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import driver.ui.components.AssignedPlans
import driver.ui.components.AssignedTrip
import driver.ui.components.AssignedVehicle
import driver.ui.components.GeneratedCodeDialog
import driver.ui.viewmodels.HomeViewModel
import driver.ui.viewmodels.MatrixLogViewModel
import driver.ui.viewmodels.TripsAssigned
import java.text.SimpleDateFormat


@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun HomeScreen(
    navController: NavHostController,
    vm: HomeViewModel = hiltViewModel(),
    mv: MatrixLogViewModel = hiltViewModel(),
    onTripSelected: (assignment: TripsAssigned) -> Unit,
    onAssignedPlansSelected: (plans: DriverPlans) -> Unit,
    activity: ComponentActivity

) {
    activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

    val matList by mv.matrixList.collectAsStateWithLifecycle()

    val context = LocalContext.current

    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val isConnected = runCatching {
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        activeNetworkInfo != null && activeNetworkInfo.isConnected
    }.getOrDefault(false)

    val driverPlanData by vm.driverPlan.collectAsStateWithLifecycle()
    vm.driverPlanAssignment(context = context)

    Log.d("TAG", "HomeScreen: $driverPlanData")


    var locations by remember {
        mutableStateOf(false)
    }

    var userProfile by remember {
        mutableStateOf(false)
    }

    var history by remember {
        mutableStateOf(false)
    }
    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    val isLocationEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    val locationEnabledState = rememberUpdatedState(isLocationEnabled)

    if (!locationEnabledState.value) {
        LocationDisabledDialog()
    } else {
        Log.e("Location", "LocationPermissionCheck: dialog is disabledd ",)
    }


    val vw: SwipeRefresh = viewModel()
    val isLoading by vw.isLoading.collectAsStateWithLifecycle()
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isLoading)
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        var expander by remember {
            mutableStateOf(false)
        }
//        Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically)
//        }
        Box(modifier = Modifier.fillMaxSize()) {
            com.google.accompanist.swiperefresh.SwipeRefresh(
                state = swipeRefreshState,
                onRefresh = vw::loadstuff
            ) {
                LazyColumn {
                    item {
                        if (isConnected) {
                            mv.loadMatrixLog(context = context)

                            val inputFormat = SimpleDateFormat("yyyy-dd-MM'T'HH:mm")
                            val outputFormat = SimpleDateFormat("HH:mm a")

                            val currentAssignmentData by vm.currentAssignment.collectAsStateWithLifecycle()
                            vm.fetchAssignmentDetail(context = context)

                            fun isLocationServiceRunning(
                                context: Context,
                                serviceClass: Class<*>
                            ): Boolean {
                                val manager =
                                    context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
                                for (service in manager.getRunningServices(Integer.MAX_VALUE)) {
                                    if (serviceClass.name == service.service.className) {
                                        return true
                                    }
                                }
                                return false
                            }
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                            ) {
                                Row(modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                                Scaffold(topBar = {
                                    TopAppBar(
                                        modifier = Modifier.padding(end = 13.dp),
                                        title = { Text(text = "DRISHTO",style = TextStyle(
                                                color = Color.Red,
                                            fontSize = 28.sp,
                                            fontWeight = FontWeight.W600,
                                            fontFamily = FontFamily.SansSerif
                                        )) },
                                        navigationIcon = {
//                IconButton(onClick = { /*TODO*/ }) {
//
                                                         //                     Icon(imageVector = Icons.Filled.Menu, contentDescription = null)
//                }
                                        },
                                        actions = {
                                            IconButton(onClick = { expander = true }) {
                                                Icon(imageVector = Icons.Filled.MoreVert, contentDescription = null)
                                            }

                                            DropdownMenu(
                                                expanded = expander,
                                                onDismissRequest = { expander = false }) {
                                                DropdownMenuItem(text = { Text(text = "User Profile") },
                                                    onClick = { userProfile = true; expander = false },
                                                    leadingIcon = {
                                                        Icon(
                                                            imageVector = Icons.Filled.Person,
                                                            contentDescription = null
                                                        )
                                                    })

                                                DropdownMenuItem(text = { Text(text = "Locations") },
                                                    onClick = { locations = true; expander = false },
                                                    leadingIcon = {
                                                        Icon(
                                                            imageVector = Icons.Filled.Place,
                                                            contentDescription = null
                                                        )
                                                    })

                                                DropdownMenuItem(text = { Text(text = "History") },
                                                    onClick = { history = true; expander = false },
                                                    leadingIcon = {
                                                        Icon(
                                                            imageVector = Icons.Filled.History,
                                                            contentDescription = null
                                                        )
                                                    })

                                                DropdownMenuItem(text = { Text(text = "Log out") },
                                                    onClick = {
                                                        val myIntent =
                                                            Intent(context, PhoneNumberActivity::class.java)
                                                        clearSession(context)
                                                        myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                                                        val location = Intent(context, LocationService::class.java)
                                                        context.stopService(location)
                                                        context.startActivity(myIntent)
                                                    },
                                                    leadingIcon = {
                                                        Icon(
                                                            imageVector = Icons.Filled.Logout,
                                                            contentDescription = null
                                                        )
                                                    })
                                            }
                                        }
                                    )
                                }, content = {
                                    it

                                }

                                )
                                }
                                Row(modifier = Modifier.fillMaxSize()) {


                                currentAssignmentData?.let {
//                        LocationPermissionScreen()
                                    RequestPermission(permission = Manifest.permission.ACCESS_FINE_LOCATION)
                                    Column {
                                        it.vehicles.let { vList ->
                                            Column {
//                                    vList.forEach { vehicleAssignment ->
//                                        AssignedVehicle(vehicleAssignment)
//                                    }
//
//                                    LazyColumn {
//                                        items(vList) { vehicleAssignment ->
//                                            AssignedVehicle(vehicleAssignment)
//                                        }
//                                    }
                                                Column {
                                                    Row(
                                                        modifier = Modifier
                                                            .fillMaxWidth()
                                                            .padding(13.dp, top = 20.dp),
                                                        horizontalArrangement = Arrangement.SpaceBetween,
                                                        verticalAlignment = Alignment.CenterVertically
                                                    ) {

                                                        Text(
                                                            text = "Vehicle Assigned ",
                                                            style = TextStyle(
                                                                color = Color.Black,
                                                                fontSize = 16.sp,
                                                                fontWeight = FontWeight.W600,
                                                                fontFamily = FontFamily.SansSerif
                                                            )
                                                        )
                                                    }
                                                    vList.take(vList.size)
                                                        .forEach { vehicleAssignment ->
                                                            AssignedVehicle(
                                                                vehicleAssignment
                                                            )
                                                        }
                                                }
                                                Row(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .padding(
                                                            start = 16.dp,
                                                            bottom = 16.dp
                                                        )
                                                ) {
                                                    Button(
                                                        onClick = {
                                                            vm.generateAssignmentCode(
                                                                context
                                                            )
                                                        }
                                                    ) {
                                                        Text(text = "Generate Code")
                                                    }
                                                }
                                                if (currentAssignmentData!!.isAssignmentCodeVisible) {
                                                    GeneratedCodeDialog(
                                                        currentAssignmentData?.assignmentCode
                                                            ?: "",
                                                        setShowDialog = {
                                                            vm.hideAssignmentCode(context)
                                                        }
                                                    )
                                                }
                                            }

//                                            Column {
//                                                Row(
//                                                    modifier = Modifier
//                                                        .fillMaxWidth()
//                                                        .padding(13.dp, top = 20.dp),
//                                                    horizontalArrangement = Arrangement.SpaceBetween,
//                                                    verticalAlignment = Alignment.CenterVertically
//                                                ) {
//
//                                                    Text(
//                                                        text = "Plans Associated ",
//                                                        style = TextStyle(
//                                                            color = Color.Black,
//                                                            fontSize = 16.sp,
//                                                            fontWeight = FontWeight.W600,
//                                                            fontFamily = FontFamily.SansSerif
//                                                        )
//                                                    )
//                                                }
//
//                                                driverPlanData?.forEach { plan->
//                                                    AssignedPlans(
//                                                        plan,
////                                                            onTripSelected
//                                                    )
//                                                }
//                                            }

                                            if (it.trips.size == 0) {
//                                                Box(
//                                                    modifier = Modifier
//                                                        .fillMaxSize()
//                                                        .padding(13.dp)
//                                                        .align(Alignment.CenterHorizontally)
//                                                ) {
//                                                    Row(
//                                                        modifier = Modifier.fillMaxSize(),
//                                                        horizontalArrangement = Arrangement.Center,
//                                                        verticalAlignment = Alignment.CenterVertically
//                                                    ) {
//                                                        Text(
//                                                            text = "No trips assigned!!",
//                                                            style = TextStyle(
//                                                                color = Color.Black,
//                                                                fontSize = 14.sp,
//                                                                fontWeight = FontWeight.Medium
//                                                            )
//                                                        )
//                                                    }
//                                                }
                                                val location =
                                                    Intent(
                                                        context,
                                                        LocationService::class.java
                                                    )
                                                context.stopService(location)
                                            } else {
                                                val location =
                                                    Intent(
                                                        context,
                                                        LocationService::class.java
                                                    )
                                                context.startForegroundService(location)
                                                val loc = LocationService::class.java
                                                val service =
                                                    isLocationServiceRunning(context, loc)
                                                if (service) {
                                                    Column(modifier = Modifier.fillMaxWidth()) {
                                                        Image(
                                                            painter = painterResource(id = R.drawable.signal),
                                                            contentDescription = null,
                                                            Modifier
                                                                .height(100.dp)
                                                                .fillMaxSize()
                                                        )
                                                        matList?.let { mList ->
                                                            if (mList.isNotEmpty()) {
                                                                val lastTime =
                                                                    mList.last().time

                                                                val parsedDate =
                                                                    inputFormat.parse(
                                                                        lastTime.toString()
                                                                    )
                                                                val formattedDate =
                                                                    outputFormat.format(
                                                                        parsedDate
                                                                    )
                                                                Row(
                                                                    modifier = Modifier.fillMaxWidth(),
                                                                    horizontalArrangement = Arrangement.Center
                                                                ) {
                                                                    Text(text = "Last recorded location time ${formattedDate} ")

                                                                }
                                                            } else {
                                                                Column(modifier = Modifier.fillMaxWidth()) {
                                                                    Row(
                                                                        modifier = Modifier.fillMaxWidth(),
                                                                        horizontalArrangement = Arrangement.Center
                                                                    ) {
                                                                        Text(text = "Last recorded location time - Not shared ")
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                Column {
                                                    if(it.trips.size >0) {
                                                        Row(
                                                            modifier = Modifier
                                                                .fillMaxWidth()
                                                                .padding(13.dp, top = 20.dp),
                                                            horizontalArrangement = Arrangement.SpaceBetween,
                                                            verticalAlignment = Alignment.CenterVertically
                                                        ) {

                                                            Text(
                                                                text = "Assigned Trips ",
                                                                style = TextStyle(
                                                                    color = Color.Black,
                                                                    fontSize = 16.sp,
                                                                    fontWeight = FontWeight.W600,
                                                                    fontFamily = FontFamily.SansSerif
                                                                )
                                                            )
                                                        }
                                                    }
                                                    it.trips.take(it.trips.size)
                                                        .forEach { trip ->
                                                            AssignedTrip(
                                                                trip,
                                                                onTripSelected
                                                            )
                                                        }
                                                }

                                            }
                                            if(driverPlanData?.size!! >0) {
                                                Column {
                                                    Row(
                                                        modifier = Modifier
                                                            .fillMaxWidth()
                                                            .padding(13.dp, top = 20.dp),
                                                        horizontalArrangement = Arrangement.SpaceBetween,
                                                        verticalAlignment = Alignment.CenterVertically
                                                    ) {

                                                        Text(
                                                            text = "Plans Associated ",
                                                            style = TextStyle(
                                                                color = Color.Black,
                                                                fontSize = 16.sp,
                                                                fontWeight = FontWeight.W600,
                                                                fontFamily = FontFamily.SansSerif
                                                            )
                                                        )
                                                    }

                                                    driverPlanData?.forEach { plan ->
                                                        AssignedPlans(
                                                            plan,
                                                            onAssignedPlansSelected
                                                        )
                                                    }
                                                }
                                            }
                                            if (it.userLocationVisible) {
                                                MatrixLog(activity)
                                            }
                                        }
                                    }
                                }
                                }
                            }

                        } else {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(48.dp)
                                )
                                Toast.makeText(
                                    context,
                                    "Please connect to a network and restart application",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
            }
        }

    }

    if (locations) {
        navController.navigate("locations-screen")
        locations = false
    }

    if (userProfile) {
        navController.navigate("user-profile")
        userProfile = false
    }

    if (history) {
        navController.navigate("history")
        history = false
    }

}

@Composable
fun LocationDisabledDialog() {

    val context = LocalContext.current
    val alertDialogShown = remember { mutableStateOf(true) }

    if (alertDialogShown.value) {
        AlertDialog(
            onDismissRequest = {
                val locationManager =
                    context.getSystemService(ComponentActivity.LOCATION_SERVICE) as LocationManager
                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    alertDialogShown.value = false
                }
            },
            title = { Text("Location is disabled") },
            text = {
                Column {
                    Text("Please enable location to use this app.")
                    Text("Go to Settings to enable location.")
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                    }
                ) {
                    Text("Go to Settings")
                }
            }
        )
    }
}
