package com.samrish.driver.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.samrish.driver.models.Trip

@Composable
fun Assignment(trip: Trip, onClick: (trip: Trip) -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable {
                onClick(trip)
            }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "3465464"
                )
                Text(
                    text = "25 Jun 23 03:00",
                    color = Color.Gray
                )
                Text(
                    text = "STARTED",
                    color = Color.Green
                )
            }
            Row() {
                Text(
                    text = trip.name
                )
            }

        }
    }
}

