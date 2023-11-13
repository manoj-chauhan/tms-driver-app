package com.samrish.driver.ui.pages

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.samrish.driver.models.History
import com.samrish.driver.ui.viewmodels.HistoryViewModel
import java.text.SimpleDateFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(vm: HistoryViewModel = hiltViewModel()) {

    val context = LocalContext.current
    val assignment by vm.assignmentDetail.collectAsStateWithLifecycle()


    vm.fetchHistoryDetail(context)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Yellow)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(
                        PaddingValues(
                            start = 25.dp, top = 30.dp, end = 12.dp, bottom = 20.dp
                        )
                    )
            ) {

                Text(
                    text = "Old Assignments", style = TextStyle(
                        color = Color.Black, fontSize = 23.sp, fontWeight = FontWeight.Bold
                    )
                )

            }

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
                        .padding(start = 16.dp, top = 30.dp, end = 12.dp)
                ) {
//                    Text(
//                        text = "CURRENT ASSIGNMENT", style = TextStyle(
//                            color = Color.Gray, fontSize = 21.sp, fontWeight = FontWeight.Bold
//                        )
//                    )
                }
                assignment?.history?.let { historyList ->
                    val lazyListState = rememberLazyListState()
                    LazyColumn(state = lazyListState){
                        items(historyList) { trip ->
                            TripsList(trip)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TripsList(trip: History) {


    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm")
    val outputFormat = SimpleDateFormat("dd MMM HH:mm ")
    val tripDateOutputFormat = SimpleDateFormat("dd MMM YYYY HH:mm ")

    val parsedDate = remember(trip.assignedAt) { inputFormat.parse(trip.assignedAt) }
    val formattedDate = remember(parsedDate) { outputFormat.format(parsedDate) }

    val assignedTill = remember(trip.assignedTill) { inputFormat.parse(trip.assignedTill) }
    val assignedTillFormattedDate = remember(assignedTill) { outputFormat.format(assignedTill) }

    val tripDate = remember(trip.tripDate) { inputFormat.parse(trip.tripDate) }
    val tripDateFormat = remember(tripDate) { tripDateOutputFormat.format(tripDate) }


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(16.dp, 16.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = trip.tripCode,
                    style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)
                )

                Text(
                    text = tripDateFormat,
                    style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium)
                )
            }

            Row(modifier = Modifier.fillMaxWidth()) {
                Text(text = trip.tripName, style = TextStyle(fontWeight = FontWeight.SemiBold, color = Color.Gray, fontSize = 14.sp))
            }

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Bottom) {
                Text(text = trip.customerCompanyName, style = TextStyle(fontWeight = FontWeight.Normal, color = Color.Gray, fontSize = 12.sp))
                Text(text = "$formattedDate - $assignedTillFormattedDate", style = TextStyle(fontWeight = FontWeight.Normal, color = Color.Gray, fontSize = 11.sp))
            }
        }


    }
}

