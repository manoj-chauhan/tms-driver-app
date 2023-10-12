package com.samrish.driver.ui.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.samrish.driver.models.TripsAssigned
import com.samrish.driver.services.getAssignedTrips

@Composable
fun HomeScreen(navController: NavHostController,
               onTripSelected: (assignment: TripsAssigned) -> Unit) {
    val context = LocalContext.current

    var tripList = remember {
        mutableStateListOf<TripsAssigned>()
    }
    val userLocationVisible = remember { mutableStateOf(false); }



    getAssignedTrips(context, onTripsListFetched={
        tripList.clear()
        tripList.addAll(it)
    })
    Column(modifier = Modifier.fillMaxSize()) {
        Row (modifier = Modifier
            .fillMaxWidth()
            .padding(13.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically){

            Text(
                text = "Assigned Trip",
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            )

            Button(
                colors = ButtonDefaults.buttonColors(
                    Color.LightGray
                ),
                onClick = {
                    userLocationVisible.value = true
                }
            )
            {
                Text(text = "Location", style = TextStyle(color = Color.Black))

            }
        }
        VehicleAssignmentDetail()
        TripAssignmentDetails(tripList, onTripSelected)




    }

    if(userLocationVisible.value){
        MatrixLog()
    }

}

