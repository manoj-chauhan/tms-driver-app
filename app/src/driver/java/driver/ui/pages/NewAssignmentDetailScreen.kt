package driver.ui.pages


import android.Manifest
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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.drishto.driver.LocationService
import com.drishto.driver.R
import com.drishto.driver.models.DriverPlans
import com.drishto.driver.ui.viewmodels.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.google.android.material.bottomsheet.BottomSheetDialog
import driver.ui.actionColors
import driver.ui.components.AssignedVehicle
import driver.ui.components.GeneratedCodeDialog
import driver.ui.generateButton
import driver.ui.headingColor
import driver.ui.placeColor
import driver.ui.subHeadingColor

import driver.ui.textColor
import driver.ui.viewmodels.AssignmentDetailViewModel
import driver.ui.viewmodels.HomeViewModel
import driver.ui.viewmodels.MatrixLogViewModel
import driver.ui.viewmodels.PastAssignmentDetailViewModel
import driver.ui.viewmodels.TripsAssigned
import driver.ui.viewmodels.VehicleAssignment
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat


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
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(modifier = Modifier.weight(1f)) {
            Text(
                text = "DRISHTO",
                fontSize = 28.sp,
                color = logo,
                fontWeight = FontWeight.W700,
            )
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = "Search",
                modifier = Modifier.size(32.dp)
            )
            Icon(
                imageVector = Icons.Outlined.Notifications,
                contentDescription = "Notification",
                modifier = Modifier.size(32.dp)
            )
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(32.dp)
                    .background(color = Color(0xFF4ACEEB), shape = CircleShape)
            ) {
                Text(
                    text = "SK",
                    color = Color.Black,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
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

    Column(modifier = Modifier.padding(horizontal = 10.dp)) {
        if (isConnected) {

            mv.loadMatrixLog(context = context)

            val inputFormat = SimpleDateFormat("yyyy-dd-MM'T'HH:mm")
            val outputFormat = SimpleDateFormat("HH:mm a")

            val currentAssignmentData by vm.currentAssignment.collectAsStateWithLifecycle()
            vm.fetchAssignmentDetail(context = context)

            val driverPlanData by vm.driverPlan.collectAsStateWithLifecycle()
            vm.driverPlanAssignment(context = context)




            Column(modifier = Modifier.fillMaxWidth()) {


                Topbar()

                Spacer(modifier = Modifier.height(8.dp))



                Spacer(modifier = Modifier.height(30.dp))
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
                    if (it.trips.size == 0) {
                        val location =
                            Intent(context, LocationService::class.java)
                        context.stopService(location)
                    } else {
                        if (isAnyTripStarted) {
                            val loc = LocationService::class.java
                            val service = isLocationServiceRunning(context, loc)
                            if(service) {

                                Column(modifier = Modifier.fillMaxWidth()) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.Center
                                    ) {


                                        Icon(
                                            imageVector = Icons.Outlined.LocationOn,
                                            tint = Color.Gray,
                                            contentDescription = "location",
                                            modifier = Modifier.size(48.dp)

                                        )
                                    }
                                    Spacer(modifier = Modifier.height(5.dp))
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


                                            Text(
                                                modifier = Modifier.fillMaxWidth(),
                                                text = "You are sharing your location",
                                                textAlign = TextAlign.Center,
                                                fontSize = 16.sp,

                                                fontWeight = FontWeight.W300,
                                            )
                                            Spacer(modifier = Modifier.height(5.dp))
                                            Text(
                                                modifier = Modifier.fillMaxWidth(),

                                                text="Last Location Shared at  ${formattedDate}",
                                                textAlign = TextAlign.Center,
                                                fontSize = 10.sp,
                                                color = textColor,


                                                )
                                            Spacer(modifier = Modifier.height(5.dp))


                                        }
                                        else {
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

                            }else{
                                permit = true
                            }
                            if(service && !locationEnabledState.value)  {
                                permit = true
                            }
                        }
                        Column {
                            if (it.trips != null) {
                                if (it.trips.size > 0) {

                                    it.vehicles.let { vList ->


                                        Column(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .background(Color(0xFFF7F7F7))
                                                .padding(6.dp)

                                        ) {
                                            currentAssignmentData?.let {
                                                RequestPermission(permission = Manifest.permission.ACCESS_FINE_LOCATION)
                                                vList.take(vList.size)
                                                    .forEach { vehicleAssignment ->
                                                        AssignedVehicle(
                                                            vehicleAssignment
                                                        )
                                                    }
                                            }
                                        }
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(30.dp))


                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Button(
                                    onClick = {
                                        vm.generateAssignmentCode(
                                            context
                                        )
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
                                    GeneratedCodeDialog(
                                        currentAssignmentData?.assignmentCode
                                            ?: "",
                                        setShowDialog = {
                                            vm.hideAssignmentCode(context)
                                        }
                                    )
                                }


                            }


                        }
                    }

                }
                BottomSheet(

                )
            }



        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet() {

    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(true) }

    val boxgray = Color(0xFFE5E5E5)

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                showBottomSheet = false
            },
            containerColor = Color.White,
            tonalElevation = BottomSheetDefaults.Elevation,
            sheetState = sheetState,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.White)
                    .padding(horizontal = 14.dp)
                    .padding(bottom = 14.dp)
            ) {
                Text(
                    text = "Trip #456456",
                    fontSize = 16.sp,

                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "ETE-GGN-PNQ-RST-ADE-AED",
                    fontSize = 12.sp,
                    color = actionColors,
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center

                )

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
                        text = "Samrish Technologies Pvt Ltd",
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
                        text = "Samrish Technologies Pvt Ltd",
                        color = actionColors,
                        fontSize = 12.sp,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Running late due to bad weather",
                    color = logo,
                    modifier = Modifier

                        .fillMaxWidth(),

                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp)
                            .background(boxgray, shape = RoundedCornerShape(8.dp))
                            .height(60.dp)
                    ) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(8.dp)
                        ) {
                            Text(
                                text = "Total Distance Covered",
                                fontSize = 12.sp,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(text = "400 kms", color = Color.Gray)
                        }
                    }
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp)
                            .background(boxgray, shape = RoundedCornerShape(8.dp))
                            .height(60.dp)
                    ) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(8.dp)
                        ) {
                            Text(
                                text = "Total Travel Time",
                                fontSize = 12.sp,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(text = "400 kms", color = Color.Gray)
                        }
                    }
                }


                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
