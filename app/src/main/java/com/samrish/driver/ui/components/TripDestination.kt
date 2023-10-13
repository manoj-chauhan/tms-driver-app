package com.samrish.driver.ui.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NextDestinationInfo(nextLocationName: String?, estimatedDistance: Double?, estimatedTime:Int?, travelledDistance: Double?, travelTime: Int?, currentLocation: String? ){
    Log.d("location Name", "NextDestinationInfo: $nextLocationName")


    var currentLocationName: String? = null
    var nextLocation: String? = null

    if (currentLocation != null) {
        currentLocationName = currentLocation
    }
    if(nextLocationName!= null) {
        nextLocation = nextLocationName
    }




}
