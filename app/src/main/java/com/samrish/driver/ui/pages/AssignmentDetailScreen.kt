package com.samrish.driver.ui.pages

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.samrish.driver.R
import com.samrish.driver.ui.components.CallCheckInDialog
import com.samrish.driver.ui.components.DocumentsDialog
import com.samrish.driver.ui.components.LocationList
import com.samrish.driver.ui.components.ScheduleDialog
import com.samrish.driver.ui.viewmodels.AssignmentDetailViewModel
import java.text.SimpleDateFormat

@Composable
fun AssignmentDetailScreen(
    navController: NavHostController,
    selectedAssignment: String,
    operatorId: Int,
    tripId: Int,
    tripCode: String,
    vm: AssignmentDetailViewModel = viewModel(),
) {
    val context = LocalContext.current
    val painter = painterResource(id = R.drawable.signal)

    val assignment by vm.assignmentDetail.collectAsStateWithLifecycle()
    if (assignment?.isDataLoaded != true) {
        vm.fetchAssignmentDetail(
            context = context,
            tripId = tripId,
            tripCode = tripCode,
            operatorId = operatorId
        )
    }
    val isCheckInDialogVisible = remember { mutableStateOf(false); }
    val isScheduleSelected = remember { mutableStateOf(false); }
    val isDocumentSelected = remember { mutableStateOf(false); }

    val inputFormat = SimpleDateFormat("yyyy-dd-MM'T'HH:mm")
    val outputFormat = SimpleDateFormat("dd-MMM-yyyy HH:mm")


    val isStartEnabled = assignment?.activeStatusDetail?.actions?.contains("START")
    val isCheckInEnabled = assignment?.activeStatusDetail?.actions?.contains("CHECKIN")
    val isDepartEnabled = assignment?.activeStatusDetail?.actions?.contains("DEPART")
    val isCancelEnabled = assignment?.activeStatusDetail?.actions?.contains("CANCEL")
    val isEndEnabled = assignment?.activeStatusDetail?.actions?.contains("END")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Yellow)
    ) {
        Column {
            assignment?.let {
                val parsedDate =
                    remember(it.tripDetail.tripDateTime) { inputFormat.parse(it.tripDetail.tripDateTime) }
                val formattedDate = remember(parsedDate) { outputFormat.format(parsedDate) }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .padding(
                            PaddingValues(
                                start = 25.dp,
                                top = 30.dp,
                                end = 12.dp,
                                bottom = 20.dp
                            )
                        )
                ) {

                    Text(
                        text = it.tripDetail.operatorName, style = TextStyle(
                            color = Color.Black,
                            fontSize = 23.sp
                        )
                    )

                }

//            Box(modifier = Modifier.fillMaxSize().background(Color.Gray)){
                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState()) // Use verticalScroll instead of ScrollView
                        .fillMaxWidth()
                        .fillMaxHeight() // You can adjust these modifiers as needed
                ) {
                        Card(
                            modifier = Modifier
                                .fillMaxSize(),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White,
                            ),
                            shape = RoundedCornerShape(35.dp, 35.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(60.dp)
                                    .padding(start = 25.dp, top = 30.dp, end = 12.dp)
                            ) {
                                Text(
                                    text = "CURRENT ASSIGNMENT",
                                    style = TextStyle(
                                        color = Color.Gray,
                                        fontSize = 21.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                )

                            }
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(80.dp)
                                    .padding(start = 25.dp, top = 30.dp, end = 12.dp)
                            ) {
                                Column(modifier = Modifier.fillMaxWidth()) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.Bottom
                                    ) {
                                        Text(
                                            text = it.tripDetail.tripCode,
                                            style = TextStyle(
                                                color = Color.Black,
                                                fontSize = 20.sp,
                                                fontWeight = FontWeight.ExtraBold
                                            )
                                        )
                                        Text(
                                            text = formattedDate,
                                            style = TextStyle(
                                                color = Color.Black,
                                                fontSize = 15.sp,
                                                fontWeight = FontWeight.ExtraBold
                                            )
                                        )

                                    }
                                    Text(
                                        text = "(${it.tripDetail.tripName})",
                                        style = TextStyle(
                                            color = Color.Gray,
                                            fontSize = 17.sp,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    )
                                }
                            }

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(160.dp)
                                    .padding(start = 25.dp, top = 20.dp, end = 12.dp),
                                contentAlignment = Alignment.Center
                            )
                            {
                                Column(modifier = Modifier.fillMaxWidth()) {

                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Column(modifier = Modifier.fillMaxWidth()) {
                                            Text(text = "Schedules")

                                            assignment?.loc?.let { it1 ->
                                                LazyColumn(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                ) {
                                                    items(
                                                        it1.locations.size
                                                    ) {
                                                        LocationList(it, it1.locations[it])
                                                    }
                                                }
                                            }

                                        }
                                    }

                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(100.dp), contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "${it.tripDetail.status}",
                                            style = TextStyle(
                                                color = Color.Red,
                                                fontSize = 18.sp,
                                                fontWeight = FontWeight.Medium
                                            )
                                        )
                                    }
                                    Box(
                                        modifier = Modifier.fillMaxWidth(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "Departed from AHL at 12:30 hr ",
                                            style = TextStyle(
                                                color = Color.Gray,
                                                fontSize = 18.sp,
                                                fontWeight = FontWeight.Medium
                                            )
                                        )
                                    }

                                }

                            }

                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(80.dp)
                            )
                            {
                                val context = LocalContext.current
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(Color.White),
                                    horizontalArrangement = Arrangement.SpaceEvenly,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Button(
                                        colors = ButtonDefaults.buttonColors(
                                            Color.LightGray
                                        ),
                                        onClick = {
                                            isScheduleSelected.value = true
                                        }
                                    ) {
                                        Text(
                                            text = "Schedule",
                                            style = TextStyle(color = Color.Black)
                                        )

                                    }
                                    Button(
                                        colors = ButtonDefaults.buttonColors(
                                            Color.LightGray
                                        ),
                                        onClick = {
                                            isDocumentSelected.value = true
                                        }) {
                                        Text(
                                            text = "Documents",
                                            style = TextStyle(color = Color.Black)
                                        )

                                    }

                                }

                            }

                            Box(
                                modifier = Modifier.height(50.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(Color.White),
                                    horizontalArrangement = Arrangement.SpaceEvenly,
                                    verticalAlignment = Alignment.Bottom
                                ) {
                                    if (assignment?.activeStatusDetail?.travelledDistance != null) {
                                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                            Text(
                                                text = "Total Distance Covered",
                                                style = TextStyle(
                                                    color = Color.Black,
                                                    fontSize = 16.sp,
                                                    fontWeight = FontWeight.Medium
                                                )
                                            )
                                            Box(contentAlignment = Alignment.Center) {
                                                Text(
                                                    text = "${assignment?.activeStatusDetail?.travelledDistance}" + "km",
                                                    style = TextStyle(
                                                        color = Color.Black,
                                                        fontSize = 16.sp,
                                                        fontWeight = FontWeight.Medium
                                                    )
                                                )
                                            }
                                        }
                                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                            Text(
                                                text = "Total Travelled Time",
                                                style = TextStyle(
                                                    color = Color.Black,
                                                    fontSize = 16.sp,
                                                    fontWeight = FontWeight.Medium
                                                )
                                            )
                                            Text(
                                                text = "${
                                                    assignment?.activeStatusDetail?.travelTime?.div(
                                                        60
                                                    )
                                                }" + " hr " + "${assignment?.activeStatusDetail?.travelTime}" + " min",
                                                style = TextStyle(
                                                    color = Color.Black,
                                                    fontSize = 16.sp,
                                                    fontWeight = FontWeight.Medium
                                                )
                                            )

                                        }
                                    }
                                }

                            }

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 25.dp, top = 30.dp, end = 12.dp)
                                    .height(100.dp), contentAlignment = Alignment.BottomStart
                            ) {

                                Column {
                                    if (assignment?.activeStatusDetail!!.nextLocationName != null) {
                                        Text(
                                            text = "Next Destination",
                                            style = TextStyle(
                                                color = Color.Gray,
                                                fontSize = 20.sp,
                                                fontWeight = FontWeight.Bold
                                            )
                                        )
                                        Text(
                                            text = "${assignment?.activeStatusDetail?.nextLocationName}",
                                            style = TextStyle(
                                                color = Color.Black,
                                                fontSize = 16.sp,
                                                fontWeight = FontWeight.Bold
                                            )
                                        )

                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.Bottom
                                        ) {
                                            Text(
                                                text = "STA 09:00 hours (hc)",
                                                style = TextStyle(
                                                    color = Color.Gray,
                                                    fontSize = 13.sp,
                                                    fontWeight = FontWeight.Medium
                                                )
                                            )

                                        }
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.Bottom
                                        ) {
                                            Text(
                                                text = "Distance ${assignment?.activeStatusDetail?.estimatedDistance}km",
                                                style = TextStyle(
                                                    color = Color.Gray,
                                                    fontSize = 13.sp,
                                                    fontWeight = FontWeight.Medium
                                                )
                                            )
                                            Text(
                                                text = "Estimated Time ${
                                                    assignment?.activeStatusDetail?.estimatedTime?.div(
                                                        60
                                                    )
                                                }hr " + "${assignment?.activeStatusDetail?.estimatedTime}" + "min",
                                                style = TextStyle(
                                                    color = Color.Gray,
                                                    fontSize = 13.sp,
                                                    fontWeight = FontWeight.Medium
                                                )
                                            )
                                        }
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.Bottom
                                        ) {
                                            Text(
                                                text = "Distance Covered ${assignment?.activeStatusDetail?.travelledDistance}km",
                                                style = TextStyle(
                                                    color = Color.Black,
                                                    fontSize = 14.sp,
                                                    fontWeight = FontWeight.Medium
                                                )
                                            )
                                            Text(
                                                text = "Travelled Time ${
                                                    assignment?.activeStatusDetail?.travelTime?.div(
                                                        60
                                                    )
                                                }hr " + "${assignment?.activeStatusDetail?.travelTime}" + "min",
                                                style = TextStyle(
                                                    color = Color.Black,
                                                    fontSize = 14.sp,
                                                    fontWeight = FontWeight.Medium
                                                )
                                            )

                                        }
                                    }

                                    if (assignment?.activeStatusDetail?.currentLocationName != null) {
                                        Text(
                                            text = "Current Location ${assignment?.activeStatusDetail?.currentLocationName}",
                                            style = TextStyle(
                                                color = Color.Gray,
                                                fontSize = 20.sp,
                                                fontWeight = FontWeight.Bold
                                            )
                                        )
                                    }
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
                                                            tripId,
                                                            tripCode,
                                                            operatorId
                                                        )
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
                                                            tripId,
                                                            tripCode,
                                                            operatorId
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
                                                            tripId,
                                                            tripCode,
                                                            operatorId
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
                                                            tripId,
                                                            tripCode,
                                                            operatorId
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

                            if (isCheckInDialogVisible.value) {
                                assignment?.loc?.let { it1 ->
                                    CallCheckInDialog(context, tripId, tripCode, operatorId, it1,
                                        setShowDialog = {
                                            Log.i("Dialog", "Dialog dismissed")
                                            isCheckInDialogVisible.value = it
                                        }
                                    )

                                }
                            }

                            if (isDocumentSelected.value) {
                                assignment?.documents.let { document ->
                                    if (document != null) {
                                        DocumentsDialog(operatorId, document, setShowDialog = {
                                            isDocumentSelected.value = it
                                        })
                                    }
                                }
                            }
                            if (isScheduleSelected.value) {
                                assignment?.loc?.let { it1 ->
                                    ScheduleDialog(
                                        it1,
                                        setShowDialog = { isScheduleSelected.value = it })
                                }
                            }
                        }
                    }
            }

        }
    }


}