//                    Text(text = "Next Location", fontWeight = FontWeight.SemiBold)
                    Text(text = "Next Location", color = headingColor, fontSize = 14.sp)
                    Text(text = "Sect 4 Gurgaon (GGN)", color = actionColors)
                }

                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Estimated Time", fontSize = 12.sp, color = actionColors)
                    Text(text = "3 hours 20 mins", fontSize = 12.sp, color = actionColors)
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Estimated Distance", fontSize = 12.sp, color = actionColors)
                    Text(text = "250 kms", fontSize = 12.sp, color = actionColors)
                }

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
//                    Text(text = "Departed from Delhi at 02:00 am", fontWeight = FontWeight.SemiBold)
                    Text(
                        text = "Departed from Delhi at 02:00 am",
                        color = headingColor,
                        fontSize = 14.sp
                    )

                }
                Spacer(modifier = Modifier.height(5.dp))



                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Standard Arrival Time at Next Location: 08:00pm",
                        fontSize = 12.sp,
                        color = actionColors
                    )
                    Text(text = "300 kms", fontSize = 12.sp, color = actionColors)
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Total Distance from last location",
                        fontSize = 12.sp,
                        color = actionColors
                    )
                    Text(text = "300 kms", fontSize = 12.sp, color = actionColors)
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = { },
                        modifier = Modifier
                            .width(120.dp)
                            .padding(end = 8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFBCC8F0),

                            )
                    ) {
                        Text(text = "Start", color = Color(0xFF101482))
                    }
                    Button(
                        onClick = { },
                        modifier = Modifier
                            .width(120.dp)
                            .padding(start = 8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFD3D3D3)

                        )
                    ) {
                        Text(text = "Cancel", color = Color(0xFF6E6D6D))
                    }
                }


                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "You are operating two trips swipe to see others",
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Gray,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(500.dp))
            }


        }
    }
}



