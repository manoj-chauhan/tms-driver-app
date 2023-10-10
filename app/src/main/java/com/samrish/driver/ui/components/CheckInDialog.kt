package com.samrish.driver.ui.components

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.samrish.driver.models.Locations
import com.samrish.driver.models.Trip
import com.samrish.driver.services.checkIn
import com.samrish.driver.viewmodels.TripCheckedInViewModel


// Reference: https://medium.com/@manojbhadane/android-custom-dialog-using-jetpack-compose-954d83e55af7



//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun CheckInDialog(
//    tripCode: String,
//    operatorId: Int,
//    schedules: List<Locations?>,
//    setShowDialog: (Boolean) -> Unit
//) {
//
//    Log.d("o", "CheckInDialog: ${schedules[0]?.placeCode}")
//
//    Dialog(onDismissRequest = { setShowDialog(false) }) {
//        Surface(
//            shape = RoundedCornerShape(16.dp),
//            color = Color.White
//        ) {
//            Box(
//                modifier = Modifier.padding(16.dp),
//                contentAlignment = Alignment.Center
//            ) {
//                Column() {
//                    val (selectedLocation, onLocationSelected) = remember { mutableStateOf(schedules[0]?.placeCode) }
//
//                    schedules.forEach { sch ->
//
//                        Row(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(horizontal = 16.dp)
//                                .selectable(
//                                    // this method is called when
////                                // radio button is selected.
//                                    selected = (sch?.placeCode == selectedLocation),
////                                // below method is called on
////                                // clicking of radio button.
//                                    onClick = { onLocationSelected(sch?.placeCode) }
//                                )
//                        ) {
//
//                            RadioButton(
//                                selected = (sch?.placeCode == selectedLocation) ,modifier = Modifier.padding(all = Dp(value = 8F)),
//                                onClick = {
////                                    onLocationSelected(sch.placeCode)
//                                    onLocationSelected(sch?.placeCode)
//
//                                }
//                            )
//                            Text(
//                                text = sch?.placeName + "(" + sch?.placeCode + ")",
//                                modifier = Modifier.padding(start = 16.dp)
//                            )
//                        }
//                    }
//
//                    val context = LocalContext.current
//
//                    Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
//                        Button(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .height(50.dp),
//                            onClick = {
//                                    if (selectedLocation != null) {
//                                        checkIn(context = context,
//                                            tripCode = tripCode,
//                                            operatorId = operatorId,
//                                            placeCode = selectedLocation)
//                                    }
//                                      setShowDialog(false)
//                            },
//                            shape = RoundedCornerShape(50.dp)
//                        ) {
//                            Text(text = "Done")
//                        }
//                    }
//                }
//            }
//        }
//    }
//}
//
