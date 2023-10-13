package com.samrish.driver.ui.pages

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.samrish.driver.R
import com.samrish.driver.ui.components.ActiveStatusTrips
import com.samrish.driver.viewmodels.AssignmentDetailViewModel
import com.samrish.driver.viewmodels.TripDetailsViewModel

@Composable
fun AssignmentDetailScreen (
    navController: NavHostController,
    selectedAssignment: String,
    operatorId: Int,
    tripId: Int,
    tripCode: String,
    vm: AssignmentDetailViewModel = viewModel()
) {
    val context = LocalContext.current
    val painter = painterResource(id = R.drawable.signal)

    val assignment by vm.assignmentDetail.collectAsStateWithLifecycle()
    vm.fetchAssignmentDetail(context = context, tripId=tripId, tripCode = tripCode, operatorId = operatorId)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Yellow)
    ) {
        Column {
            assignment?.let {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .padding(
                            PaddingValues(
                                start = 25.dp,
                                top = 30.dp,
                                end = 12.dp,
                                bottom = 20.dp
                            )
                        )
                ) {

                    Text(
                        text =  it.tripDetail.operatorName , style = TextStyle(
                            color = Color.Black,
                            fontSize = 23.sp
                        )
                    )

                }

//            Box(modifier = Modifier.fillMaxSize().background(Color.Gray)){
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxSize(1f),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White,
                    ),
                    shape = RoundedCornerShape(35.dp, 35.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .padding(start = 25.dp, top = 30.dp, end = 12.dp)
                    ) {
                        Text(
                            text = "CURRENT ASSIGNMENT",
                            style = TextStyle(
                                color = Color.Gray,
                                fontSize = 21.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )

                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp)
                            .padding(start = 25.dp, top = 30.dp, end = 12.dp)
                    ) {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = it.tripDetail.tripCode,
                                    style = TextStyle(
                                        color = Color.Black,
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.ExtraBold
                                    )
                                )
                                Text(
                                    text = it.tripDetail.tripDate,
                                    style = TextStyle(
                                        color = Color.Black,
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.ExtraBold
                                    )
                                )

                            }
                            Text(
                                text = "(${it.tripDetail.tripName})",
                                style = TextStyle(
                                    color = Color.Gray,
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(160.dp)
                            .padding(start = 25.dp, top = 20.dp, end = 12.dp),
                        contentAlignment = Alignment.Center
                    )
                    {
                        Column(modifier = Modifier.fillMaxWidth()) {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painter, contentDescription = null,
                                Modifier
                                    .height(100.dp)
                                    .fillMaxSize()
                            )
                        }

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(20.dp), contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Sharing Location",
                                    style = TextStyle(
                                        color = Color.Black,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(30.dp), contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "IN TRANSIT",
                                    style = TextStyle(
                                        color = Color.Red,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                )
                            }
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Departed from AHL at 12:30 hrs",
                                    style = TextStyle(
                                        color = Color.Gray,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                )
                            }

                        }

                    }

                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                    )
                    {
                        val context = LocalContext.current
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Button(
                                colors = ButtonDefaults.buttonColors(
                                    Color.LightGray
                                ),
                                onClick = {
                                    Toast.makeText(context, "Schedule Selected", Toast.LENGTH_SHORT)
                                        .show()
                                    Log.i("toast", "new")
                                }
                            ) {
                                Text(text = "Schedule", style = TextStyle(color = Color.Black))

                            }
                            Button(
                                colors = ButtonDefaults.buttonColors(
                                    Color.LightGray
                                ),
                                onClick = {
                                    Toast.makeText(
                                        context,
                                        "Documents Selected",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                    Log.i("toast", "new")
                                }) {
                                Text(text = "Documents", style = TextStyle(color = Color.Black))

                            }

                        }

                    }

                    Box(modifier = Modifier.height(50.dp), contentAlignment = Alignment.Center) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.Bottom
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "Total Distance Covered",
                                    style = TextStyle(
                                        color = Color.Black,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                )
                                Box(contentAlignment = Alignment.Center) {
                                    Text(
                                        text = "0"+"kms" ,
                                        style = TextStyle(
                                            color = Color.Black,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Medium
                                        )
                                    )
                                }
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "Total Travelled Time",
                                    style = TextStyle(
                                        color = Color.Black,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                )
                                Text(
                                    text = "0"+" hours",
                                    style = TextStyle(
                                        color = Color.Black,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                )

                            }
                        }

                    }
                    ActiveStatusTrips(context, it.tripDetail.tripId, operatorId, selectedAssignment)
                }
            }
        }
    }
//    }


}

