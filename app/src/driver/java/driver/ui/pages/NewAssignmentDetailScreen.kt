package driver.ui.pages


import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo

import android.location.LocationManager
import android.net.ConnectivityManager
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.LocationOff
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.fontscaling.MathUtils.lerp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.FractionalThreshold
import androidx.wear.compose.material.rememberSwipeableState
import androidx.wear.compose.material.swipeable
import com.drishto.driver.LocationService
import com.drishto.driver.R
import com.drishto.driver.models.DriverPlans
import com.drishto.driver.models.History
import com.drishto.driver.models.Schedule
import com.drishto.driver.models.ScheduleLocation
import com.drishto.driver.ui.operatorI
import com.drishto.driver.ui.viewmodels.HistoryViewModel
import com.drishto.driver.ui.viewmodels.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.skydoves.flexible.bottomsheet.material3.FlexibleBottomSheet
import com.skydoves.flexible.core.FlexibleSheetSize
import com.skydoves.flexible.core.rememberFlexibleBottomSheetState
import driver.ui.actionColors
import driver.ui.components.AssignedVehicle
import driver.ui.components.CallCheckInDialog
import driver.ui.components.DocumentsDialog
import driver.ui.components.GeneratedCodeDialog
import driver.ui.components.LocationList
import driver.ui.components.StartTripDialog
import driver.ui.components.TripInfoNewDesign
import driver.ui.generateButton
import driver.ui.headingColor
import driver.ui.placeColor
import driver.ui.subHeadingColor
import driver.ui.subText

import driver.ui.textColor
import driver.ui.viewmodels.AssignmentDetailViewModel
import driver.ui.viewmodels.HomeViewModel
import driver.ui.viewmodels.MatrixLogViewModel
import driver.ui.viewmodels.PastAssignmentDetailViewModel
import driver.ui.viewmodels.TripsAssigned
import driver.ui.viewmodels.VehicleAssignment
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import kotlin.math.roundToInt


val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

val fontName = GoogleFont("Russo One")

val fontFamily = FontFamily(
    Font(googleFont = fontName, fontProvider = provider)
)
val logo = Color(android.graphics.Color.parseColor("#ef2427"))

@Composable
fun Topbar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Transparent)
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(
                imageVector = Icons.Outlined.Person,
                contentDescription = "profile",

                tint = Color.Black,
                modifier = Modifier
                    .size(30.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray)
            )

        }




        Button(modifier = Modifier
            .width(260.dp)
            .height(40.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0XFFF9E7E6)),
            onClick = { }) {


            Text(
                modifier = Modifier.fillMaxWidth(),

                text = "Home",


                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                color = Color(0XFF5c3939),


                )

        }

        Icon(
            imageVector = Icons.Outlined.Notifications,
            contentDescription = "Notifications",
            modifier = Modifier.size(30.dp)
        )


    }


}

