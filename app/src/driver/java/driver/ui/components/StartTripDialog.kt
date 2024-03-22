package driver.ui.components

import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.drishto.driver.LocationService
import driver.ui.pages.LocationDisabledDialog
import driver.ui.viewmodels.AssignmentDetailViewModel

@Composable
fun StartTripDialog(tripId:Int,operatorId: Int, tripCode:String,setShowDialog: (Boolean) -> Unit) {

    val vm: AssignmentDetailViewModel = viewModel()
    var locationEnabled by remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current
    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    // Check if location is enabled
    val isLocationEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    val locationEnabledState = rememberUpdatedState(isLocationEnabled)

    Dialog(onDismissRequest = { setShowDialog(false) }) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White
        ) {
            Box(
                modifier = Modifier.padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Column() {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .align(Alignment.CenterHorizontally)

                    ) {
                        Text(
                            text = "You have to share location in order to start the trip",
                            style = TextStyle(fontWeight = FontWeight.Normal, fontSize = 16.sp)
                        )
                    }

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                        Button(
                            modifier = Modifier,
                            onClick = {
                                if (!locationEnabledState.value) {
                                    locationEnabled = true
                                } else {
                                    Log.e("Location", "LocationPermissionCheck: dialog is disabledd ",)

                                }
                                vm.startTrip(context, tripId, tripCode, operatorId)
                                val location = Intent(
                                    context,
                                    LocationService::class.java
                                )
                                context.startForegroundService(location)
                                setShowDialog(false)
                            },
                            shape = RoundedCornerShape(50.dp)
                        ) {
                            Text(text = "Yes")
                        }

                        Spacer(modifier = Modifier.width(12.dp))
                        OutlinedButton(
                            modifier = Modifier,
                            onClick = {
                                setShowDialog(false)
                            },
                            shape = RoundedCornerShape(50.dp)
                        ) {
                            Text(text = "No")
                        }
                    }
                }
            }
        }

        if(locationEnabled){
            LocationDisabledDialog()
        }
    }
}