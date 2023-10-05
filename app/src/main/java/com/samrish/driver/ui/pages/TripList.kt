package com.samrish.driver.ui.pages

import android.graphics.Paint.Style
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.samrish.driver.R
import com.samrish.driver.models.Trip
import com.samrish.driver.models.VehicleAssignment
import com.samrish.driver.services.getTrips
import com.samrish.driver.services.vehicleDetails
import com.samrish.driver.ui.components.GeneratedCodeDialog

class TripList : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TripListPrint()
        }
    }
}

@Composable
fun TripListPrint() {

    val context = LocalContext.current
    val vehicleAssignment = remember {
        mutableStateOf<VehicleAssignment>(VehicleAssignment(0,"",0,"",0,"","", 0,"","",""))
    }

    vehicleDetails(context, onVehicleDetailFetched = {
        vehicleAssignment.value = it
        Log.d("TAG", "TripListPrint: $it")
    })

    val isCheckInDialogVisible = remember { mutableStateOf(false); }

//
//    val tripList = remember {
//        mutableStateListOf<Trip>()
//    }
//
//    getTrips(LocalContext.current, onTripsFetched = {
//        tripList.clear()
//        tripList.addAll(it)
//    Log.d("TAG", "TripListPrint: $it")
//    })
    Column(modifier = Modifier.fillMaxSize()) {
            val context = LocalContext.current
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
        }

        VehicleAssignmentDetail(vehicleAssignment.value)

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(13.dp)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp), shape = RoundedCornerShape(10.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .height(80.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Box(
                            modifier = Modifier
                                .width(50.dp)
                        ) {
                            Text(
                                text = "3987",
                                style = TextStyle(
                                    color = Color.Black,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            )
                        }

                        Box(
                            modifier = Modifier
                        ) {
                            Text(
                                text = "12-sep-2023 10:00 AM",
                                style = TextStyle(
                                    color = Color.Black,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            )
                        }

                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        Box(
                            modifier = Modifier
                        ) {
                            Text(
                                text = "Hyundai Santro",
                                style = TextStyle(
                                    color = Color.Black,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            )
                        }

                        Box(
                            modifier = Modifier
                                .width(120.dp), contentAlignment = Alignment.BottomEnd
                        ) {
                            Text(
                                text = "DL4VB3211",
                                style = TextStyle(
                                    color = Color.Black,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            )
                        }

                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        Box(
                            modifier = Modifier
                        ) {
                            Text(
                                text = "Route -:",
                                style = TextStyle(
                                    color = Color.Black,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            )
                        }

                        Box(
                            modifier = Modifier
                        ) {
                            Text(
                                text = "RTS-DFR-DCV-SAA",
                                style = TextStyle(
                                    color = Color.Black,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            )
                        }
                    }

                }

            }
        }





    }




}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TripListPrint()
}