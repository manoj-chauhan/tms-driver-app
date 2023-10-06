package com.samrish.driver.ui.components
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.samrish.driver.models.Trip
import com.samrish.driver.services.getTripDetail

@Composable
fun CurrentAssignmentScreen(
    selectedAssignmentCode: String,
    operatorId: Int
) {
    val context = LocalContext.current

    Log.d("TAG", "design: $selectedAssignmentCode")
//    val tripDetail = remember {
//        mutableStateOf<Trip?>(null)
//    }

    var tripDetail by remember { mutableStateOf<Trip?>(null) }

    getTripDetail(
        context = context,
        tripCode = selectedAssignmentCode,
        operatorId = operatorId,
        onTripDetailFetched = {
//            tripDetail.value = it
            tripDetail = it;
        }
    )
    tripDetail?.let {
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
                text = "Hello", style = TextStyle(
                    color = Color.Black,
                    fontSize = 23.sp
                )
            )


        }
    }

//    tripDetail?.let {
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(Color.Yellow)
//        ) {
//            Column {
//
//                Box(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(100.dp)
//                        .padding(
//                            PaddingValues(
//                                start = 25.dp,
//                                top = 30.dp,
//                                end = 12.dp,
//                                bottom = 20.dp
//                            )
//                        )
//                ) {
//
////                    Text(
////                        text = it.operatorName, style = TextStyle(
////                            color = Color.Black,
////                            fontSize = 23.sp
////                        )
////                    )
//
//                }
//
////            Box(modifier = Modifier.fillMaxSize().background(Color.Gray)){
//                Card(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .fillMaxSize(1f),
//                    colors = CardDefaults.cardColors(
//                        containerColor = Color.White,
//                    ),
//                    shape = RoundedCornerShape(35.dp, 35.dp)
//                ) {
//                    Box(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(60.dp)
//                            .padding(start = 25.dp, top = 30.dp, end = 12.dp)
//                    ) {
//                        Text(
//                            text = "CURRENT ASSIGNMENT",
//                            style = TextStyle(
//                                color = Color.Gray,
//                                fontSize = 21.sp,
//                                fontWeight = FontWeight.Bold
//                            )
//                        )
//
//                    }
//                    Box(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(80.dp)
//                            .padding(start = 25.dp, top = 30.dp, end = 12.dp)
//                    ) {
//                        Column(modifier = Modifier.fillMaxWidth()) {
//                            Row(
//                                modifier = Modifier.fillMaxWidth(),
//                                horizontalArrangement = Arrangement.SpaceBetween
//                            ) {
////                            Text(
////                                text = "${tripDetail.code}",
////                                style = TextStyle(
////                                    color = Color.Black,
////                                    fontSize = 20.sp,
////                                    fontWeight = FontWeight.ExtraBold
////                                )
////                            )
//                                Text(
//                                    text = "12-Aug-2023",
//                                    style = TextStyle(
//                                        color = Color.Black,
//                                        fontSize = 15.sp,
//                                        fontWeight = FontWeight.ExtraBold
//                                    )
//                                )
//
//                            }
////                        Text(
////                            text = "(${tripDetail.name})",
////                            style = TextStyle(
////                                color = Color.Gray,
////                                fontSize = 17.sp,
////                                fontWeight = FontWeight.SemiBold
////                            )
////                        )
//                        }
//                    }
//
//                    Box(
//                        modifier = Modifier
//                            .fillMaxWidth()
////                        .height(160.dp)
//                            .padding(start = 25.dp, top = 30.dp, end = 12.dp),
//                        contentAlignment = Alignment.Center
//                    )
//                    {
//                        Column(modifier = Modifier.fillMaxWidth()) {
////                        Box(
////                            modifier = Modifier.fillMaxWidth(),
////                            contentAlignment = Alignment.Center
////                        ) {
////                            Image(
////                                painter = painter, contentDescription = null,
////                                Modifier
////                                    .height(100.dp)
////                                    .fillMaxSize()
////                            )
////                        }
//
//                            Box(
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                                    .height(20.dp), contentAlignment = Alignment.Center
//                            ) {
//                                Text(
//                                    text = "Sharing Location",
//                                    style = TextStyle(
//                                        color = Color.Black,
//                                        fontSize = 16.sp,
//                                        fontWeight = FontWeight.Medium
//                                    )
//                                )
//                            }
//                            Box(
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                                    .height(30.dp), contentAlignment = Alignment.Center
//                            ) {
//                                Text(
//                                    text = "IN TRANSIT",
//                                    style = TextStyle(
//                                        color = Color.Red,
//                                        fontSize = 18.sp,
//                                        fontWeight = FontWeight.Medium
//                                    )
//                                )
//                            }
//                            Box(
//                                modifier = Modifier.fillMaxWidth(),
//                                contentAlignment = Alignment.Center
//                            ) {
//                                Text(
//                                    text = "Departed from AHL at 12:30 hrs",
//                                    style = TextStyle(
//                                        color = Color.Gray,
//                                        fontSize = 18.sp,
//                                        fontWeight = FontWeight.Medium
//                                    )
//                                )
//                            }
//
//                        }
//
//                    }
//
//                    Surface(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(60.dp)
//                    )
//                    {
//                        val context = LocalContext.current
//                        Row(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .background(Color.White),
//                            horizontalArrangement = Arrangement.SpaceEvenly,
//                            verticalAlignment = Alignment.CenterVertically
//                        ) {
//                            Button(
//                                colors = ButtonDefaults.buttonColors(
//                                    Color.LightGray
//                                ),
//                                onClick = {
//                                    Toast.makeText(context, "Schedule Selected", Toast.LENGTH_SHORT)
//                                        .show()
//                                    Log.i("toast", "new")
//                                }
//                            ) {
//                                Text(text = "Schedule", style = TextStyle(color = Color.Black))
//
//                            }
//                            Button(
//                                colors = ButtonDefaults.buttonColors(
//                                    Color.LightGray
//                                ),
//                                onClick = {
//                                    Toast.makeText(
//                                        context,
//                                        "Documents Selected",
//                                        Toast.LENGTH_SHORT
//                                    )
//                                        .show()
//                                    Log.i("toast", "new")
//                                }) {
//                                Text(text = "Documents", style = TextStyle(color = Color.Black))
//
//                            }
//
//                        }
//
//                    }
//
//                    Box(modifier = Modifier.height(50.dp), contentAlignment = Alignment.Center) {
//                        Row(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .background(Color.White),
//                            horizontalArrangement = Arrangement.SpaceEvenly,
//                            verticalAlignment = Alignment.Bottom
//                        ) {
//                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                                Text(
//                                    text = "Total Distance Covered",
//                                    style = TextStyle(
//                                        color = Color.Black,
//                                        fontSize = 16.sp,
//                                        fontWeight = FontWeight.Medium
//                                    )
//                                )
//                                Box(contentAlignment = Alignment.Center) {
//                                    Text(
//                                        text = "20 kms",
//                                        style = TextStyle(
//                                            color = Color.Black,
//                                            fontSize = 16.sp,
//                                            fontWeight = FontWeight.Medium
//                                        )
//                                    )
//                                }
//                            }
//                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                                Text(
//                                    text = "Total Travelled Time",
//                                    style = TextStyle(
//                                        color = Color.Black,
//                                        fontSize = 16.sp,
//                                        fontWeight = FontWeight.Medium
//                                    )
//                                )
//                                Text(
//                                    text = "2 hours",
//                                    style = TextStyle(
//                                        color = Color.Black,
//                                        fontSize = 16.sp,
//                                        fontWeight = FontWeight.Medium
//                                    )
//                                )
//
//                            }
//                        }
//
//                    }
//
//                    Box(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(start = 25.dp, top = 30.dp, end = 12.dp)
//                            .height(90.dp), contentAlignment = Alignment.BottomStart
//                    ) {
//                        Column {
//                            Text(
//                                text = "Next Destination",
//                                style = TextStyle(
//                                    color = Color.Gray,
//                                    fontSize = 20.sp,
//                                    fontWeight = FontWeight.Bold
//                                )
//                            )
//                            Row(
//                                modifier = Modifier.fillMaxWidth(),
//                                horizontalArrangement = Arrangement.SpaceBetween,
//                                verticalAlignment = Alignment.Bottom
//                            ) {
//                                Text(
//                                    text = "BH4 - Bharat 4",
//                                    style = TextStyle(
//                                        color = Color.Black,
//                                        fontSize = 17.sp,
//                                        fontWeight = FontWeight.Bold
//                                    )
//                                )
//                                Text(
//                                    text = "STA 09:00 hours",
//                                    style = TextStyle(
//                                        color = Color.Gray,
//                                        fontSize = 13.sp,
//                                        fontWeight = FontWeight.Medium
//                                    )
//                                )
//
//                            }
//                            Row(
//                                modifier = Modifier.fillMaxWidth(),
//                                horizontalArrangement = Arrangement.SpaceBetween,
//                                verticalAlignment = Alignment.Bottom
//                            ) {
//                                Text(
//                                    text = "Distance 40kms",
//                                    style = TextStyle(
//                                        color = Color.Gray,
//                                        fontSize = 13.sp,
//                                        fontWeight = FontWeight.Medium
//                                    )
//                                )
//                                Text(
//                                    text = "Estimated Time 09:00 hours",
//                                    style = TextStyle(
//                                        color = Color.Gray,
//                                        fontSize = 13.sp,
//                                        fontWeight = FontWeight.Medium
//                                    )
//                                )
//
//                            }
//                            Row(
//                                modifier = Modifier.fillMaxWidth(),
//                                horizontalArrangement = Arrangement.SpaceBetween,
//                                verticalAlignment = Alignment.Bottom
//                            ) {
//                                Text(
//                                    text = "Distance Covered 20kms",
//                                    style = TextStyle(
//                                        color = Color.Black,
//                                        fontSize = 14.sp,
//                                        fontWeight = FontWeight.Medium
//                                    )
//                                )
//                                Text(
//                                    text = "Travelled Time 2 hours",
//                                    style = TextStyle(
//                                        color = Color.Black,
//                                        fontSize = 14.sp,
//                                        fontWeight = FontWeight.Medium
//                                    )
//                                )
//
//                            }
//                        }
//                    }
//
//                    Box(
//                        modifier = Modifier
//                            .fillMaxWidth()
////                        .fillMaxHeight()
//                            .padding(start = 25.dp, top = 30.dp, end = 12.dp, bottom = 30.dp),
//                        contentAlignment = Alignment.BottomStart
//
//                    )
//                    {
//                        var context = LocalContext.current
//                        Row(
//                            modifier = Modifier.fillMaxWidth(),
//                            horizontalArrangement = Arrangement.SpaceBetween
//                        ) {
//                            Button(colors = ButtonDefaults.buttonColors(
//                                Color.Red
//                            ),
//                                onClick = {
//                                    Toast.makeText(context, "Schedule Selected", Toast.LENGTH_LONG)
//                                        .show()
//                                    Log.i("toast", "new")
//                                }) {
//                                Text(text = "Check In ")
//                            }
//                            Button(colors = ButtonDefaults.buttonColors(
//                                Color.Red
//                            ),
//                                onClick = { /*TODO*/ }) {
//                                Text(text = "Cancel ")
//                            }
//                            Button(colors = ButtonDefaults.buttonColors(
//                                Color.Red
//                            ),
//                                onClick = { click(context) }) {
//                                Text(text = "End ")
//                            }
//
//                        }
//                    }
//                }
//            }
//        }
//    }
}

//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    val painter = painterResource(id = R.drawable.signal)
//    design(painter)
//
//}