package com.samrish.driver.ui.pages

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.samrish.driver.models.Trip
import com.samrish.driver.ui.components.Assignment
import getTrips

@Composable
fun Assignments(
    navController: NavHostController,
    onAssignmentSelected: (assignment: Trip) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        var tripList = remember {
            mutableStateListOf<Trip>()
        }

        getTrips(LocalContext.current, onTripsFetched = {
            tripList.clear()
            tripList.addAll(it)
        })
        AssignmentList(
            tripList = tripList,
            onAssignmentClick = {
                onAssignmentSelected(it)
            }
        )
        Text(text = "")
    }

}

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

