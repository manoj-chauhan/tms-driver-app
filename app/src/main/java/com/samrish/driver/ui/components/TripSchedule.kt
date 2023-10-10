package com.samrish.driver.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.samrish.driver.models.Schedule

@Composable
fun TripSchedule(
    schedules: List<Schedule>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        schedules.forEach { 
            Location(schedule = it)
        }
    }

}

@Composable
fun Location(
    schedule: Schedule
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
//        Text(text = schedule.placeName + "(" + schedule.placeCode + ")" )
//        Text(text = schedule.sta)
//        Text(text = schedule.std)
    }
}

@Preview
@Composable
fun TripSchedulePreview() {

}


