package com.samrish.driver.ui.components

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.samrish.driver.models.Trip


@Composable
fun AssignmentList(tripList: List<Trip>, onAssignmentClick: (trip: Trip) -> Unit) {
    Log.i("Assignments", "Just before display  $tripList")
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        tripList.forEach { trip -> Assignment(trip, onAssignmentClick) }
    }
}

@Preview
@Composable
fun AssignmentListPreview() {
    AssignmentList(tripList = listOf(
        Trip("BH4-BH5-BH6", "34456456", "STARTED", null, null),
        Trip("BH4-BH5-BH6", "34456457", "NOT STARTED", null, null),
    ), onAssignmentClick = {})
}

