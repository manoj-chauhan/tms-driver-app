package com.samrish.driver.ui.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.samrish.driver.ui.components.AssignedTrip
import com.samrish.driver.ui.components.AssignedVehicle
import com.samrish.driver.viewmodels.HomeViewModel
import com.samrish.driver.viewmodels.TripsAssigned

@Composable
fun HomeScreen(
    navController: NavHostController,
    vm: HomeViewModel = viewModel(),
    onTripSelected: (assignment: TripsAssigned) -> Unit
) {
    val context = LocalContext.current

    val currentAssignmentData by vm.currentAssignment.collectAsStateWithLifecycle()
    vm.fetchAssignmentDetail(context = context)

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(13.dp, top = 36.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = " ",
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            )
        }
        currentAssignmentData?.let {
            AssignedVehicle(it.vehicle)

            if (it.trips.size == 0 ){
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .padding(13.dp,)
                    .align(Alignment.CenterHorizontally)) {
                    Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
                        Text(text ="No trips assigned!!",style = TextStyle(
                            color = Color.Black,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        ))
                    }

                }
            }
            it.trips.forEach { trip -> AssignedTrip(trip, onClick=onTripSelected) }

            if (it.userLocationVisible) {
                MatrixLog()
            }
        }

    }
}

