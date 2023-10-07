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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.samrish.driver.models.VehicleAssignment
import com.samrish.driver.services.vehicleDetails
import com.samrish.driver.ui.components.GeneratedCodeDialog
import com.samrish.driver.viewmodels.CurrentAssignmentViewModel
import com.samrish.driver.viewmodels.VehicleAssignmentViewModel
import java.time.LocalDateTime

@Composable
fun VehicleAssignmentDetail(vm: VehicleAssignmentViewModel = viewModel()) {
    val isCheckInDialogVisible = remember { mutableStateOf(false); }

    val context = LocalContext.current
    val assignment by vm.currentAssignment.collectAsStateWithLifecycle()
    vm.fetchAssignmentDetail(context = context)

        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {

            Card(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp)

            ) {
                assignment?.let{
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "${ it.vehicleNumber }")

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

                            Text(text = "${it.brand} ${it.model} (${it.fuelType}) ${it.vehicleSize}ft")
                        }



                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Assigned by ${it.assignerName}, ${it.companyName}")
                    }
                    Row() {
                        Text(text = "Assigned At ${it.assignedAt}")
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
        }

        if (isCheckInDialogVisible.value)
            GeneratedCodeDialog(
                setShowDialog = {
                    Log.i("Dialog", "Dialog dismissed")
                    isCheckInDialogVisible.value = it
                })

    }
//}

@Preview(showBackground = true)
@Composable
fun VehicleAssignmentDetailPreview() {

    VehicleAssignmentDetail()
}