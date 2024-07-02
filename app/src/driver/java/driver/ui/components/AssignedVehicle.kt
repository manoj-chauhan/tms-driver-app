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
import driver.ui.viewmodels.VehicleAssignment
import java.text.SimpleDateFormat


//@Composable
//fun AssignedVehicle(vehicleAssignment: VehicleAssignment) {
//
//    val inputFormat = SimpleDateFormat("yyyy-dd-MM'T'HH:mm")
//    val outputFormat = SimpleDateFormat("dd MMMM, yyyy HH:mm a")
//
////    val tripTime = SimpleDateFormat("HH:mm:ss")
////    val outputtripTime = SimpleDateFormat(" hh:mm a")
////
////    val parsedTime = remember(vehicleAssignment) {tripTime.parse(vehicleAssignment.timeOfNotice) }
////    val formattedTime = remember(parsedTime) { outputtripTime.format(parsedTime) }
//
//
//    val parsedDate = remember(vehicleAssignment.assignedAt) { inputFormat.parse(vehicleAssignment.assignedAt) }
//    val formattedDate = remember(parsedDate) { outputFormat.format(parsedDate) }
//
//
//
//    Column(modifier = Modifier
//        .fillMaxWidth()
//        .background(Color(0xFFF7F7F7))
//        .padding(6.dp)
//
//    ) {
//
//        Row(modifier = Modifier) {
//            Text(
//
//                text = vehicleAssignment.vehicleNumber,
//
//                fontSize = 16.sp,
//                fontWeight = FontWeight.SemiBold
//
//            )
//            Spacer(modifier = Modifier.width(18.dp))
//            Text(
//
//                text = "${vehicleAssignment.brand} ${vehicleAssignment.model}, ${vehicleAssignment.vehicleSize}ft",
//
//
//                fontSize = 12.sp,
//                color = actionColors
//
//            )
//
//        }
//
//
//        Spacer(modifier = Modifier.height(5.dp))
//        Text(
//            modifier = Modifier.fillMaxWidth(),
//            text = "Assigned by ${vehicleAssignment.assignerName}, ${vehicleAssignment.companyName} at ${formattedDate}",
//
//            fontSize = 11.sp,
//            color = actionColors,
//        )
//
//    }
//
//
//
//}

@Composable
fun AssignedVehicle(vehicleAssignment: VehicleAssignment) {
    val inputFormat = SimpleDateFormat("yyyy-dd-MM'T'HH:mm")
    val outputFormat = SimpleDateFormat("dd MMMM, yyyy HH:mm a")

    val parsedDate = remember(vehicleAssignment.assignedAt) { inputFormat.parse(vehicleAssignment.assignedAt) }
    val formattedDate = remember(parsedDate) { outputFormat.format(parsedDate) }

    val lightPeach = Color(0XFFFFF4EA)
    val darkPeach = Color(0XFFFEE1DC)

    val gradientBrush = Brush.verticalGradient(
        colors = listOf(lightPeach, darkPeach),
        startY = 0f,
        endY = 500f
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(gradientBrush)
            .padding(16.dp)
    ) {
        Text(
            text = vehicleAssignment.vehicleNumber,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Text(
            text = "${vehicleAssignment.brand} ${vehicleAssignment.model}, ${vehicleAssignment.vehicleSize}ft",
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Assigned by ${vehicleAssignment.assignerName}, ${vehicleAssignment.companyName}",
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Text(
            text = "at ${formattedDate}",
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}

