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
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import driver.arrivalTripColor
import driver.cardColor
import driver.headingColor
import driver.models.ParentPastTrip
import driver.models.ParentTrip
import driver.placeColor
import driver.subHeadingColor
import driver.ui.viewmodels.parentTripAssigned
import java.text.SimpleDateFormat
import java.time.LocalDate

@Composable
fun NewTripsDesign(
    navigationController:NavHostController,
    onTripSelected: (assignment: ParentTrip) -> Unit,
    onPastTripSelected: (assignment: ParentPastTrip) -> Unit,
) {


    val parentTripAssigned: parentTripAssigned = hiltViewModel()
    val tripList by parentTripAssigned.parentTrip.collectAsStateWithLifecycle()

    val context = LocalContext.current


    parentTripAssigned.fetchParentTrip(context)

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
                tripList?.forEach { trip ->
                    CurrentTrip(trip, onTripSelected)
                }
                PastTrip(onPastTripSelected, "home", navigationController)
            }
        }
    }
}

@Composable
fun PastTrip(onPastTripSelected: (assignment: ParentPastTrip) -> Unit, screen: String, navigationController: NavHostController) {
    val parentTripAssigned: parentTripAssigned = hiltViewModel()
    val pastTripList by parentTripAssigned.pastTripList.collectAsStateWithLifecycle()

    val context = LocalContext.current

    parentTripAssigned.fetchParentPastTrip()
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            if (screen == "home") {
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
                        Spacer(modifier = Modifier.height(10.dp))
                        pastTripList?.let {
                            if (screen == "home") {
                                Column {
                                    it.take(4).forEach { trip ->
                                        PastTripList(trip, onPastTripSelected)
                                    }
                                }
                                if (it.size >= 3) {
                                    val text = remember {
                                        buildAnnotatedString {
                                            withStyle(
                                                style = SpanStyle(
                                                    color = subHeadingColor,
                                                    fontSize = 12.sp,
                                                    fontWeight = FontWeight.W400
                                                )
                                            ) {
                                                append("SEE ALL")
                                            }
                                        }
                                    }


                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(13.dp)
                                    ) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.End
                                        ) {

                                            ClickableText(
                                                text = text,
                                                onClick = { offset -> 
                                                    navigationController.navigate("past-trips-list")
                                                }
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize().padding(10.dp)
                    ) {
                        Column(modifier = Modifier.fillMaxSize()) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding( top = 10.dp)
                                    .height(30.dp) ,
                                verticalAlignment = Alignment.Top
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Start,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(
                                        modifier = Modifier.width(30.dp),
                                        horizontalArrangement = Arrangement.Start
                                    ) {
                                        Icon(
                                            imageVector = Icons.Outlined.ArrowBack,
                                            contentDescription = "",
                                            modifier = Modifier
                                                .height(25.dp)
                                                .clickable {
                                                    navigationController.popBackStack()
                                                },
                                        )
                                    }
                                    Box(modifier = Modifier.fillMaxWidth(0.65f)) {
                                        Text(
                                            text = "Past Trips",
                                            style = TextStyle(
                                                color = Color.Black,
                                                fontSize = 18.sp,
                                                fontFamily = FontFamily.SansSerif,
                                                fontWeight = FontWeight.W600
                                            )
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            pastTripList?.forEach { trip ->
                                PastTripList(trip, onPastTripSelected)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PastTripList(trip: ParentPastTrip, onPastTripSelected: (assignment: ParentPastTrip) -> Unit) {
    val fontStyle: FontFamily = FontFamily.SansSerif

    val tripTime = SimpleDateFormat("HH:mm:ss")
    val outputtripTime = SimpleDateFormat(" hh:mm a")

    val parsedTime = remember(trip.tripTime) { tripTime.parse(trip.tripTime) }
    val formattedTime = remember(parsedTime) { outputtripTime.format(parsedTime) }

    val inputFormat = remember { SimpleDateFormat("yyyy-MM-dd") }
    val outputFormat = remember { SimpleDateFormat("dd MMM") }

    val parsedDate = remember(trip.tripDate) { inputFormat.parse(trip.tripDate) }
    val formattedDate = remember(parsedDate) { outputFormat.format(parsedDate) }

    val localDate = remember { LocalDate.now() }
    val tripLocalDate = remember(trip.tripDate) { LocalDate.parse(trip.tripDate) }

    val dateString = when {
        tripLocalDate == localDate -> "Today"
        tripLocalDate == localDate.minusDays(1) -> "Yesterday"
        else -> formattedDate
    }


    ElevatedCard(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onPastTripSelected(trip) }
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
//
                    text = "$dateString, $formattedTime",

                    style = TextStyle(
                        color = subHeadingColor,
                        fontSize = 12.sp,
                        fontFamily = fontStyle,
                        fontWeight = FontWeight.W400
                    )
                )


                Text(
                    text = trip.status,
                    style = TextStyle(
                        color = arrivalTripColor,
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
                                color = subHeadingColor,
                                fontSize = 12.sp,
                                fontFamily = fontStyle,
                                fontWeight = FontWeight.W600
                            )
                        )
                    }
                    Box(modifier = Modifier.fillMaxWidth(0.7f)) {
                        Text(
                            text = trip.boardingPlaceName,
                            style = TextStyle(
                                color = placeColor,
                                fontSize = 12.sp,
                                fontFamily = fontStyle,
                                fontWeight = FontWeight.W400
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    Spacer(modifier = Modifier.padding(3.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        trip.deBoardingPlaceTime?.let {
                            Text(
                                text = it,
                                style = TextStyle(
                                    color = placeColor,
                                    fontSize = 12.sp,
                                    fontFamily = fontStyle,
                                    fontWeight = FontWeight.W400
                                )
                            )
                        }
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
                                color = subHeadingColor,
                                fontSize = 12.sp,
                                fontFamily = fontStyle,
                                fontWeight = FontWeight.W600
                            )
                        )
                    }
                    Box(modifier = Modifier.fillMaxWidth(0.7f)) {
                        Text(
                            text = trip.deBoardingPlaceName,
                            style = TextStyle(
                                color = placeColor,
                                fontSize = 12.sp,
                                fontFamily = fontStyle,
                                fontWeight = FontWeight.W400
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    Spacer(modifier = Modifier.padding(3.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        trip.deBoardingPlaceTime?.let {
                            Text(
                                text = it,
                                style = TextStyle(
                                    color = placeColor,
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
    }
    Spacer(modifier = Modifier.height(10.dp))

}

@Composable
fun CurrentTrip(trip: ParentTrip, onClick: (tripsToDriver: ParentTrip) -> Unit) {
    val message = Color(android.graphics.Color.parseColor("#939499"))
    val fontStyle: FontFamily = FontFamily.SansSerif

    val tripTime = SimpleDateFormat("HH:mm:ss")
    val outputtripTime = SimpleDateFormat(" hh:mm a")

    val parsedTime = remember(trip.tripTime) { tripTime.parse(trip.tripTime) }
    val formattedTime = remember(parsedTime) { outputtripTime.format(parsedTime) }

    val localDate = remember { LocalDate.now() }
    val tripLocalDate = remember(trip.tripDate) { LocalDate.parse(trip.tripDate) }

    val inputFormat = remember { SimpleDateFormat("yyyy-MM-dd") }
    val outputFormat = remember { SimpleDateFormat("dd MMM") }

    val parsedDate = remember(trip.tripDate) { inputFormat.parse(trip.tripDate) }
    val formattedDate = remember(parsedDate) { outputFormat.format(parsedDate) }

    val dateString = when {
        tripLocalDate == localDate -> "Today"
        tripLocalDate == localDate.minusDays(1) -> "Yesterday"
        else -> formattedDate
    }

    val destinations = AnnotatedString.Builder().apply {
        pushStyle(
            style = SpanStyle(
                color = message,
                fontSize = 14.sp,
                fontFamily = fontStyle,
                fontWeight = FontWeight.W400
            )
        )
//        append(trip.mesage)
        append(text = "Please reach to your stop")
        pop()

        pushStyle(
            style = SpanStyle(
                color = subHeadingColor,
                fontSize = 14.sp,
                fontFamily = fontStyle,
                fontWeight = FontWeight.W500
            )
        )
        append(" ${trip.boardingPlaceName}")
//        append(text=" D black, Swaroop Nagar Delhi, Delhi")
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
//        append(trip.destination)
        append(text = "Expected to reach destination")
        pop()

        pushStyle(
            style = SpanStyle(
                color = subHeadingColor,
                fontSize = 12.sp,
                fontFamily = fontStyle,
                fontWeight = FontWeight.W400
            )
        )
        append(" ${trip.deBoardingPlaceName}")

        pop()




        pushStyle(
            style = SpanStyle(
                color = subHeadingColor,
                fontSize = 12.sp,
                fontFamily = fontStyle,
                fontWeight = FontWeight.W400
            )
        )
//        append(" at ${trip.deBoardingPlaceTime}")
        append(text = "  $formattedTime")

        pop()

    }.toAnnotatedString()



    ElevatedCard(
        colors = CardDefaults.cardColors(containerColor = cardColor),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(trip) }
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
                    text = "$dateString, $formattedTime",
                    style = TextStyle(
                        color = subHeadingColor,
                        fontSize = 12.sp,
                        fontFamily = fontStyle,
                        fontWeight = FontWeight.W400
                    )
                )


                Text(
//                    text = trip.arrival,
                    text = "Arrival in 20 minutes",
                    style = TextStyle(
                        color = arrivalTripColor,
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
                    tint = subHeadingColor
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
                    tint = subHeadingColor
                )
            }
        }
    }

    Spacer(modifier = Modifier.height(10.dp))

}