//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun BottomSheet() {
//
//    val scaffoldState = rememberBottomSheetScaffoldState()
//    val scope = rememberCoroutineScope()
//
//    var showBottomSheet by remember { mutableStateOf(true) }
//    val boxgray = Color(0xFFE5E5E5)
//
//
//    LaunchedEffect(scaffoldState.bottomSheetState) {
//        scaffoldState.bottomSheetState.expand()
//    }
//
//
//    if (showBottomSheet) {
//        BottomSheetScaffold(
//            scaffoldState = scaffoldState,
//            sheetPeekHeight = 40.dp,
//            sheetContainerColor = Color.White,
//
//            containerColor = Color.White,
//            sheetShadowElevation = 10.dp,
//
//            sheetSwipeEnabled = true,
//
//            sheetContent = {
//                Column(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .background(color = Color.White)
//                        .padding(horizontal = 14.dp)
//                        .padding(bottom = 14.dp)
//                        .verticalScroll(rememberScrollState())
//                ) {
//                    Text(
//                        text = "Trip #456456",
//                        fontSize = 16.sp,
//
//                        modifier = Modifier.fillMaxWidth(),
//                        textAlign = TextAlign.Center
//                    )
//                    Spacer(modifier = Modifier.height(8.dp))
//                    Text(
//                        text = "ETE-GGN-PNQ-RST-ADE-AED",
//                        fontSize = 12.sp,
//                        color= actionColors,
//                        modifier = Modifier
//                            .fillMaxWidth(),
//                        textAlign = TextAlign.Center
//
//                    )
//
//                    Spacer(modifier = Modifier.height(16.dp))
//
//                    Row(
//                        modifier = Modifier.fillMaxWidth(),
//                        horizontalArrangement = Arrangement.SpaceBetween
//                    ) {
//                        Text(
//                            text = "Created by",
//                            modifier = Modifier.align(Alignment.CenterVertically),
//                            fontSize = 12.sp,
//                            fontWeight = FontWeight.SemiBold
//                        )
//                        Text(
//                            text = "Samrish Technologies Pvt Ltd",
//                            color = actionColors,
//                            fontSize = 12.sp,
//                            modifier = Modifier.align(Alignment.CenterVertically)
//                        )
//                    }
//                    Spacer(modifier = Modifier.height(8.dp))
//
//                    Row(
//                        modifier = Modifier.fillMaxWidth(),
//                        horizontalArrangement = Arrangement.SpaceBetween
//                    ) {
//                        Text(
//                            text = "Operated by",
//                            modifier = Modifier.align(Alignment.CenterVertically),
//                            fontSize = 12.sp,
//                            fontWeight = FontWeight.SemiBold
//
//                        )
//                        Text(
//                            text = "Samrish Technologies Pvt Ltd",
//                            color = actionColors,
//                            fontSize = 12.sp,
//                            modifier = Modifier.align(Alignment.CenterVertically)
//                        )
//                    }
//
//                    Spacer(modifier = Modifier.height(20.dp))
//
//                    Text(
//                        text = "Running late due to bad weather",
//                        color = logo,
//                        modifier = Modifier
//
//                            .fillMaxWidth(),
//
//                        textAlign = TextAlign.Center
//                    )
//
//                    Spacer(modifier = Modifier.height(20.dp))
//
//                    Row(
//                        modifier = Modifier
//                            .padding(top = 20.dp)
//                            .fillMaxWidth(),
//                        horizontalArrangement = Arrangement.SpaceBetween
//                    ) {
//                        Box(
//                            modifier = Modifier
//                                .weight(1f)
//                                .padding(end = 8.dp)
//                                .background(boxgray, shape = RoundedCornerShape(8.dp))
//                                .height(60.dp)
//                        ) {
//                            Column(
//                                verticalArrangement = Arrangement.Center,
//                                horizontalAlignment = Alignment.CenterHorizontally,
//                                modifier = Modifier
//                                    .fillMaxSize()
//                                    .padding(8.dp)
//                            ) {
//                                Text(
//                                    text = "Total Distance Covered",
//                                    fontSize = 12.sp,
//                                    textAlign = TextAlign.Center
//                                )
//                                Spacer(modifier = Modifier.height(2.dp))
//                                Text(text = "400 kms", color = Color.Gray)
//                            }
//                        }
//                        Box(
//                            modifier = Modifier
//                                .weight(1f)
//                                .padding(start = 8.dp)
//                                .background(boxgray, shape = RoundedCornerShape(8.dp))
//                                .height(60.dp)
//                        ) {
//                            Column(
//                                verticalArrangement = Arrangement.Center,
//                                horizontalAlignment = Alignment.CenterHorizontally,
//                                modifier = Modifier
//                                    .fillMaxSize()
//                                    .padding(8.dp)
//                            ) {
//                                Text(
//                                    text = "Total Travel Time",
//                                    fontSize = 12.sp,
//                                    textAlign = TextAlign.Center
//                                )
//                                Spacer(modifier = Modifier.height(2.dp))
//                                Text(text = "400 kms", color = Color.Gray)
//                            }
//                        }
//                    }
//
//
//                    Spacer(modifier = Modifier.height(20.dp))
//
//                    Row(
//                        modifier = Modifier.fillMaxWidth(),
//                        horizontalArrangement = Arrangement.SpaceBetween
//                    ) {
////                    Text(text = "Next Location", fontWeight = FontWeight.SemiBold)
//                        Text(text = "Next Location", color = headingColor, fontSize = 14.sp)
//                        Text(text = "Sect 4 Gurgaon (GGN)", color = actionColors)
//                    }
//
//                    Spacer(modifier = Modifier.height(20.dp))
//                    Row(
//                        modifier = Modifier.fillMaxWidth(),
//                        horizontalArrangement = Arrangement.SpaceBetween
//                    ) {
//                        Text(text = "Estimated Time", fontSize = 12.sp , color = actionColors)
//                        Text(text = "3 hours 20 mins", fontSize = 12.sp , color = actionColors)
//                    }
//                    Row(
//                        modifier = Modifier.fillMaxWidth(),
//                        horizontalArrangement = Arrangement.SpaceBetween
//                    ) {
//                        Text(text = "Estimated Distance", fontSize = 12.sp , color = actionColors)
//                        Text(text = "250 kms", fontSize = 12.sp , color = actionColors)
//                    }
//
//                    Spacer(modifier = Modifier.height(20.dp))
//
//                    Row(
//                        modifier = Modifier.fillMaxWidth(),
//                        horizontalArrangement = Arrangement.SpaceBetween
//                    ) {
////                    Text(text = "Departed from Delhi at 02:00 am", fontWeight = FontWeight.SemiBold)
//                        Text(text = "Departed from Delhi at 02:00 am", color = headingColor, fontSize = 14.sp)
//
//                    }
//                    Spacer(modifier = Modifier.height(5.dp))
//
//
//
//                    Row(
//                        modifier = Modifier.fillMaxWidth(),
//                        horizontalArrangement = Arrangement.SpaceBetween
//                    ) {
//                        Text(text = "Standard Arrival Time at Next Location: 08:00pm", fontSize = 12.sp, color = actionColors)
//                        Text(text = "300 kms", fontSize = 12.sp , color = actionColors)
//                    }
//
//                    Row(
//                        modifier = Modifier.fillMaxWidth(),
//                        horizontalArrangement = Arrangement.SpaceBetween
//                    ) {
//                        Text(text = "Total Distance from last location", fontSize = 12.sp , color = actionColors)
//                        Text(text = "300 kms", fontSize = 12.sp , color = actionColors)
//                    }
//
//                    Spacer(modifier = Modifier.height(16.dp))
//
//                    Row(
//                        modifier = Modifier.fillMaxWidth(),
//                        horizontalArrangement = Arrangement.SpaceEvenly
//                    ) {
//                        Button(
//                            onClick = {  },
//                            modifier = Modifier
//                                .width(120.dp)
//                                .padding(end = 8.dp),
//                            colors = buttonColors(
//                                containerColor = Color(0xFFBCC8F0),
//
//                                )
//                        ) {
//                            Text(text = "Start", color = Color(0xFF101482))
//                        }
//                        Button(
//                            onClick = {  },
//                            modifier = Modifier
//                                .width(120.dp)
//                                .padding(start = 8.dp),
//                            colors = buttonColors(
//                                containerColor = Color(0xFFD3D3D3)
//
//                            )
//                        ) {
//                            Text(text = "Cancel" , color = Color(0xFF6E6D6D))
//                        }
//                    }
//
//
//                    Spacer(modifier = Modifier.height(16.dp))
//
//                    Text(
//                        text = "You are operating two trips swipe to see others",
//                        fontWeight = FontWeight.SemiBold,
//                        color = Color.Gray,
//                        modifier = Modifier.fillMaxWidth(),
//                        textAlign = TextAlign.Center
//                    )
//
//                    Spacer(modifier = Modifier.height(500.dp))
//                }
//
//
//
//            }){
//
//
//
//        }
//    }
//}



