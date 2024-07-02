package driver.ui.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import driver.ui.actionColors
import driver.ui.headingColor
import driver.ui.subText
import driver.ui.viewmodels.VehicleAssignment
import java.text.SimpleDateFormat



@Composable
fun AssignedVehicle(vehicleAssignment: VehicleAssignment) {
    val inputFormat = SimpleDateFormat("yyyy-dd-MM'T'HH:mm")
    val outputFormat = SimpleDateFormat("dd MMMM, yyyy HH:mm a")

    val parsedDate = remember(vehicleAssignment.assignedAt) { inputFormat.parse(vehicleAssignment.assignedAt) }
    val formattedDate = remember(parsedDate) { outputFormat.format(parsedDate) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
            .padding(16.dp)
    ) {
        Text(
            text = vehicleAssignment.vehicleNumber,
            fontSize = 20.sp,
            color = headingColor,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "${vehicleAssignment.brand} ${vehicleAssignment.model}, (${vehicleAssignment.vehicleSize}ft)",
            fontSize = 11.sp,
            color = subText,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Assigned by ${vehicleAssignment.assignerName}, ${vehicleAssignment.companyName}",
            fontSize = 11.sp,
            color = actionColors,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Text(
            text = "at ${formattedDate}",
            fontSize = 11.sp,
            color = actionColors,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}

