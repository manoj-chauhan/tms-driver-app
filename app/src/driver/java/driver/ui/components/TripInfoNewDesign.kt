package driver.ui.components

import android.content.Context
import android.location.LocationManager
import android.net.ConnectivityManager
import android.util.Log
import androidx.activity.ComponentActivity
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.drishto.driver.LocationService
import com.drishto.driver.R
import com.drishto.driver.ui.viewmodels.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import driver.ui.actionColors
import driver.ui.headingColor
import driver.ui.pages.isLocationServiceRunning
import driver.ui.viewmodels.AssignmentDetailViewModel
import driver.ui.viewmodels.PastAssignmentDetailViewModel
import java.text.SimpleDateFormat

@Composable
fun TripInfoNewDesign(

    selectedAssignment: String,
    operatorId: Int,
    tripId: Int,
    tripCode: String,
    activity: ComponentActivity,
    vm: AssignmentDetailViewModel = hiltViewModel(),
    pt: PastAssignmentDetailViewModel = hiltViewModel()
) {

    Log.d("Detail", "AssignmentDetailScreen: ")
    val context = LocalContext.current
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val isConnected = runCatching {
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        activeNetworkInfo != null && activeNetworkInfo.isConnected
    }.getOrDefault(false)

    if (isConnected) {
        val painter = painterResource(id = R.drawable.signal)

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

        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
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

        assignment?.let { it ->
            if (it.tripDetail.status == "TRIP_IN_TRANSIT" && it.tripDetail.status != "TRIP_ENDED") {
                val parsedDate =
                    remember(assignment?.activeStatusDetail?.arrivalTime) {
                        arrivalTime.parse(
                            assignment?.activeStatusDetail?.arrivalTime
                        )
                    }
                val formattedDate =
                    remember(parsedDate) {
                        outputArrivaltime.format(
                            parsedDate
                        )
                    }

                Column(modifier = Modifier.fillMaxWidth()) {

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
                                .background(
                                    color = Color(0XFFDCE1FE), shape = RoundedCornerShape(8.dp)
                                )
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
                                    color = Color.Gray,
                                    textAlign = TextAlign.Center
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(text = "${assignment?.activeStatusDetail?.travelledDistance}" + "km", color = Color.Black)
                            }
                        }
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 8.dp)
                                .background(
                                    color = Color(0XFFDCE1FE), shape = RoundedCornerShape(8.dp)
                                )
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
                                    color = Color.Gray,
                                    fontSize = 12.sp,
                                    textAlign = TextAlign.Center
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(text = "${
                                    assignment?.activeStatusDetail?.travelTime?.div(
                                        60
                                    )
                                }" + " hr " + "${
                                    assignment?.activeStatusDetail?.travelTime?.rem(
                                        60
                                    )
                                }" + " min", color = Color.Black)
                            }
                        }
                    }


                    Spacer(modifier = Modifier.height(16.dp))
                    HorizontalDivider(thickness = 2.dp, color = Color.LightGray)
                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
//                    Text(text = "Next Location", fontWeight = FontWeight.SemiBold)
                        Text(text = "Next Location", color = headingColor, fontSize = 14.sp)
                        Text(text = "${assignment?.activeStatusDetail?.nextLocationName}", color = actionColors)
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Estimated Time", fontSize = 12.sp, color = actionColors
                        )
                        Text(
                            text = "${
                                assignment?.activeStatusDetail?.estimatedTime?.div(
                                    60
                                )
                            }hr " + "${
                                assignment?.activeStatusDetail?.estimatedTime?.rem(
                                    60
                                )
                            }" + "min", fontSize = 12.sp, color = actionColors
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Estimated Distance", fontSize = 12.sp, color = actionColors
                        )
                        Text(text = "${assignment?.activeStatusDetail?.estimatedDistance}km", fontSize = 12.sp, color = actionColors)
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
                            text = "Standard Arrival Time at Next Location: ${assignment?.activeStatusDetail?.arrivalTime}",
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
                    HorizontalDivider(thickness = 2.dp, color = Color.LightGray)
                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(
                            onClick = { }, modifier = Modifier
                                .width(120.dp)
                                .padding(end = 8.dp),

                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFEBF4FA),

                                )
                        ) {
                            Text(text = "Start", color = Color.Blue)
                        }
                        Button(
                            onClick = { },

                            modifier = Modifier
                                .width(120.dp)
                                .padding(start = 8.dp),

                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFE8EAED)


                            )
                        ) {
                            Text(text = "Cancel", color = Color.Gray)
                        }
                    }


                    Spacer(modifier = Modifier.height(16.dp))
                    HorizontalDivider(thickness = 2.dp, color = Color.LightGray)
                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "You are operating two trips swipe to see others",
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Gray,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                }
            }
        }
    }
}