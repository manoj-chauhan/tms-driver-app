package com.samrish.driver.ui.pages

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.samrish.driver.models.TripsAssigned
import com.samrish.driver.models.VehicleAssignment
import com.samrish.driver.services.getAssignedTrips
import com.samrish.driver.services.vehicleDetails

@Composable
fun TripListPrint() {
    val context = LocalContext.current

    var tripList = remember {
        mutableStateListOf<TripsAssigned>()
    }

    getAssignedTrips(context, onTripsListFetched={
        tripList.clear()
        tripList.addAll(it)
//        Log.d("TAG", "TripAssignmentDetails:${it}")
//         obj = it[0];
//        Log.d("Obj", "details:${obj}")
//        println(obj?.tripCode);
//        println(obj?.companyName);
//        println(obj?.tripCode);
//        println(obj?.tripCode);
    })
    Column(modifier = Modifier.fillMaxSize()) {
            val context = LocalContext.current
        Row (modifier = Modifier
            .fillMaxWidth()
            .padding(13.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically){

            Text(
                text = "Assigned Trip",
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            )
        }
        VehicleAssignmentDetail()
        TripAssignmentDetails(tripList)

    }

}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TripListPrint()
}