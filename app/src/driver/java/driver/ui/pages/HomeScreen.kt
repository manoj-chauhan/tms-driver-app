package driver.ui.pages

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.drishto.driver.LocationService
import com.drishto.driver.R
import driver.ui.components.AssignedTrip
import driver.ui.components.AssignedVehicle
import driver.ui.components.GeneratedCodeDialog
import driver.ui.viewmodels.HomeViewModel
import driver.ui.viewmodels.MatrixLogViewModel
import driver.ui.viewmodels.TripsAssigned
import java.text.SimpleDateFormat


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun HomeScreen(
    navController: NavHostController,
    vm: HomeViewModel = hiltViewModel(),
    mv: MatrixLogViewModel = hiltViewModel(),
    onTripSelected: (assignment: TripsAssigned) -> Unit
) {

    val matList by mv.matrixList.collectAsStateWithLifecycle()

    val context = LocalContext.current

    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val isConnected = runCatching {
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        activeNetworkInfo != null && activeNetworkInfo.isConnected
    }.getOrDefault(false)


    Box(modifier = Modifier.fillMaxSize()) {
        if (isConnected) {
            mv.loadMatrixLog(context = context)

            val inputFormat = SimpleDateFormat("yyyy-dd-MM'T'HH:mm")
            val outputFormat = SimpleDateFormat("HH:mm a")

            val currentAssignmentData by vm.currentAssignment.collectAsStateWithLifecycle()
            vm.fetchAssignmentDetail(context = context)

            fun isLocationServiceRunning(context: Context, serviceClass: Class<*>): Boolean {
                val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
                for (service in manager.getRunningServices(Integer.MAX_VALUE)) {
                    if (serviceClass.name == service.service.className) {
                        return true
                    }
                }
                return false
            }

            Column(modifier = Modifier.fillMaxSize()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(13.dp, top = 36.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = " ",
                        style = TextStyle(
                            color = Color.Black,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    )
                }
                currentAssignmentData?.let {
                    LocationPermissionScreen()

                    it.vehicles.let { vList ->

                        Column {
                            vList.forEach { vehicleAssignment ->
                                AssignedVehicle(vehicleAssignment)
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 16.dp, bottom = 16.dp)
                            ) {
                                Button(
                                    onClick = {
                                        vm.generateAssignmentCode(context)
                                    }
                                ) {
                                    Text(text = "Generate Code")
                                }
                            }
                            if (currentAssignmentData!!.isAssignmentCodeVisible) {
                                GeneratedCodeDialog(
                                    currentAssignmentData?.assignmentCode ?: "",
                                    setShowDialog = {
                                        vm.hideAssignmentCode(context)
                                    }
                                )
                            }
                        }

                        if (it.trips.size == 0) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(13.dp)
                                    .align(Alignment.CenterHorizontally)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = "No trips assigned!!", style = TextStyle(
                                            color = Color.Black,
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Medium
                                        )
                                    )
                                }

                            }
                            val location = Intent(context, LocationService::class.java)
                            context.stopService(location)
                        } else {
                            val location = Intent(context, LocationService::class.java)
                            context.startForegroundService(location)
                            val loc = LocationService::class.java
                            val service = isLocationServiceRunning(context, loc)
                            if (service) {
                                Column(modifier = Modifier.fillMaxWidth()) {
                                    Image(
                                        painter = painterResource(id = R.drawable.signal),
                                        contentDescription = null,
                                        Modifier
                                            .height(100.dp)
                                            .fillMaxSize()
                                    )
                                    matList?.let { mList ->
                                        if (mList.isNotEmpty()) {
                                            val lastTime = mList.last().time

                                            val parsedDate = inputFormat.parse(lastTime.toString())
                                            val formattedDate = outputFormat.format(parsedDate)
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.Center
                                            ) {
                                                Text(text = "Last recorded location time ${formattedDate} ")

                                            }
                                        } else {
                                            Column(modifier = Modifier.fillMaxWidth()) {
                                                Row(
                                                    modifier = Modifier.fillMaxWidth(),
                                                    horizontalArrangement = Arrangement.Center
                                                ) {
                                                    Text(text = "Last recorded location time - Not shared ")
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            LazyColumn {
                                items(it.trips) { trip ->
                                    AssignedTrip(trip, onClick = onTripSelected)
                                }
                            }
                        }

                        if (it.userLocationVisible) {
                            MatrixLog()
                        }
                    }

                }
            }
        } else {
            // No network available, show loading indicator or error message
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(48.dp)
                )
                Toast.makeText(
                    context,
                    "Please connect to a network and restart application",
                    Toast.LENGTH_SHORT
                ).show()            }
        }
    }
}

