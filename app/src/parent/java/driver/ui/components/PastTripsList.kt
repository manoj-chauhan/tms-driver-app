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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

    if (pastTrip?.size != 0) {
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
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }

                pastTrip?.let {
                    if (screen == "home") {
                        LazyColumn {
                            items(it.take(2)) { trip ->
                                past_trip(trip, onTripSelected)
                            }
                        }
                        if (it.size >= 3) {
                            val text = remember {
                                buildAnnotatedString {
                                    withStyle(
                                        style = SpanStyle(
                                            color = Color.Blue,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.W400
                                        )
                                    ) {
                                        append("SEE MORE")
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
                    } else {
                        LazyColumn {
                            items(it) { trip ->
                                past_trip(trip, onClick = onTripSelected)
                            }
                        }
                    }
                }
            }
        }
    } else {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(13.dp)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(13.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = "Past Trips",
                        style = TextStyle(
                            color = Color.Black,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.W600,
                            fontFamily = FontFamily.SansSerif
                        )
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "No trips assigned!!",
                        style = TextStyle(
                            color = Color.Black,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun past_trip(trip: ParentPastTrip,  onClick: (tripsToDriver: ParentPastTrip) -> Unit) {

    val inputFormat = SimpleDateFormat("yyyy-dd-MM")
    val outputFormat = SimpleDateFormat("dd-MMM HH:mm")

    val parsedDate = remember(trip.tripDate) { inputFormat.parse(trip.tripDate) }
    val formattedDate = remember(parsedDate) { outputFormat.format(parsedDate) }

    val gry=Color(android.graphics.Color.parseColor("#838383"))
    val fontStyle:FontFamily = FontFamily.SansSerif


    Box(
        modifier = Modifier
            .fillMaxSize(1f)
            .clickable { onClick(trip) }
            .padding(13.dp, top = 13.dp, end = 13.dp)
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color.White,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(105.dp),
            shape = RoundedCornerShape(10.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
                    .height(100.dp),
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
                            fontSize = 16.sp,
                            fontFamily = fontStyle,
                            fontWeight = FontWeight.W700
                        )
                    )


                    Text(
                        text = formattedDate,
                        style = TextStyle(
                            color = gry,
                            fontSize = 13.sp,
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

                    Text(
                        text = "2.5 kms",
                        style = TextStyle(
                            color = Color.Black,
                            fontSize = 13.sp,
                            fontFamily = fontStyle,
                            fontWeight = FontWeight.W600
                        )
                    )


                    Text(
                        text = "1 hour 20 Mins",
                        style = TextStyle(
                            color = Color.Black,
                            fontSize = 13.sp,
                            fontFamily = fontStyle,
                            fontWeight = FontWeight.W600
                        )
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = "Running Late",
                        style = TextStyle(
                            color = gry,
                            fontSize = 13.sp,
                            fontFamily = fontStyle,
                            fontWeight = FontWeight.W400
                        )
                    )

                    Text(
                        text = "Arrival 9:00 am",
                        style = TextStyle(
                            color = gry,
                            fontSize = 13.sp,
                            fontFamily = fontStyle,
                            fontWeight = FontWeight.W400
                        )
                    )
                }
            }
        }
    }
}
