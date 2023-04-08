package com.samrish.driver.ui.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.samrish.driver.models.OldAssignment
import com.samrish.driver.models.Trip
import com.samrish.driver.services.SessionStorage
import com.samrish.driver.services.getOldAssignments

@Composable
fun History() {
    val context = LocalContext.current
    val driverId = SessionStorage().getDriverId(context)

    val oldAssignmentList = remember {
        mutableStateListOf<OldAssignment>()
    }

    getOldAssignments(
        context = context,
        driverId = driverId,
        onTripsFetched = {
            oldAssignmentList.clear()
            oldAssignmentList.addAll(it)
        }
    )
    Box(
        modifier = Modifier
            .background(Color.Red)
            .fillMaxSize()
    ) {
        TripList(oldAssignmentList = oldAssignmentList)
    }
}


@Composable
fun TripDetail(oldAssignment: OldAssignment) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = oldAssignment.tripCode + " (" + oldAssignment.tripName + ")"
                )
                Text(
                    text = oldAssignment.assignedAt,
                    color = Color.Gray
                )
//                oldAssignment.assignedTill?.let {
//                    Text(
//                        text = it,
//                        color = Color.Gray
//                    )
//                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = oldAssignment.vendorName
                )

            }

        }
    }
}


@Composable
fun TripList(oldAssignmentList: List<OldAssignment>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(
            oldAssignmentList.size
        ) {
            TripDetail(oldAssignmentList[it])
        }
    }
}
