package com.samrish.driver.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.samrish.driver.ui.pages.MatrixRecord
import com.samrish.driver.viewmodels.Schedule
import com.samrish.driver.viewmodels.ScheduleLocation

@Composable
fun ScheduleDialog(
    location:Schedule,
    setShowDialog: (Boolean) -> Unit
) {
    Dialog(onDismissRequest = { setShowDialog(false) }) {

        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White
        ) {
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
//                matList!!.forEach { record -> MatrixRecord(record) }
                items(
                    location.locations.size
                ) {
                    LocationList(location.locations[it] )
                }
            }
        }

    }
}


@Composable
fun LocationList(location: ScheduleLocation){
    Text(text = "${ location.placeCode }")
}