@Composable
fun NewAssignmentDetailScreen(
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

    var locations by remember {
        mutableStateOf(false)
    }

    var userProfile by remember {
        mutableStateOf(false)
    }

    var history by remember {
        mutableStateOf(false)
    }

    var permit by remember {
        mutableStateOf(false)
    }

    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    val isLocationEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    val locationEnabledState = rememberUpdatedState(isLocationEnabled)

    val vw: SwipeRefresh = viewModel()
    val isLoading by vw.isLoading.collectAsStateWithLifecycle()
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isLoading)

    val lightPeach = Color(0XFFFFF4EA)
    val darkPeach = Color(0XFFFEE1DC)

    val gradientBrush = Brush.verticalGradient(
        colors = listOf(lightPeach, darkPeach), startY = 0f, endY = 1000f
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(gradientBrush)
    ) {

        Column(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .background(Color.Transparent)
        ) {
            if (isConnected) {

                mv.loadMatrixLog(context = context)

                val inputFormat = SimpleDateFormat("yyyy-dd-MM'T'HH:mm")
                val outputFormat = SimpleDateFormat("HH:mm a")

                val currentAssignmentData by vm.currentAssignment.collectAsStateWithLifecycle()
                vm.fetchAssignmentDetail(context = context)

                val driverPlanData by vm.driverPlan.collectAsStateWithLifecycle()
                vm.driverPlanAssignment(context = context)

                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Topbar()





                    currentAssignmentData?.let {
                        RequestPermission(permission = Manifest.permission.ACCESS_FINE_LOCATION)

                        var isAnyTripStarted = false;

                        it.trips.forEach { trip ->
                            if (!isAnyTripStarted) {
                                if (trip.status != "TRIP_CREATED") {
                                    isAnyTripStarted = true;
                                }
                            }
                        }

                        if (it.trips.isEmpty()) {
                            val location = Intent(context, LocationService::class.java)
                            context.stopService(location)
                        } else {
                            if (isAnyTripStarted) {
                                val loc = LocationService::class.java
                                val service = isLocationServiceRunning(context, loc)
                                if (service) {

                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(Color.Transparent)
                                            .padding(6.dp)
                                    ) {
                                        currentAssignmentData?.let {
                                            RequestPermission(permission = Manifest.permission.ACCESS_FINE_LOCATION)
                                            it.vehicles.take(it.vehicles.size)
                                                .forEach { vehicleAssignment ->
                                                    AssignedVehicle(vehicleAssignment)
                                                }
                                        }
                                    }




                                    matList?.let { mList ->
                                        if (mList.isNotEmpty()) {
                                            val lastTime = mList.last().time

                                            val parsedDate = inputFormat.parse(lastTime.toString())
                                            val formattedDate = outputFormat.format(parsedDate)

                                            Column(
                                                modifier = Modifier.fillMaxWidth(),
                                                verticalArrangement = Arrangement.Center
                                            ) {
                                                Row(
                                                    modifier = Modifier.fillMaxWidth(),
                                                    horizontalArrangement = Arrangement.Absolute.Center
                                                ) {
                                                    Icon(
                                                        imageVector = Icons.Outlined.LocationOn,
                                                        tint = Color.Green,
                                                        contentDescription = "location",
                                                        modifier = Modifier.size(20.dp)
                                                    )
                                                    Spacer(modifier = Modifier.width(6.dp))

                                                    Text(
                                                        color = headingColor,
                                                        text = "You are sharing your location....",
                                                        fontFamily = FontFamily.SansSerif,
                                                        fontSize = 14.sp,
                                                        fontWeight = FontWeight.W500,
                                                    )
                                                }

                                                Text(
                                                    modifier = Modifier.fillMaxWidth(),
                                                    text = "Last Location Shared at  ${formattedDate}",
                                                    textAlign = TextAlign.Center,
                                                    fontSize = 10.sp,
                                                    color = textColor,
                                                )
                                                Spacer(modifier = Modifier.height(5.dp))

                                            }


                                        } else {
                                            Column(
                                                modifier = Modifier.fillMaxWidth(),
                                                verticalArrangement = Arrangement.Center
                                            ) {

                                                Row(
                                                    modifier = Modifier.fillMaxWidth(),
                                                    horizontalArrangement = Arrangement.SpaceBetween
                                                ) {
                                                    Icon(
                                                        imageVector = Icons.Outlined.LocationOff,
                                                        tint = Color.Green,
                                                        contentDescription = "location",
                                                        modifier = Modifier.size(24.dp)
                                                    )
                                                    Spacer(modifier = Modifier.width(5.dp))
                                                    Text(text = "Last recorded location time - Not shared ")
                                                }
                                            }
                                        }
                                    }


                                } else {
                                    permit = true
                                }
                                if (service && !locationEnabledState.value) {
                                    permit = true
                                }
                            }
                        }

                        Column {
                            Spacer(modifier = Modifier.height(30.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Button(
                                    onClick = {
                                        vm.generateAssignmentCode(context)
                                    },
                                    modifier = Modifier
                                        .height(50.dp)
                                        .width(275.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(0xFFD9F0BC)
                                    )
                                ) {
                                    Text(
                                        text = "Generate Assignment Code",
                                        textAlign = TextAlign.Center,
                                        color = Color(0xFF429D77),
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        modifier = Modifier
                                    )
                                }
                                if (currentAssignmentData!!.isAssignmentCodeVisible) {
                                    GeneratedCodeDialog(currentAssignmentData?.assignmentCode ?: "",
                                        setShowDialog = {
                                            vm.hideAssignmentCode(context)
                                        })
                                }
                            }
                        }
                    }


                    currentAssignmentData?.let {
                        Column {
                            it.trips.take(it.trips.size).forEach { trip ->
                                BottomSheet(
                                    trip, onTripSelected,navController
                                )
                            }

                        }
                    }


                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
    trip: TripsAssigned,
    onClick: (tripsToDriver: TripsAssigned) -> Unit,
    navController: NavHostController,
    vm: AssignmentDetailViewModel = hiltViewModel(),
    pt: PastAssignmentDetailViewModel = hiltViewModel(),


) {


    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(true) }

    var isStartDialogVisible = remember { mutableStateOf(false); }


    val boxgray = Color(0xFFE5E5E5)

    val assignment by vm.assignmentDetail.collectAsStateWithLifecycle()
    val pastAssignment by pt.pastassignmentDetail.collectAsStateWithLifecycle()

    val isStartEnabled = assignment?.activeStatusDetail?.actions?.contains("START")
    val isCheckInEnabled = assignment?.activeStatusDetail?.actions?.contains("CHECKIN")
    val isDepartEnabled = assignment?.activeStatusDetail?.actions?.contains("DEPART")
    val isCancelEnabled = assignment?.activeStatusDetail?.actions?.contains("CANCEL")
    val isEndEnabled = assignment?.activeStatusDetail?.actions?.contains("END")

    val isCheckInDialogVisible = remember { mutableStateOf(false); }

    val context = LocalContext.current


    if (showBottomSheet) {
        FlexibleBottomSheet(
            onDismissRequest = {
                showBottomSheet = true
            },


            sheetState = rememberFlexibleBottomSheetState(
                allowNestedScroll = true,
                isModal = false,
                flexibleSheetSize = FlexibleSheetSize(

                    fullyExpanded = 1f,
                    intermediatelyExpanded = 0.77f,
                    slightlyExpanded = 0.24f,

                    ),


                skipSlightlyExpanded = false,
            ),


            scrimColor = Color.Transparent,
            containerColor = Color.White,
            tonalElevation = BottomSheetDefaults.Elevation,


            ) {

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()

                    .background(color = Color.White)
                    .padding(horizontal = 14.dp)
                    .padding(bottom = 14.dp)
            ) {
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Row {
                                Text(
                                    text = "Trip Code: ",
                                    color = headingColor,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.SemiBold
                                )

                                Text(
                                    text = trip.tripCode,
                                    fontSize = 16.sp,
                                    modifier = Modifier.fillMaxWidth(),
                                    fontWeight = FontWeight.SemiBold,
                                    textAlign = TextAlign.Start
                                )

                            }

                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = trip.tripName,
                                fontSize = 12.sp,
                                color = actionColors,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Start
                            )
                        }
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .padding(8.dp)

                        ) {
                            Icon(
                                imageVector = Icons.Outlined.MoreVert,
                                tint = Color.Black,
                                contentDescription = "Menu",
                                modifier = Modifier.size(28.dp)
                            )
                        }
                    }





                    Spacer(modifier = Modifier.height(16.dp))
                    HorizontalDivider(thickness = 2.dp, color = Color.LightGray)
                }
                item {

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Created by",
                            modifier = Modifier.align(Alignment.CenterVertically),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = "${trip.companyName}(${trip.companyCode})",
                            color = actionColors,
                            fontSize = 12.sp,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))


                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Operated by",
                            modifier = Modifier.align(Alignment.CenterVertically),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold

                        )
                        Text(
                            text = "${trip.operatorCompanyName}(${trip.operatorCompanyCode})",
                            color = actionColors,
                            fontSize = 12.sp,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    HorizontalDivider(thickness = 2.dp, color = Color.LightGray)
                    Spacer(modifier = Modifier.height(20.dp))
                }

                item {


                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .background(Color(0XFFEBF4FA))
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {


                            Icon(
                                imageVector = Icons.Outlined.Info,
                                tint = Color.Blue,
                                contentDescription = "information",
                                modifier = Modifier
                                    .size(50.dp)
                                    .padding(start = 10.dp)
                            )
                            Spacer(modifier = Modifier.width(5.dp))

                            Text(text = trip.status, color = Color.Blue)
                        }

                    }



                    Spacer(modifier = Modifier.height(16.dp))
                    HorizontalDivider(thickness = 2.dp, color = Color.LightGray)
                }

                item {

                    Column(modifier = Modifier.fillMaxWidth()) {



                        val navController = rememberNavController()
                        val activity = LocalContext.current as? ComponentActivity
                        if (activity != null) {
                            TripInfoNewDesign(

                                operatorId = trip.operatorCompanyId,
                                tripId = trip.tripId,
                                tripCode = trip.tripCode,
                                activity = activity
                            )
                        }
                    }
                }


                item {
                    assignment?.let { it ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            if (trip.status == "TRIP_STARTED") {

                                Button(
                                    onClick = {
                                        vm.startTrip(
                                            context,
                                            trip.tripId,
                                            trip.tripCode,
                                            trip.operatorCompanyId
                                        )
                                        isStartDialogVisible.value = true
                                    },
                                    modifier = Modifier
                                        .width(120.dp)
                                        .padding(end = 8.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(0xFFEBF4FA),
                                    )
                                ) {
                                    Text(text = "Start", color = Color.Blue)
                                }

                                Button(
                                    onClick = {
                                        isCheckInDialogVisible.value = true
                                    },
                                    modifier = Modifier.width(120.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(0xFFEBF4FA),
                                    )
                                ) {
                                    Text(text = "Check-In", color = Color.Blue)
                                }
                            } else {

                                Button(
                                    onClick = {
                                        vm.startTrip(
                                            context,
                                            trip.tripId,
                                            trip.tripCode,
                                            trip.operatorCompanyId
                                        )
                                        isStartDialogVisible.value = true
                                    },
                                    modifier = Modifier
                                        .width(120.dp)
                                        .padding(end = 8.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(0xFFEBF4FA),
                                    )
                                ) {
                                    Text(text = "Start", color = Color.Blue)
                                }
                            }


                        }

//                    if (isStartDialogVisible.value) {
//                        StartTripDialog(
//                            tripId = trip.tripId,
//                            operatorId = trip.operatorCompanyId,
//                            tripCode = trip.tripCode,
//                            setShowDialog = {
//                                isStartDialogVisible.value = it
//
//                                isCheckInEnabled = it
//                            }
//                        )
//                    }
                        if (isStartDialogVisible.value) {
                            StartTripDialog(
                                tripId = trip.tripId,
                                operatorId = trip.operatorCompanyId,
                                tripCode = trip.tripCode,
                                setShowDialog = {
                                    isStartDialogVisible.value = it
                                }
                            )
                        }

//                    if (isCheckInDialogVisible.value) {
//                        CallCheckInDialog(
//                            tripId = trip.tripId,
//                            operatorId = trip.operatorCompanyId,
//                            tripCode = trip.tripCode,
//                            context = context,
//
//
//                            setShowDialog = {
//                                isCheckInDialogVisible.value = it
//                            }
//                        )
//                    }
                        if (isCheckInDialogVisible.value) {
                            assignment?.loc?.let { it1 ->
                                CallCheckInDialog(context,
                                    tripId = trip.tripId,
                                    tripCode = trip.tripCode,
                                    operatorId = trip.operatorCompanyId,
                                    it1,
                                    setShowDialog = {
                                        Log.i("Dialog", "Dialog dismissed")
                                        isCheckInDialogVisible.value = it
                                    }
                                )

                            }
                        }
                        if (it.tripDetail.status != "TRIP_ENDED") {
                            assignment?.activeStatusDetail?.actions.let {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .fillMaxHeight()
                                        .padding(
                                            start = 25.dp,
                                            top = 30.dp,
                                            end = 12.dp,
                                            bottom = 30.dp
                                        ),
                                    contentAlignment = Alignment.BottomStart

                                )
                                {

                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceEvenly
                                    ) {
                                        if (isStartEnabled == true) {
                                            Button(colors = ButtonDefaults.buttonColors(
                                                Color.Red
                                            ),
                                                onClick = {
                                                            vm.startTrip(
                                                                context,
                                                                tripId=trip.tripId,
                                                                tripCode=trip.tripCode,
                                                                operatorId=trip.operatorCompanyId
                                                            )
                                                    isStartDialogVisible.value = true
                                                },
                                                content = {
                                                    Text(text = "Start")
                                                }
                                            )
                                        }

                                        if (isCancelEnabled == true) {
                                            Button(
                                                onClick = {
                                                    vm.cancelTrip(
                                                        context,
                                                        tripId = trip.tripId,
                                                        tripCode=trip.tripCode,
                                                        operatorId=trip.operatorCompanyId,
                                                        navController
                                                    )

                                                },
                                                content = {
                                                    Text(text = "Cancel")
                                                }
                                            )
                                        }
                                        if (isEndEnabled == true) {
                                            Button(
                                                onClick = {
                                                    vm.endTrip(
                                                        context,
                                                        tripId=trip.tripId,
                                                        tripCode=trip.tripCode,
                                                        operatorId=trip.operatorCompanyId,
                                                        navController
                                                    )

                                                },
                                                content = {
                                                    Text(text = "End")
                                                }
                                            )
                                        }
                                        if (isCheckInEnabled == true) {
                                            Button(
                                                onClick = {
                                                    isCheckInDialogVisible.value = true
                                                },
                                                content = {
                                                    Text(text = "Check-In")
                                                }
                                            )
                                        }
                                        if (isDepartEnabled == true) {
                                            Button(
                                                onClick = {
                                                    vm.departTrip(
                                                        context,
                                                        tripId=trip.tripId,
                                                        tripCode=trip.tripCode,
                                                        operatorId=trip.operatorCompanyId
                                                    )
                                                },
                                                content = {
                                                    Text(text = "Depart")
                                                }
                                            )

                                        }
                                    }

                                }

                            }
                        }

                    }
                }

                item {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        val navController = rememberNavController()
                        val activity = LocalContext.current as? ComponentActivity
                        if (activity != null) {
                            TabViewContent(
                                navController = navController,
                                operatorId = trip.operatorCompanyId,
                                tripId = trip.tripId,
                                tripCode = trip.tripCode,
                                activity = activity
                            )
                        } else {
                            Log.d("BottomSheet", "Activity is null")
                        }
                    }
                }
            }


        }
    }
}

