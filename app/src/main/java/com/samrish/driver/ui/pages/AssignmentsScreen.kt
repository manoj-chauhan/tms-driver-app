package com.samrish.driver.ui.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.samrish.driver.models.Trip
import com.samrish.driver.ui.components.AssignmentList
import getTrips

@Composable
fun AssignmentsScreen(
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
