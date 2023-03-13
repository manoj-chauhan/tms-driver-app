package com.samrish.driver.ui.composition

import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.samrish.driver.models.Trip

import getTripDetail

@Composable
fun AssignmentDetail(
    assignmentCode:String
) {

    var x = remember {
        mutableStateOf<Trip?>(null)
    }

    getTripDetail(
        context = LocalContext.current,
        tripCode = assignmentCode,
        onTripDetailFetched = {
            x.value = it
        }
    );

    x.value?.let {
        Card() {
            Text(text = "($it.code}")
            Text(text = "${it.name}")
            Text(text = "${it.status}")

        }
    }

}