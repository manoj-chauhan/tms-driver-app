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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.samrish.driver.services.cancel
import com.samrish.driver.services.depart
import com.samrish.driver.services.end
import com.samrish.driver.services.start
import com.samrish.driver.viewmodels.TripNextDestination

@Composable
fun ActiveStatusTrips(context: Context,  tripId:Int, operatorId: Int, tripCode: String, vm: TripNextDestination = viewModel()){

    val assignment by vm.tripNextDestinationActions.collectAsStateWithLifecycle()
    vm.getTripActions(context = context, tripId = tripId, operatorId = operatorId)

    val isCheckInDialogVisible = remember { mutableStateOf(false); }

    val isStartEnabled = remember { mutableStateOf(false); }
    val isCheckInEnabled = remember { mutableStateOf(false); }
    val isDepartEnabled = remember { mutableStateOf(false); }
    val isCancelEnabled = remember { mutableStateOf(false); }
    val isEndEnabled = remember { mutableStateOf(false); }

    if (assignment?.actions != null) {
        println("actions are" + { assignment!!.actions })
        isStartEnabled.value = assignment!!.actions.contains("START")
        isCheckInEnabled.value = assignment!!.actions.contains("CHECKIN")
        isDepartEnabled.value = assignment!!.actions.contains("DEPART")
        isCancelEnabled.value = assignment!!.actions.contains("CANCEL")
        isEndEnabled.value = assignment!!.actions.contains("END")
    }
    //    }

//    getTripActions(
//         context = context,
//         tripId = tripId,
//         operatorId = operatorId,
//         onTripActionsFetched = {
//            Log.d("TAG", "ActiveStatusTrips: $it")
//             tripActions = it
//            isStartEnabled.value = it.actions.contains("START")
//            isCheckInEnabled.value = it.actions.contains("CHECKIN")
//            isDepartEnabled.value = it.actions.contains("DEPART")
//            isCancelEnabled.value = it.actions.contains("CANCEL")
//            isEndEnabled.value = it.actions.contains("END")
//         }
//    )

//        getTripSchedule(
//             context = context,
//             tripCode = tripCode,
//             operatorId = operatorId,
//             onTripScheduleFetched = {
//                 tripSchedule = it
//             }
//    )



    assignment?.let{

        NextDestinationInfo(
            it.nextLocationName,
            it.estimatedDistance,
            it.estimatedTime,
            it.travelledDistance,
            it.travelTime
        )
    }
    assignment?.actions?.let{
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

    }



    if (isCheckInDialogVisible.value) {
//            CheckInDialog(
//                tripCode = tripCode,
//                operatorId = operatorId,
//                schedules = tripSchedule!!.locations,
//                setShowDialog = {
//                    Log.i("Dialog", "Dialog dismissed")
//                    isCheckInDialogVisible.value = it
//                }
//            )
        CallCheckInDialog(tripCode, operatorId, context,
            setShowDialog = {
                    Log.i("Dialog", "Dialog dismissed")
                    isCheckInDialogVisible.value = it
                }
        )
//        isCheckInDialogVisible.value = false
        }
}