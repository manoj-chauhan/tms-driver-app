package com.samrish.driver.ui.components

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.samrish.driver.models.Schedule
import com.samrish.driver.models.TripActions
import com.samrish.driver.services.cancel
import com.samrish.driver.services.depart
import com.samrish.driver.services.end
import com.samrish.driver.services.getTripActions
import com.samrish.driver.services.getTripSchedule
import com.samrish.driver.services.start

@Composable
fun ActiveStatusTrips(context: Context,  tripId:Int, operatorId: Int, tripCode: String){

        var tripActions by remember {
        mutableStateOf<TripActions?>(null)
    }

        var tripSchedule by remember {
        mutableStateOf<Schedule?>(null)
    }

    val context = LocalContext.current

    val isCheckInDialogVisible = remember { mutableStateOf(false); }


    val isStartEnabled = remember { mutableStateOf(false); }
    val isCheckInEnabled = remember { mutableStateOf(false); }
    val isDepartEnabled = remember { mutableStateOf(false); }
    val isCancelEnabled = remember { mutableStateOf(false); }
    val isEndEnabled = remember { mutableStateOf(false); }

    getTripActions(
         context = context,
         tripId = tripId,
         operatorId = operatorId,
         onTripActionsFetched = {
            Log.d("TAG", "ActiveStatusTrips: $it")
             tripActions = it
            isStartEnabled.value = it.actions.contains("START")
            isCheckInEnabled.value = it.actions.contains("CHECKIN")
            isDepartEnabled.value = it.actions.contains("DEPART")
            isCancelEnabled.value = it.actions.contains("CANCEL")
            isEndEnabled.value = it.actions.contains("END")
         }
    )

        getTripSchedule(
             context = context,
             tripCode = tripCode,
             operatorId = operatorId,
             onTripScheduleFetched = {
                 tripSchedule = it
             }
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(start = 25.dp, top = 30.dp, end = 12.dp, bottom = 30.dp),
        contentAlignment = Alignment.BottomStart

    )
    {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            if (isStartEnabled.value) {
                Button(colors = ButtonDefaults.buttonColors(
                Color.Red
                ),
                    onClick = {
                            start(context, tripCode, operatorId)
                    },
                    content = {
                        Text(text = "Start")
                    }
                )
            }

            if (isCancelEnabled.value) {
                Button(
                    onClick = {
//                        tripDetail.value?.let {
                            cancel(context, tripCode, operatorId)
//                        }
                    },
                    content = {
                        Text(text = "Cancel")
                    }
                )
            }
            if (isEndEnabled.value) {
                Button(
                    onClick = {
//                        tripDetail.value?.let {
                            end(context, tripCode, operatorId)
//                        }
                    },
                    content = {
                        Text(text = "End")
                    }
                )
            }
            if (isCheckInEnabled.value) {
                Button(
                    onClick = {
                            isCheckInDialogVisible.value = true
//                            checkIn(context, tripCode, operatorId)
                    },
                    content = {
                        Text(text = "Check-In")
                    }
                )
            }
            if (isDepartEnabled.value) {
                Button(
                    onClick = {
//                        tripDetail.value?.let {
                            depart(context, tripCode, operatorId)
//                        }
                    },
                    content = {
                        Text(text = "Depart")
                    }
                )

            }
        }

    }

    if (isCheckInDialogVisible.value) {
            CheckInDialog(
                tripCode = tripCode,
                operatorId = operatorId,
                schedules = tripSchedule!!.locations,
                setShowDialog = {
                    Log.i("Dialog", "Dialog dismissed")
                    isCheckInDialogVisible.value = it
                }
            )

//        CheckInDialog(
//            tripCode = tripCode,
//            operatorId = operatorId,
//            context = context,
////            schedules = tripSchedule!!.locations,
//            setShowDialog = {
//                Log.i("Dialog", "Dialog dismissed")
//                isCheckInDialogVisible.value = it
//            }
//        )
        }
//    }
}