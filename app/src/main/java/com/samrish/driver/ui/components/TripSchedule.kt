package com.samrish.driver.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TripSchedule() {
    Column(
        modifier = Modifier.fillMaxWidth().padding(8.dp)
    ) {
        Location()
        Location()
    }

}

@Composable
fun Location() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "BH4")
        Text(text = "STA")
        Text(text = "STD")
    }
}

@Preview
@Composable
fun TripSchedulePreview() {
    TripSchedule()
}


