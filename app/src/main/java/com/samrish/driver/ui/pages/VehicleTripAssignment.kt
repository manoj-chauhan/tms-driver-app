package com.samrish.driver.ui.pages


import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.samrish.driver.models.VehicleAssignment
import com.samrish.driver.services.vehicleDetails
import com.samrish.driver.ui.components.GeneratedCodeDialog
import java.time.LocalDateTime

@Composable
fun VehicleAssignmentDetail() {
    val isCheckInDialogVisible = remember { mutableStateOf(false); }
//    var isApiCalled = remember { mutableStateOf(false); }

    val context = LocalContext.current

    var vehicleAssignment = remember { mutableStateOf<VehicleAssignment>(VehicleAssignment(0,"",0,"","","",0,"","",""),) }

    vehicleDetails(context, onVehicleDetailFetched = {
        vehicleAssignment.value = it
//        isApiCalled.value = true
    })

//    if(isApiCalled.value) {

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
                        Text(text = "${vehicleAssignment.value.vehicleNumber}")
                        Text(text = "${vehicleAssignment.value.brand} ${vehicleAssignment.value.model} (${vehicleAssignment.value.fuelType}) ${vehicleAssignment.value.vehicleSize}ft")
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Assigned by ${vehicleAssignment.value.assignerName}, ${vehicleAssignment.value.companyName}")
                    }
                    Row() {
                        Text(text = "Assigned At ${vehicleAssignment.value.assignedAt}")
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, bottom = 16.dp)
            ) {
                Button(onClick = {
                    isCheckInDialogVisible.value = true
                }) {
                    Text(text = "Generate Code")
                }
            }

        }

        if (isCheckInDialogVisible.value)
            GeneratedCodeDialog(
                setShowDialog = {
                    Log.i("Dialog", "Dialog dismissed")
                })
    }
//}

@Preview(showBackground = true)
@Composable
fun VehicleAssignmentDetailPreview() {

    VehicleAssignmentDetail()
}