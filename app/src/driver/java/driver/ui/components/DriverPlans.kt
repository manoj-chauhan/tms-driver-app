package driver.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.drishto.driver.models.DriverPlans
import java.text.SimpleDateFormat

@Composable
fun AssignedPlans(plan: DriverPlans, onClick: (driverPlans: DriverPlans) -> Unit) {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm")
    val outputFormat = SimpleDateFormat("dd MMMM, yyyy HH:mm a")


    val parsedFromDate = remember(plan.from) { inputFormat.parse(plan.from) }
    val formattedFromDate = remember(parsedFromDate) { outputFormat.format(parsedFromDate) }

    val parsedTillDate = remember(plan.till) { inputFormat.parse(plan.till) }
    val formattedTillDate = remember(parsedTillDate) { outputFormat.format(parsedTillDate) }

    Box(modifier = Modifier.fillMaxSize().clickable { onClick(plan) }
    ) {
        Card(
            Modifier
                .fillMaxWidth()
                .padding(16.dp)

        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = plan.vehicleNumber)
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Made by ${plan.userName}, ${plan.companyName}")
                }
                Row() {
                    Text(text = "Route name at ${plan.routeName}")
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Trip start time " + plan.startTime)

                }
            }
        }
    }
}