package com.samrish.driver.ui.pages

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import cancel
import checkIn
import com.samrish.driver.models.Trip
import depart
import end

import getTripDetail
import start

@Composable
fun AssignmentDetailScreen(
    assignmentCode: String
) {

    val tripDetail = remember {
        mutableStateOf<Trip?>(Trip("", "", "", listOf(), null, null))
    }
    val isStartEnabled = remember { mutableStateOf(false); }
    val isCheckInEnabled = remember { mutableStateOf(false); }
    val isDepartEnabled = remember { mutableStateOf(false); }
    val isCancelEnabled = remember { mutableStateOf(false); }
    val isEndEnabled = remember { mutableStateOf(false); }


    getTripDetail(
        context = LocalContext.current,
        tripCode = assignmentCode,
        onTripDetailFetched = {
            tripDetail.value = it
            isStartEnabled.value = it.actions.contains("START")
            isCheckInEnabled.value = it.actions.contains("CHECKIN")
            isDepartEnabled.value = it.actions.contains("DEPART")
            isCancelEnabled.value = it.actions.contains("CANCEL")
            isEndEnabled.value = it.actions.contains("END")
        }
    );

    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize()
    ) {
        val context = LocalContext.current

        Card(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(modifier = Modifier.padding(16.dp), text = "${tripDetail.value?.code}")
                    Text(modifier = Modifier.padding(16.dp), text = "${tripDetail.value?.name}")
                    Text(modifier = Modifier.padding(16.dp), text = "${tripDetail.value?.status}")
                }

                val assignedDriver = (tripDetail.value as Trip).assignedDriver
                val assignedVehicle = (tripDetail.value as Trip).assignedVehicle

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        modifier = Modifier.padding(16.dp),
                        text = "${assignedDriver?.driverName}"
                    )
                    Text(
                        modifier = Modifier.padding(16.dp),
                        text = "${assignedVehicle?.vehicleNumber}(${assignedVehicle?.typeName})"
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            if (isStartEnabled.value) {
                Button(
                    onClick = {
                        tripDetail.value?.let {
                            start(context, it.code)
                        }
                    },
                    content = {
                        Text(text = "Start")
                    }
                )
            }
            if (isCancelEnabled.value) {
                Button(
                    onClick = {
                        tripDetail.value?.let {
                            cancel(context, it.code)
                        }
                    },
                    content = {
                        Text(text = "Cancel")
                    }
                )
            }
            if (isEndEnabled.value) {
                Button(
                    onClick = {
                        tripDetail.value?.let {
                            end(context, it.code)
                        }
                    },
                    content = {
                        Text(text = "End")
                    }
                )
            }
            if (isCheckInEnabled.value) {
                Button(
                    onClick = {
                        tripDetail.value?.let {
                            checkIn(context, it.code)
                        }
                    },
                    content = {
                        Text(text = "Check-In")
                    }
                )
            }
            if (isDepartEnabled.value) {
                Button(
                    onClick = {
                        tripDetail.value?.let {
                            depart(context, it.code)
                        }
                    },
                    content = {
                        Text(text = "Depart")
                    }
                )

            }
        }
    }
}
