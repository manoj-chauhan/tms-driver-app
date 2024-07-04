package driver.ui.pages

import android.content.pm.ActivityInfo
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.drishto.driver.ui.viewmodels.TripHistory
import com.drishto.driver.ui.viewmodels.TripHistoryViewModel
import driver.ui.actionColors
import driver.ui.subHeadingColor
import java.text.SimpleDateFormat

@Composable
fun History(navController: NavHostController,tripCode: String,operatorId:Int,activity: ComponentActivity,thm: TripHistoryViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT


    val currentAssignmentData by thm.tripHistory.collectAsStateWithLifecycle()
    thm.fetchTripHistoryDetail(context = context, tripCode, operatorId)

    Box(
        modifier = Modifier
            .fillMaxSize()
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

                currentAssignmentData?.let { historyList ->
                    val lazyListState = rememberLazyListState()
                    LazyColumn(state = lazyListState, modifier = Modifier.height(500.dp)){
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

    val time = SimpleDateFormat("yyyy-dd-MM'T'HH:mm:ss")
    val outputtime = SimpleDateFormat("dd-MM-yyyy HH:mm")

    val parsedDate =
        remember(history.time) { time.parse(history.time) }
    val formattedDate = remember(parsedDate) { outputtime.format(parsedDate) }

    val displayState = when (history.state) {
        "DRIVER_ASSIGNED" -> "Driver Assigned"
        "VEHICLE_ASSIGNED" -> "Vehicle Assigned"
        "TRIP_STARTED" -> "Trip Started"
        else -> history.state
    }

    Spacer(modifier = Modifier.height(3.dp))

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(3.dp, RoundedCornerShape(12.dp))
            .padding(8.dp),

    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 25.dp, vertical = 8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = ".${displayState}",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = TextStyle(
                            color = subHeadingColor,
                            fontSize = 14.sp,

                        ),
                        modifier = Modifier.width(150.dp)
                    )
                    Text(
                        text = formattedDate,
                        style = TextStyle(
                            color = Color.Gray,
                            fontSize = 12.sp,
                        )
                    )
                }
            }
            Spacer(modifier = Modifier.height(1.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 25.dp, vertical = 8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = history.description,
                        style = TextStyle(
                            color = Color.Gray,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Normal
                        )
                    )
                }
            }
        }
    }
    Spacer(modifier = Modifier.height(3.dp))

}