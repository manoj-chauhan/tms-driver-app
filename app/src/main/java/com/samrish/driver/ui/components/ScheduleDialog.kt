package com.samrish.driver.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.samrish.driver.viewmodels.Schedule
import com.samrish.driver.viewmodels.ScheduleLocation

@Composable
fun ScheduleDialog(
    location: Schedule,
    setShowDialog: (Boolean) -> Unit
) {
    Dialog(onDismissRequest = { setShowDialog(false) }) {

        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
//                    .height(300.dp)
                    .background(Color.White)
            ) {
                Box(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                    Text(text = "Locations", style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold))
                }
                LazyColumn(modifier = Modifier.fillMaxWidth().padding(top = 32.dp)) {
                    items(
                        location.locations.size
                    ) {
                        LocationList(it, location.locations[it])
                    }
                }
            }
        }

    }
}


    @Composable
    fun LocationList(i: Int, scheduleLocation: ScheduleLocation) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(modifier = Modifier.width(23.dp)) {
                        Text(text = "${i + 1}")
                    }
                    Box(modifier = Modifier.width(30.dp)) {
                        Text(
                            text = "TMC", style = TextStyle(
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 8.dp)
                    ) {

                        Text(
                            text = "${scheduleLocation.placeName}", style = TextStyle(
                                color = Color.Gray,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }

                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                ) {
                    Column {


                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 9.dp)
                        ) {
                            Text(text = "|")
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 3.dp)
                        ) {
                            Text(text = "${scheduleLocation.actualDistance}")
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 9.dp)
                        ) {
                            Text(text = "|")
                        }
                    }

                }

            }

    }
