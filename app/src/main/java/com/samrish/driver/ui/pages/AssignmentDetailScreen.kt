package com.samrish.driver.ui.pages

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.samrish.driver.models.Schedule
import com.samrish.driver.models.Trip
import com.samrish.driver.services.*
import com.samrish.driver.ui.components.CheckInDialog
import com.samrish.driver.ui.components.TripSchedule

@Composable
fun AssignmentDetailScreen(
    assignmentCode: String
) {

//    val tripDetail = remember {
//        mutableStateOf<Trip?>(Trip("", "", "", null, null,"",""))
//    }
//    var tripSchedule = remember {
//        mutableStateListOf<Schedule>()
//    }
//    val isStartEnabled = remember { mutableStateOf(false); }
//    val isCheckInEnabled = remember { mutableStateOf(false); }
//    val isDepartEnabled = remember { mutableStateOf(false); }
//    val isCancelEnabled = remember { mutableStateOf(false); }
//    val isEndEnabled = remember { mutableStateOf(false); }
//
//    val isCheckInDialogVisible = remember { mutableStateOf(false); }

//    getTripDetail(
//        context = LocalContext.current,
//        tripCode = assignmentCode,
//        onTripDetailFetched = {
//            tripDetail.value = it
//            isStartEnabled.value = it.actions.contains("START")
//            isCheckInEnabled.value = it.actions.contains("CHECKIN")
//            isDepartEnabled.value = it.actions.contains("DEPART")
//            isCancelEnabled.value = it.actions.contains("CANCEL")
//            isEndEnabled.value = it.actions.contains("END")
//        }
//    );

//    getTripSchedule(
//     context = LocalContext.current,
//     tripCode = assignmentCode,
//     onTripScheduleFetched = {
//         tripSchedule.clear()
//         tripSchedule.addAll(it)
//     }
//    )

//    Column(
//        modifier = Modifier
//            .padding(8.dp)
//            .fillMaxSize()
//    ) {
//        val context = LocalContext.current
//
//        Card(
//            modifier = Modifier
//                .padding(8.dp)
//                .fillMaxWidth()
//        ) {
//            Column(
//                modifier = Modifier
//                    .padding(8.dp)
//                    .fillMaxWidth()
//            ) {
//                Row(
//                    modifier = Modifier.fillMaxWidth(),
//                    horizontalArrangement = Arrangement.SpaceBetween
//                ) {
//                    Text(modifier = Modifier.padding(16.dp), text = "${tripDetail.value?.code}")
//                    Text(modifier = Modifier.padding(16.dp), text = "${tripDetail.value?.name}")
//                    Text(modifier = Modifier.padding(16.dp), text = "${tripDetail.value?.status}")
//                }
//
//                val assignedDriver = (tripDetail.value as Trip).assignedDriver
//                val assignedVehicle = (tripDetail.value as Trip).assignedVehicle
//
//                Row(
//                    modifier = Modifier.fillMaxWidth(),
//                    horizontalArrangement = Arrangement.SpaceBetween
//                ) {
//                    Text(
//                        modifier = Modifier.padding(16.dp),
//                        text = "${assignedDriver?.driverName}"
//                    )
//                    Text(
//                        modifier = Modifier.padding(16.dp),
//                        text = "${assignedVehicle?.vehicleNumber}(${assignedVehicle?.typeName})"
//                    )
//                }
//                TripSchedule(schedules = tripSchedule)
//            }
//        }
//
//        Row(
//            modifier = Modifier
//                .padding(8.dp)
//                .fillMaxWidth()
//        ) {
//            if (isStartEnabled.value) {
//                Button(
//                    onClick = {
//                        tripDetail.value?.let {
//                            start(context, it.code)
//                        }
//                    },
//                    content = {
//                        Text(text = "Start")
//                    }
//                )
//            }
//            if (isCancelEnabled.value) {
//                Button(
//                    onClick = {
//                        tripDetail.value?.let {
//                            cancel(context, it.code)
//                        }
//                    },
//                    content = {
//                        Text(text = "Cancel")
//                    }
//                )
//            }
//            if (isEndEnabled.value) {
//                Button(
//                    onClick = {
//                        tripDetail.value?.let {
//                            end(context, it.code)
//                        }
//                    },
//                    content = {
//                        Text(text = "End")
//                    }
//                )
//            }
//            if (isCheckInEnabled.value) {
//                Button(
//                    onClick = {
//                        tripDetail.value?.let {
//                            isCheckInDialogVisible.value = true
////                            checkIn(context, it.code)
//                        }
//                    },
//                    content = {
//                        Text(text = "Check-In")
//                    }
//                )
//            }
//            if (isDepartEnabled.value) {
//                Button(
//                    onClick = {
//                        tripDetail.value?.let {
//                            depart(context, it.code)
//                        }
//                    },
//                    content = {
//                        Text(text = "Depart")
//                    }
//                )
//
//            }
//        }
//        if (isCheckInDialogVisible.value) {
//            CheckInDialog(
//                tripCode = tripDetail.value!!.code,
//                schedules = tripSchedule.distinctBy { it.placeCode
//                },
//                setShowDialog = {
//                    Log.i("Dialog", "Dialog dismissed")
//                    isCheckInDialogVisible.value = it
//                }
//            )
//        }
//    }
}
