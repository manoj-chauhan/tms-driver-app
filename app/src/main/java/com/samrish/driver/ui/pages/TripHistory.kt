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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.samrish.driver.ui.viewmodels.TripHistory
import com.samrish.driver.ui.viewmodels.TripHistoryViewModel

@Composable
fun History(navController: NavHostController,tripCode: String, thm: TripHistoryViewModel = hiltViewModel()) {
    val context = LocalContext.current

    val currentAssignmentData by thm.tripHistory.collectAsStateWithLifecycle()
    thm.fetchTripHistoryDetail(context = context, tripCode)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Yellow)
    ) {
        Column {


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
                        .height(100.dp)
                        .padding(
                            PaddingValues(
                                start = 25.dp, top = 30.dp, end = 12.dp, bottom = 20.dp
                            )
                        )
                ) {

                    Text(
                        text = "Trip History", style = TextStyle(
                            color = Color.Black, fontSize = 23.sp, fontWeight = FontWeight.Bold
                        )
                    )

                }

                currentAssignmentData?.let { historyList ->
                    val lazyListState = rememberLazyListState()
                    LazyColumn(state = lazyListState){
                        items(historyList) { history ->
                            HistoryList(history)
                        }
                    }
                }
            }
        }

    }
}

@Composable
fun HistoryList(history: TripHistory){
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 20.dp)) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    PaddingValues(
                        start = 25.dp, end = 12.dp
                    )
                )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = history.state, style = TextStyle(
                        color = Color.Gray,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = history.time, style = TextStyle(
                        color = Color.Gray,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }


        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    PaddingValues(
                        start = 25.dp, end = 12.dp
                    )
                )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = history.description,
                    style = TextStyle(
                        color = Color.Gray,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal
                    )
                )
            }
        }
    }

}