package com.samrish.driver.ui.composition

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.samrish.driver.models.Trip

@Composable
fun Assignments() {
    Box(
        modifier = Modifier
            .background(Color.Blue)
            .fillMaxSize()
    ) {
        AssignmentList(tripList = listOf(
            Trip("BH4-BH5-BH6","34456456", "STARTED"),
            Trip("BH4-BH5-BH6","34456457", "NOT STARTED"),
        ), onAssignmentClick = {})
    }
}

@Composable
fun AssignmentList(tripList: List<Trip>, onAssignmentClick: (trip: Trip) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        tripList.forEach { trip -> Assignment(trip, onAssignmentClick) }
    }
}


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


@Preview
@Composable
fun AssignmentListPreview() {
    AssignmentList(tripList = listOf(
        Trip("BH4-BH5-BH6","34456456", "STARTED"),
        Trip("BH4-BH5-BH6","34456457", "NOT STARTED"),
    ), onAssignmentClick = {})
}