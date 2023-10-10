package com.samrish.driver.ui.components

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.samrish.driver.services.checkIn
import com.samrish.driver.viewmodels.TripCheckedInViewModel

@Composable
fun CallCheckInDialog(tripCode: String, operatorId: Int,context:Context,setShowDialog: (Boolean) -> Unit, vm: TripCheckedInViewModel = viewModel()){


    val assignment by vm.tripCheckedIn.collectAsStateWithLifecycle()
    vm.getTripLocations(context= context, selectedCode= tripCode, operatorId= operatorId)



    Log.d("CheckIn", "CallCheckInDialog: ${assignment?.locations}")


        assignment?.let {
            Dialog(onDismissRequest = { setShowDialog(false) }) {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = Color.White
                ) {
                    Box(
                        modifier = Modifier.padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column() {
                            val (selectedLocation, onLocationSelected) = remember { mutableStateOf(it.locations[0]?.placeCode) }

                            it.locations.forEach { sch ->

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp)
                                        .selectable(
                                            // this method is called when
//                                // radio button is selected.
                                            selected = (sch?.placeCode == selectedLocation),
//                                // below method is called on
//                                // clicking of radio button.
                                            onClick = { onLocationSelected(sch?.placeCode) }
                                        )
                                ) {

                                    RadioButton(
                                        selected = (sch?.placeCode == selectedLocation) ,modifier = Modifier.padding(all = Dp(value = 8F)),
                                        onClick = {
//                                    onLocationSelected(sch.placeCode)
                                            onLocationSelected(sch?.placeCode)

                                        }
                                    )
                                    Text(
                                        text = sch?.placeName + "(" + sch?.placeCode + ")",
                                        modifier = Modifier.padding(start = 16.dp)
                                    )
                                }
                            }

                            val context = LocalContext.current

                            Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
                                Button(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(50.dp),
                                    onClick = {
                                        if (selectedLocation != null) {
                                            checkIn(context = context,
                                                tripCode = tripCode,
                                                operatorId = operatorId,
                                                placeCode = selectedLocation)
                                        }
                                        setShowDialog(false)
                                    },
                                    shape = RoundedCornerShape(50.dp)
                                ) {
                                    Text(text = "Done")
                                }
                            }
                        }
                    }
                }
            }

        }
    }