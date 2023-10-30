package com.samrish.driver.ui.components


import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.samrish.driver.ui.viewmodels.VehicleAssignment
import java.text.SimpleDateFormat


@Composable
fun AssignedVehicle(vehicleAssignment: VehicleAssignment, assignmentCode: String, isCheckInDialogVisible: Boolean, onGenerateCodeClicked: () -> Unit, onGeneratedDialogDismissed: () -> Unit) {

    val inputFormat = SimpleDateFormat("yyyy-dd-MM'T'HH:mm")
    val outputFormat = SimpleDateFormat("dd MMMM, yyyy HH:mm a")


    val parsedDate = remember(vehicleAssignment.assignedAt) { inputFormat.parse(vehicleAssignment.assignedAt) }
    val formattedDate = remember(parsedDate) { outputFormat.format(parsedDate) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
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
                    Text(text = vehicleAssignment.vehicleNumber)

//                            assignment?.brand?.let{
//                                Text(text = it)
//                            }
//                            assignment?.model?.let{
//                                Text(text = it)
//                            }
//                            assignment?.fuelType?.let{
//                                Text(text = it)
//                            }
//                            assignment?.vehicleSize?.let{
//                                Text(text = it.toString()+ "ft")
//                            }

                    Text(text = "${vehicleAssignment.brand} ${vehicleAssignment.model} (${vehicleAssignment.fuelType}) ${vehicleAssignment.vehicleSize}ft")
                }



                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Assigned by ${vehicleAssignment.assignerName}, ${vehicleAssignment.companyName}")
                }
                Row() {
                    Text(text = "Assigned at ${formattedDate}")
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, bottom = 16.dp)
        ) {
            Button(onClick = onGenerateCodeClicked) {
                Text(text = "Generate Code")
            }
        }

    }

    if(isCheckInDialogVisible) {
        GeneratedCodeDialog(
            assignmentCode,
            setShowDialog = onGeneratedDialogDismissed
        )
    }
}
