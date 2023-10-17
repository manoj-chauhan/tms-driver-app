package com.samrish.driver.ui.components

import android.content.Context
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.samrish.driver.viewmodels.ActionButtonViewModel
import com.samrish.driver.viewmodels.Schedule

@Composable
fun CallCheckInDialog(context: Context,tripCode:String,operatorId:Int, locations: Schedule, setShowDialog: (Boolean) -> Unit, viewModel: ActionButtonViewModel = viewModel()){

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
                            val (selectedLocation, onLocationSelected) = remember { mutableStateOf(locations.locations[0]?.placeCode) }

                            locations.locations.forEach { sch ->

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp)
                                        .selectable(
                                            selected = (sch?.placeCode == selectedLocation),
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

                            Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
                                Button(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(50.dp),
                                    onClick = {
                                        if (selectedLocation != null) {
                                            viewModel.checkInTrip(context = context,
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