@Composable
fun TabViewContent(
    navController: NavHostController,
    operatorId: Int,
    tripId: Int,
    tripCode: String,
    activity: ComponentActivity,
    pt: PastAssignmentDetailViewModel = hiltViewModel()
) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Schedule", "Trip History", "Documents")

    val pt: PastAssignmentDetailViewModel = hiltViewModel()
    val pastAssignment by pt.pastassignmentDetail.collectAsStateWithLifecycle()

    if (pastAssignment?.isDataLoaded != true) {
        pt.fetchAssignmentDetail(
            context = LocalContext.current,
            tripId = tripId,
            tripCode = tripCode,
            operatorId = operatorId
        )
    }

    Column {
        TabRow(
            selectedTabIndex = selectedTabIndex, indicator = { tabPositions ->
                SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                    color = Color(0XFFD9454E)
                )
            }, containerColor = Transparent
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(selected = selectedTabIndex == index,
                    selectedContentColor = Color(0XFFD9454E),
                    unselectedContentColor = Color.Black,
                    onClick = { selectedTabIndex = index },
                    text = { Text(title) })
            }
        }
        pastAssignment?.let { assignmentDetail ->
            when (selectedTabIndex) {
                0 ->
//                    TempScheduleContent()
                    ScheduleContent(
                        navController = navController,

                        operatorId = operatorId,
                        tripId = tripId,
                        tripCode = tripCode,
                        activity
                    )

                1 -> pastAssignment?.let {
                    History(
                        navController = navController,
                        it.tripDetail.tripCode,
                        it.tripDetail.operatorId,
                        activity
                    )
                }

                2 -> pastAssignment?.let {
                    pastAssignment?.documents.let { document ->
                        if (document != null) {
                            DocumentsDialog(operatorId, document)
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun ScheduleContent(
    navController: NavHostController,


    operatorId: Int,
    tripId: Int,
    tripCode: String,
    activity: ComponentActivity,
    vm: AssignmentDetailViewModel = hiltViewModel(),
    pt: PastAssignmentDetailViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val isConnected = runCatching {
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        activeNetworkInfo != null && activeNetworkInfo.isConnected
    }.getOrDefault(false)

    if (isConnected) {


        val assignment by vm.assignmentDetail.collectAsStateWithLifecycle()
        val pastAssignment by pt.pastassignmentDetail.collectAsStateWithLifecycle()

        if (assignment?.isDataLoaded != true) {
            vm.fetchAssignmentDetail(
                context = context,
                tripId = tripId,
                tripCode = tripCode,
                operatorId = operatorId
            )
        }

        val isCheckInDialogVisible = remember { mutableStateOf(false); }
        var isStartDialogVisible = remember { mutableStateOf(false); }
        var permit by remember {
            mutableStateOf(false)
        }

        val isDocumentSelected = remember { mutableStateOf(true); }
        val inputFormat = SimpleDateFormat("yyyy-dd-MM'T'HH:mm")
        val outputFormat = SimpleDateFormat("dd-MMM-yyyy HH:mm")

        val arrivalTime = SimpleDateFormat("yyyy-dd-MM'T'HH:mm:ss")
        val outputArrivaltime = SimpleDateFormat("HH:mm")

        val isStartEnabled = assignment?.activeStatusDetail?.actions?.contains("START")
        val isCheckInEnabled = assignment?.activeStatusDetail?.actions?.contains("CHECKIN")
        val isDepartEnabled = assignment?.activeStatusDetail?.actions?.contains("DEPART")
        val isCancelEnabled = assignment?.activeStatusDetail?.actions?.contains("CANCEL")
        val isEndEnabled = assignment?.activeStatusDetail?.actions?.contains("END")

        val coroutineScope = rememberCoroutineScope()
        val vw: SwipeRefresh = viewModel()
        val isLoading by vw.isLoading.collectAsStateWithLifecycle()
        val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isLoading)

        val locationManager =
            context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isLocationEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val locationEnabledState = rememberUpdatedState(isLocationEnabled)


        val loc = LocationService::class.java
        val service = isLocationServiceRunning(context, loc)

        Log.d(
            "This is the permit of dialog",
            "AssignmentDetailScreen: ${locationEnabledState.value}, ${assignment?.tripDetail?.status}, ${service}"
        )
        if (assignment?.tripDetail?.status != "TRIP_CREATED") {
            if (!service) {
                permit = true
            }
        } else {
            permit = false
        }

        Column(modifier = Modifier.height(500.dp)) {
            assignment?.let { it ->

                assignment?.loc?.let { it1 ->
                    it1.locations.forEach { location ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            LocationList(location)
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










