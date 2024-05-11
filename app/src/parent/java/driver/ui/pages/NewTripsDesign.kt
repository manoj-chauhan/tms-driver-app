package driver.ui.pages

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import driver.models.PastTrip
import driver.models.PresentTrip
import driver.models.getDummyPastTrip
import driver.models.getDummyPresentTrip

@Composable
fun NewTripsDesign() {
    val tripList = getDummyPresentTrip()
    val headingColor = Color(android.graphics.Color.parseColor("#1c1b1f"))

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(13.dp, top = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Current Trips ",
                    style = TextStyle(
                        color = headingColor,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W500,
                        fontFamily = FontFamily.SansSerif
                    )
                )
            }

            Column(modifier = Modifier.padding(10.dp)) {
                tripList.forEach { trip ->
                    PresentTrip(trip, onTripSelected = {})
                }
                PastTripDesign()
            }
        }
    }
}

@Composable
fun PastTripDesign() {
    val headingColor = Color(android.graphics.Color.parseColor("#1c1b1f"))
    val pastTrips = getDummyPastTrip()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 13.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 3.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "Past Trips ",
                    style = TextStyle(
                        color = headingColor,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W500
                    )
                )
            }

            Column(modifier = Modifier.padding(top = 13.dp)) {
                pastTrips.forEach { trip ->
                    PastTripList(trip, onPastTripSelected = {})
                }
            }
        }
    }
}

@Composable
fun PastTripList(trip: PastTrip, onPastTripSelected: () -> Unit) {
    val colors = Color(android.graphics.Color.parseColor("#828282"))
    val arrival = Color(android.graphics.Color.parseColor("#ef2427"))
    val place = Color(android.graphics.Color.parseColor("#999999"))

    val fontStyle: FontFamily = FontFamily.SansSerif


    ElevatedCard(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onPastTripSelected() }
            .shadow(3.dp, RoundedCornerShape(12.dp)),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = trip.date,
                    style = TextStyle(
                        color = colors,
                        fontSize = 12.sp,
                        fontFamily = fontStyle,
                        fontWeight = FontWeight.W400
                    )
                )


                Text(
                    text = trip.status,
                    style = TextStyle(
                        color = arrival,
                        fontSize = 12.sp,
                        fontFamily = fontStyle,
                        fontWeight = FontWeight.W600
                    )
                )
            }

            Spacer(modifier = Modifier.height(10.dp))


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Box(modifier = Modifier.width(80.dp)) {
                        Text(
                            text = "Departure",
                            style = TextStyle(
                                color = colors,
                                fontSize = 12.sp,
                                fontFamily = fontStyle,
                                fontWeight = FontWeight.W600
                            )
                        )
                    }
                    Box(modifier = Modifier.fillMaxWidth(0.7f)) {
                        Text(
                            text = trip.departure,
                            style = TextStyle(
                                color = place,
                                fontSize = 12.sp,
                                fontFamily = fontStyle,
                                fontWeight = FontWeight.W400
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    Spacer(modifier = Modifier.padding(3.dp))

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End){
                        Text(
                            text = trip.departureTime,
                            style = TextStyle(
                                color = place,
                                fontSize = 12.sp,
                                fontFamily = fontStyle,
                                fontWeight = FontWeight.W400
                            )
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Box(modifier = Modifier.width(80.dp)) {
                        Text(
                            text = "Arrival",
                            style = TextStyle(
                                color = colors,
                                fontSize = 12.sp,
                                fontFamily = fontStyle,
                                fontWeight = FontWeight.W600
                            )
                        )
                    }
                    Box(modifier = Modifier.fillMaxWidth(0.7f)) {
                        Text(
                            text = trip.arrival,
                            style = TextStyle(
                                color = place,
                                fontSize = 12.sp,
                                fontFamily = fontStyle,
                                fontWeight = FontWeight.W400
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    Spacer(modifier = Modifier.padding(3.dp))

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End){
                        Text(
                            text = trip.arrivalTime,
                            style = TextStyle(
                                color = place,
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
    Spacer(modifier = Modifier.height(10.dp))

}

@Composable
fun PresentTrip(trip: PresentTrip, onTripSelected: () -> Unit) {
    val cardColor = Color(android.graphics.Color.parseColor("#dae2ff"))
    val arrival = Color(android.graphics.Color.parseColor("#ef2427"))
    val colors = Color(android.graphics.Color.parseColor("#828282"))
    val message = Color(android.graphics.Color.parseColor("#939499"))
    val destination = Color(android.graphics.Color.parseColor("#a3a6b1"))



    val fontStyle: FontFamily = FontFamily.SansSerif

    val destinations = AnnotatedString.Builder().apply {
        pushStyle(
            style = SpanStyle(
                color = message,
                fontSize = 14.sp,
                fontFamily = fontStyle,
                fontWeight = FontWeight.W400
            )
        )
        append(trip.mesage)
        pop()

        pushStyle(
            style = SpanStyle(
                color = colors,
                fontSize = 14.sp,
                fontFamily = fontStyle,
                fontWeight = FontWeight.W500
            )
        )
        append(" ${trip.reaching}")
        pop()
    }.toAnnotatedString()

    val expectedDestination = AnnotatedString.Builder().apply {
        pushStyle(
            style = SpanStyle(
                color = Color.Gray,
                fontSize = 11.sp,
                fontFamily = fontStyle,
                fontWeight = FontWeight.W400
            )
        )
        append(trip.destination)
        pop()

        pushStyle(
            style = SpanStyle(
                color = colors,
                fontSize = 12.sp,
                fontFamily = fontStyle,
                fontWeight = FontWeight.W400
            )
        )
        append(" ${trip.destinationPlace}")
        pop()

        pushStyle(
            style = SpanStyle(
                color = colors,
                fontSize = 12.sp,
                fontFamily = fontStyle,
                fontWeight = FontWeight.W400
            )
        )
        append(" at ${trip.timing}")
        pop()
    }.toAnnotatedString()



    ElevatedCard(
        colors = CardDefaults.cardColors(containerColor = cardColor),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onTripSelected() }
            .shadow(3.dp, RoundedCornerShape(12.dp)),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = trip.day,
                    style = TextStyle(
                        color = colors,
                        fontSize = 12.sp,
                        fontFamily = fontStyle,
                        fontWeight = FontWeight.W400
                    )
                )


                Text(
                    text = trip.arrival,
                    style = TextStyle(
                        color = arrival,
                        fontSize = 12.sp,
                        fontFamily = fontStyle,
                        fontWeight = FontWeight.W600
                    )
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Row(modifier = Modifier.fillMaxWidth(0.9f)) {
                    Text(
                        text = destinations,
                    )
                }
                Spacer(modifier = Modifier.width(7.dp))
                Icon(
                    imageVector = Icons.Outlined.LocationOn,
                    contentDescription = "",
                    modifier = Modifier
                        .size(30.dp)
                        .clickable {
//                                onSearchClick()
                        },
                    tint = colors
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Row(modifier = Modifier.fillMaxWidth(0.9f)) {
                    Text(
                        text = expectedDestination,
                    )
                }
                Spacer(modifier = Modifier.width(7.dp))
                Icon(
                    imageVector = Icons.Outlined.LocationOn,
                    contentDescription = "",
                    modifier = Modifier
                        .size(30.dp)
                        .clickable {
//                                onSearchClick()
                        },
                    tint = colors
                )
            }
        }
    }
    
    Spacer(modifier = Modifier.height(10.dp))

}

@Composable
@Preview
fun NewTripPreview() {
    NewTripsDesign()
}
