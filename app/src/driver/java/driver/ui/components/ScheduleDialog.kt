package driver.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.samrish.driver.models.ScheduleLocation
import java.text.SimpleDateFormat


@Composable
    fun LocationList( scheduleLocation: ScheduleLocation) {

        val inputFormat = SimpleDateFormat("dd-MM-yyyy HH:mm")
        val outputFormat = SimpleDateFormat("HH:mm")
    val scheduledArrivalTime =
        remember(scheduleLocation.scheduledArrivalTime) { inputFormat.parse(scheduleLocation.scheduledArrivalTime) }
    val formatSchedule = remember(scheduledArrivalTime) { outputFormat.format(scheduledArrivalTime) }

    val scheduledDepartureTime =
        remember(scheduleLocation.scheduledDepartureTime) { inputFormat.parse(scheduleLocation.scheduledDepartureTime) }
    val formatDeparture = remember(scheduledDepartureTime) { outputFormat.format(scheduledDepartureTime) }


    val actualArrivalTime = scheduleLocation?.actualArrivalTime?.let {
        inputFormat.parse(it)
    }

    val formatArrivalTime = actualArrivalTime?.let {
        outputFormat.format(it)
    } ?: "--"

    val actualDepartureTime = scheduleLocation?.actualDepartureTime?.let {
        inputFormat.parse(it)
    }

    val formatActualDeparture = actualDepartureTime?.let { outputFormat.format(it) } ?: "--"

            Spacer(modifier = Modifier.padding(8.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                        Text(
                            text = "${scheduleLocation.placeCode} - ", style = TextStyle(
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium
                            )
                        )

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
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(modifier = Modifier.width(50.dp)) {
                        Text(
                            text = "  ", style = TextStyle(
                                color = Color.Gray,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }
                    Box(modifier = Modifier.width(100.dp), Alignment.Center) {

                        Text(
                            text = "Arrival", style = TextStyle(
                                color = Color.Black,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }
                    Box(modifier = Modifier.width(100.dp), Alignment.Center) {

                        Text(
                            text = "Departure", style = TextStyle(
                                color = Color.Black,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }

                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(modifier = Modifier.width(50.dp)) {

                        Text(
                            text = "Planned", style = TextStyle(
                                color = Color.Gray,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }

                    Box(modifier = Modifier.width(100.dp), Alignment.Center) {
                        Text(
                            text = formatSchedule, style = TextStyle(
                                color = Color.Black,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }

                    Box(modifier = Modifier.width(100.dp), Alignment.Center) {
                        Text(
                            text = formatDeparture, style = TextStyle(
                                color = Color.Black,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }

                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(modifier = Modifier.width(50.dp)) {

                        Text(
                            text = "Actual", style = TextStyle(
                                color = Color.Gray,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }
                    Box(modifier = Modifier.width(100.dp), contentAlignment = Alignment.Center) {
                        Text(
                            text = formatArrivalTime ,
                            style = TextStyle(
                                color = Color.Black,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }
                    Box(modifier = Modifier.width(100.dp), contentAlignment = Alignment.Center) {
                        Text(
                            text = formatActualDeparture,
                            style = TextStyle(
                                color = Color.Black,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }
                }
            }
    }