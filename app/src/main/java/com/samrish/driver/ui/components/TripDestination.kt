package com.samrish.driver.ui.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NextDestinationInfo(nextLocationName: String?, estimatedDistance: Double?, estimatedTime:Int?, travelledDistance: Double?, travelTime: Int?, currentLocation: String? ){
    Log.d("location Name", "NextDestinationInfo: $nextLocationName")


    var currentLocationName: String? = null
    var nextLocation: String? = null

    if (currentLocation != null) {
        currentLocationName = currentLocation
    }
    if(nextLocationName!= null) {
        nextLocation = nextLocationName
    }


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 25.dp, top = 30.dp, end = 12.dp)
            .height(90.dp), contentAlignment = Alignment.BottomStart
    ) {

        Column {
            if (nextLocation != null) {
                Text(
                    text = "Next Destination",
                    style = TextStyle(
                        color = Color.Gray,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                )


                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
//                Text(
//                    text = "$nextLocationName",
//                    modifier = Modifier
//                        .widthIn(min = 50.dp, max = 100.dp),
//                    style = TextStyle(
//                        color = Color.Black,
//                        fontSize = 17.sp,
//                        fontWeight = FontWeight.Bold
//                    )
//                )
                    Text(
                        text = "STA 09:00 hours",
                        style = TextStyle(
                            color = Color.Gray,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium
                        )
                    )

                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = "Distance ${estimatedDistance}kms",
                        style = TextStyle(
                            color = Color.Gray,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium
                        )
                    )
                    Text(
                        text = "Estimated Time ${estimatedTime} hours",
                        style = TextStyle(
                            color = Color.Gray,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium
                        )
                    )

                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = "Distance Covered ${travelledDistance}kms",
                        style = TextStyle(
                            color = Color.Black,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                    )
                    Text(
                        text = "Travelled Time ${travelTime}hrs",
                        style = TextStyle(
                            color = Color.Black,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                    )

                }
            }

            if (currentLocationName != null) {
                Text(
                    text = "Current Location ${currentLocation}",
                    style = TextStyle(
                        color = Color.Gray,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }

}
