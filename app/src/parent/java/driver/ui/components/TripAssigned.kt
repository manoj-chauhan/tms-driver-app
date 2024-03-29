package driver.ui.components

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import driver.models.ParentTrip
import java.text.SimpleDateFormat

@Composable
fun AssignedTrip(trip: ParentTrip, onClick: (tripsToDriver: ParentTrip) -> Unit) {

    Log.d("Trip", "AssignedTrip: $trip ")
    Box(
        modifier = Modifier
            .fillMaxSize(1f)
            .clickable { onClick(trip) }
            .padding(13.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(110.dp), shape = RoundedCornerShape(10.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .height(100.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Box(
                        modifier = Modifier
                            .width(130.dp)
                    ) {
                        Text(
                            text = trip.childName,

                            style = TextStyle(
                                color = Color.Black,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }

                    Box(
                        modifier = Modifier.width(20.dp)

                    ) {
                        Text(
                            text = trip.childStandard,
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


                    Text(
                        text = "School",
                        style = TextStyle(
                            color = Color.Black,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    )



                    Text(
                        text = trip.childSchool,
                        style = TextStyle(
                            color = Color.Black,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    )

                }

                if (trip.currentLocation != null) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Current Location",
                                style = TextStyle(
                                    color = Color.Black,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            )
                            Text(
                                text = trip.currentLocation,
                                style = TextStyle(
                                    color = Color.Black,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            )
                        }
                        if (trip.currentLocation != trip.deBoardingPlaceName) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Deboarding Location",
                                    style = TextStyle(
                                        color = Color.Black,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                )
                                Text(
                                    text = trip.deBoardingPlaceName,
                                    style = TextStyle(
                                        color = Color.Black,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                )

                            }
                        }

                    }
                } else {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Boarding Location",
                            style = TextStyle(
                                color = Color.Black,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium
                            )
                        )
                        Text(
                            text = trip.boardingPlaceName,
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
                    Text(
                        text = "Driver",
                        style = TextStyle(
                            color = Color.Black,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    )
                    Text(
                        text = "${trip.driverName}(${trip.vehicleNumber})",
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


@Composable
fun tripList(trip: ParentTrip, onClick: (tripsToDriver: ParentTrip) -> Unit) {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd")
    val outputFormat = SimpleDateFormat("dd MMM")

    val tripTime = SimpleDateFormat("HH:mm:ss")
    val outputtripTime = SimpleDateFormat(" hh:mm a")

    val parsedDate = remember(trip.tripDate) { inputFormat.parse(trip.tripDate) }
    val formattedDate = remember(parsedDate) { outputFormat.format(parsedDate) }

    val parsedTime = remember(trip.tripTime) {tripTime.parse(trip.tripTime) }
    val formattedTime = remember(parsedTime) { outputtripTime.format(parsedTime) }

    val parseddeboardingTime = remember(trip.deBoardingPlaceTime) {tripTime.parse(trip.deBoardingPlaceTime) }
    val formattedDeboardingTime = remember(parsedTime) { outputtripTime.format(parseddeboardingTime) }


    val fontStyle: FontFamily = FontFamily.SansSerif

    val gry=Color(android.graphics.Color.parseColor("#838383"))


    Box(
        modifier = Modifier
            .fillMaxSize(1f)
            .clickable { onClick(trip) }
            .padding(13.dp, top = 10.dp, end = 13.dp)
    ){
        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color.White,
            ),
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
//                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = trip.childName,
                        style = TextStyle(
                            color = Color.Black,
                            fontSize = 14.sp,
                            fontFamily = fontStyle,
                            fontWeight = FontWeight.W700
                        )
                    )


                    Text(
                        text = formattedDate+ formattedTime,
                        style = TextStyle(
                            color = gry,
                            fontSize = 12.sp,
                            fontFamily = fontStyle,
                            fontWeight = FontWeight.W600
                        )
                    )
                }
                Spacer(modifier = Modifier.height(2.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = trip.childSchool,
                        style = TextStyle(
                            color = gry,
                            fontSize = 12.sp,
                            fontFamily = fontStyle,
                            fontWeight = FontWeight.W400
                        )
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    val estimatedDistance = trip.estDistance.div(1000)
                    if(estimatedDistance >1) {
                        Text(
                            text = "${estimatedDistance.toInt()} km",
                            style = TextStyle(
                                color = Color.Black,
                                fontSize = 12.sp,
                                fontFamily = fontStyle,
                                fontWeight = FontWeight.W400
                            )
                        )
                    }else{
                        Text(
                            text = "${trip.estDistance.toInt()} m",
                            style = TextStyle(
                                color = Color.Black,
                                fontSize = 12.sp,
                                fontFamily = fontStyle,
                                fontWeight = FontWeight.W400
                            )
                        )
                    }


                    val estimatedTimeHours: Int =
                        trip.estTime.div(60).toInt()

                    if (estimatedTimeHours == 0) {
                        Text(
                            text = "${
                                trip.estTime.rem(
                                    60
                                ).toInt()
                            }" + " min",
                            style = TextStyle(
                                color = Color.Black,
                                fontSize = 12.sp,
                                fontFamily = fontStyle,
                                fontWeight = FontWeight.W600
                            )
                        )
                    } else {
                        Text(
                            text = "${
                                trip.estTime.div(
                                    60
                                )
                            }" + " hr " + "${
                                trip.estTime.rem(
                                    60
                                )
                            }" + " min",
                            style = TextStyle(
                                color = Color.Black,
                                fontSize = 12.sp,
                                fontFamily = fontStyle,
                                fontWeight = FontWeight.W600
                            )
                        )
                    }
                }


                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if(trip.status != "TRIP CREATED" && trip.status != "TRIP STARTED") {
                        if(trip.delay >0 ) {
                            Text(
                                text = "Running Late",
                                style = TextStyle(
                                    color = gry,
                                    fontSize = 12.sp,
                                    fontFamily = fontStyle,
                                    fontWeight = FontWeight.W400
                                )
                            )
                        }else{
                            Text(
                                text = "Reaching Early",
                                style = TextStyle(
                                    color = gry,
                                    fontSize = 12.sp,
                                    fontFamily = fontStyle,
                                    fontWeight = FontWeight.W400
                                )
                            )
                        }
                    }else{
                        Text(
                            text = trip.status,
                            style = TextStyle(
                                color = gry,
                                fontSize = 12.sp,
                                fontFamily = fontStyle,
                                fontWeight = FontWeight.W400
                            )
                        )
                    }

                    Text(
                        text = "Arrival $formattedDeboardingTime",
                        style = TextStyle(
                            color = gry,
                            fontSize = 12.sp,
                            fontFamily = fontStyle,
                            fontWeight = FontWeight.W400
                        )
                    )
                }
            }
        }
    }
}