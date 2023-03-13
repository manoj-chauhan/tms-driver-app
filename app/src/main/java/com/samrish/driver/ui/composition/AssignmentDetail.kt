package com.samrish.driver.ui.composition

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.samrish.driver.models.Trip

import getTripDetail

@Composable
fun AssignmentDetail(
    assignmentCode:String
) {

    var x = remember {
        mutableStateOf<Trip?>(Trip("","",""))
    }

    getTripDetail(
        context = LocalContext.current,
        tripCode = assignmentCode,
        onTripDetailFetched = {
            x.value = it
        }
    );
    Row( modifier = Modifier
        .padding(16.dp)
        .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Card(modifier = Modifier.fillMaxWidth()) {
            Text(modifier = Modifier.padding(16.dp), text = "${x.value?.code}")
            Text(modifier = Modifier.padding(16.dp), text = "${x.value?.name}")
            Text(modifier = Modifier.padding(16.dp), text = "${x.value?.status}")
        }
    }

}