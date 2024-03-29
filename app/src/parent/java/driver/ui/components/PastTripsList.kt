package driver.ui.components

import android.util.Log
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import driver.models.ParentPastTrip
import driver.ui.viewmodels.parentTripAssigned
import java.text.SimpleDateFormat

@Composable
fun pastTrips(
    navHostController: NavHostController,
    screen: String,
    onTripSelected: (assignment: ParentPastTrip) -> Unit,
    vm: parentTripAssigned = hiltViewModel()
) {
    val pastTrip by vm.pastTripList.collectAsStateWithLifecycle()
    vm.fetchParentPastTrip()

    Log.d("PAST", "pastTrips:  $pastTrip")

    val fontStyle: FontFamily = FontFamily.SansSerif
    val gry=Color(android.graphics.Color.parseColor("#838383"))
    val gradient = Brush.linearGradient(
        listOf(
            Color(android.graphics.Color.parseColor("#FFFFFF")),
            Color(android.graphics.Color.parseColor("#E8F1F8"))
        ), start = Offset(0.0f, 90f), end = Offset(0.0f, 200f)
    )

    if (pastTrip?.size != 0) {
        if(screen == "home"){
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 13.dp)
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(13.dp, top = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Text(
                            text = "Past Trips ",
                            style = TextStyle(
                                color = Color.Black,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }

                    pastTrip?.let {
                        if (screen == "home") {
                            Column {
                                it.take(4).forEach { trip ->
                                    past_trip(trip, onTripSelected)
                                }
                            }
                            if (it.size >= 3) {
                                val text = remember {
                                    buildAnnotatedString {
                                        withStyle(
                                            style = SpanStyle(
                                                color = gry,
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
                                                navHostController.navigate("past-trips-list")
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(brush = gradient)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 13.dp)
                ) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, top = 14.dp)
                                .height(30.dp)
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
                                                navHostController.popBackStack()
                                            },
                                    )
                                }
                                Box(modifier = Modifier.fillMaxWidth(0.65f)) {
                                    Text(
                                        text = "Past Trips",
                                        style = TextStyle(
                                            color = Color.Black,
                                            fontSize = 18.sp,
                                            fontFamily = fontStyle,
                                            fontWeight = FontWeight.W600
                                        )
                                    )
                                }
                            }
                        }

                        pastTrip?.let {

                                LazyColumn {
                                    items(it) { trip ->
                                        past_trip(trip, onClick = onTripSelected)
                                    }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun past_trip(trip: ParentPastTrip,  onClick: (tripsToDriver: ParentPastTrip) -> Unit) {

    val inputFormat = SimpleDateFormat("yyyy-MM-dd")
    val outputFormat = SimpleDateFormat("dd MMM")

    val tripTime = SimpleDateFormat("HH:mm:ss")
    val outputtripTime = SimpleDateFormat(" hh:mm a")

    val parsedDate = remember(trip.tripDate) { inputFormat.parse(trip.tripDate) }
    val formattedDate = remember(parsedDate) { outputFormat.format(parsedDate) }

    val parsedTime = remember(trip.tripTime) {tripTime.parse(trip.tripTime) }
    val formattedTime = remember(parsedTime) { outputtripTime.format(parsedTime) }


    val gry=Color(android.graphics.Color.parseColor("#838383"))
    val fontStyle:FontFamily = FontFamily.SansSerif


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
//                    .height(100.dp),
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
                        text = formattedDate + formattedTime,
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

                if(trip.deBoardingPlaceTime != null) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        val parsedDeboarding = remember(trip.deBoardingPlaceTime) {tripTime.parse(trip.deBoardingPlaceTime) }
                        val formatDeboarding = remember(parsedDeboarding) { outputtripTime.format(parsedDeboarding) }

                        Text(
                            text = "Arrived at ${formatDeboarding}",
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

                    Text(
                        text = "Boarded from ${trip.boardingPlaceName}",
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
