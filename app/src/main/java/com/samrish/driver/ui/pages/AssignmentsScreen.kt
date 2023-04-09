package com.samrish.driver.ui.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.samrish.driver.models.Trip
import com.samrish.driver.ui.components.AssignmentList
import com.samrish.driver.services.getTrips

@Composable
fun AssignmentsScreen(
    navController: NavHostController,
    onAssignmentSelected: (assignment: Trip) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        val tripList = remember {
            mutableStateListOf<Trip>()
        }

        getTrips(LocalContext.current, onTripsFetched = {
            tripList.clear()
            tripList.addAll(it)
        })
        Button(
            onClick = {
                navController.navigate("profile")
            }
        ) {
         Text(text = "Profile")
        }
        AssignmentList(
            tripList = tripList,
            onAssignmentClick = {
                onAssignmentSelected(it)
            }
        )
        Text(text = "")
    }

}
