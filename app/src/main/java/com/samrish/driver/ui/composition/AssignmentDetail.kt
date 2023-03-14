package com.samrish.driver.ui.composition

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
fun AssignmentDetail(
    assignmentCode: String
) {

    var x = remember {
        mutableStateOf<Trip?>(Trip("", "", ""))
    }

    getTripDetail(
        context = LocalContext.current,
        tripCode = assignmentCode,
        onTripDetailFetched = {
            x.value = it
        }
    );
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        val context = LocalContext.current
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Card(
                modifier = Modifier.fillMaxWidth()
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
                        Text(modifier = Modifier.padding(16.dp), text = "${x.value?.code}")
                        Text(modifier = Modifier.padding(16.dp), text = "${x.value?.name}")
                        Text(modifier = Modifier.padding(16.dp), text = "${x.value?.status}")
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Button(
                            onClick = {
                                x.value?.let {
                                    start(context, it.code)
                                }
                            },
                            content = {
                                Text(text = "Start")
                            }
                        )
                        Button(
                            onClick = {
                                x.value?.let {
                                    cancel(context, it.code)
                                }
                            },
                            content = {
                                Text(text = "Cancel")
                            }
                        )
                        Button(
                            onClick = {
                                x.value?.let {
                                    end(context, it.code)
                                }
                            },
                            content = {
                                Text(text = "End")
                            }
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Button(
                            onClick = {
                                x.value?.let {
                                    checkIn(context, it.code)
                                }
                            },
                            content = {
                                Text(text = "Check-In")
                            }
                        )
                        Button(
                            onClick = {
                                x.value?.let {
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

    }
}