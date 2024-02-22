package driver.ui.pages

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import driver.ui.viewmodels.parentTripDetail
import java.text.SimpleDateFormat
import kotlin.math.abs
import kotlin.math.log2
import kotlin.math.max

@Composable
fun MapsActivityContent(
    navController: NavHostController,
    passengerTripId: Int,
    tripCode: String,
    operatorId: Int
) {

    val vm: parentTripDetail = hiltViewModel()
    val context = LocalContext.current

    val assignmentDetail by vm.assignmentDetail.collectAsStateWithLifecycle()
    vm.fetchTripDetails(context, passengerTripId)

    Log.d("Detail", "MapsActivityContent: $assignmentDetail")

    val gradient = Brush.linearGradient(
        listOf(
            Color(android.graphics.Color.parseColor("#FFFFFF")),
            Color(android.graphics.Color.parseColor("#E8F1F8"))
        ), start = Offset(0.0f, 90f), end = Offset(0.0f, 200f)
    )

    val gry=Color(android.graphics.Color.parseColor("#838383"))
    val fontStyle: FontFamily = FontFamily.SansSerif
    val back = Color(android.graphics.Color.parseColor("#F5F5F5"))

    val inputFormat = SimpleDateFormat("yyyy-dd-MM")
    val outputFormat = SimpleDateFormat("dd MMM")

    val arrivalTime = SimpleDateFormat("HH:mm:ss")
    val outputArrivaltime = SimpleDateFormat(" HH:mm")

    var map by remember { mutableStateOf(false) }


    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = gradient)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 18.dp)
                    .height(50.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier.width(30.dp),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowBack,
                            contentDescription = "",
                            modifier = Modifier
                                .height(25.dp)
                                .clickable {
                                    navController.popBackStack()
                                },
                        )
                    }
                    Box(modifier = Modifier.fillMaxWidth(0.65f)) {
                        Text(
                            text = "Ongoing Trip ",
                            style = TextStyle(
                                color = Color.Black,
                                fontSize = 18.sp,
                                fontFamily = fontStyle,
                                fontWeight = FontWeight.W600
                            )
                        )
                    }

                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 16.dp), verticalAlignment = Alignment.Bottom) {
                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(25.dp)
                                .align(Alignment.Bottom),
                            enabled = true,
                            onClick = {
                                      navController.navigate("map-screen")
                            },
                            contentPadding = PaddingValues(),
                            colors = ButtonDefaults.buttonColors(
                                Color.Transparent
                            ),
                            shape = RoundedCornerShape(40.dp)
                        ) {
                            val primary = Color(0xFF92A3FD)
                            val secondary = Color(0XFF9DCEFF)
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .heightIn(35.dp)
                                    .align(Alignment.Bottom)
                                    .background(
                                        brush = Brush.horizontalGradient(
                                            listOf(
                                                primary,
                                                secondary
                                            )
                                        ),
                                        shape = RoundedCornerShape(40.dp)
                                    ), contentAlignment = Alignment.Center
                            ) {
                                Row(
                                    modifier = Modifier,
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = "View map",
                                        style = TextStyle(
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Box(
                modifier = Modifier.fillMaxSize()
            )
            {
                Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 10.dp)) {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White,
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.46f),
                        shape = RoundedCornerShape( topEnd = 10.dp, topStart = 10.dp),
                    ) {
                        assignmentDetail?.let {

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                        Text(
                                            text = it.passengerName,
                                            style = TextStyle(
                                                color = Color.Black,
                                                fontSize = 14.sp,
                                                fontFamily = fontStyle,
                                                fontWeight = FontWeight.W700
                                            )
                                        )

                                val parsedDate = remember(it.tripDate) { inputFormat.parse(it.tripDate)}
                                val formattedDate = remember(parsedDate) { outputFormat.format(parsedDate) }
                                val parsedTime = remember(it.tripTime) {arrivalTime.parse(it.tripTime) }
                                val formattedTime = remember(parsedDate) { outputArrivaltime.format(parsedTime) }

                                    Text(
                                        text = formattedDate + formattedTime,
                                        style = TextStyle(
                                            color = gry,
                                            fontSize = 12.sp,
                                            fontFamily = fontStyle,
                                            fontWeight = FontWeight.W600
                                        )
                                    )
                                }

                                Spacer(modifier = Modifier.height(24.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Running late ",
                                        style = TextStyle(
                                            color = Color.Black,
                                            fontSize = 14.sp,
                                            fontFamily = fontStyle,
                                            fontWeight = FontWeight.W400
                                        )
                                    )
                                }

                                Spacer(modifier = Modifier.height(4.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Estimated Arrival",
                                        style = TextStyle(
                                            color = Color.Black,
                                            fontSize = 12.sp,
                                            fontFamily = fontStyle,
                                            fontWeight = FontWeight.W400
                                        )
                                    )


                                    Text(
                                        text = "03:00 am",
                                        style = TextStyle(
                                            color = Color.Gray,
                                            fontSize = 12.sp,
                                            fontFamily = fontStyle,
                                            fontWeight = FontWeight.W400
                                        )
                                    )
                                }

                                Spacer(modifier = Modifier.height(24.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Boarded",
                                        style = TextStyle(
                                            color = gry,
                                            fontSize = 12.sp,
                                            fontFamily = fontStyle,
                                            fontWeight = FontWeight.W400
                                        )
                                    )
                                }

                                Spacer(modifier = Modifier.height(4.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Maharaja Agrasen Public School ",
                                        style = TextStyle(
                                            color = Color.Black,
                                            fontSize = 12.sp,
                                            fontFamily = fontStyle,
                                            fontWeight = FontWeight.W400
                                        )
                                    )


                                    Text(
                                        text = "03:00 am",
                                        style = TextStyle(
                                            color = Color.Gray,
                                            fontSize = 12.sp,
                                            fontFamily = fontStyle,
                                            fontWeight = FontWeight.W400
                                        )
                                    )
                                }

                                Spacer(modifier = Modifier.height(27.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {

                                    Row(
                                        modifier = Modifier
                                            .background(
                                                color = back,
                                                shape = RoundedCornerShape(5.dp)
                                            )
                                            .width(140.dp)
                                            .padding(5.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Center
                                    ) {
                                        it.vehicleNumber?.let { it1 ->
                                            Text(
                                                text = it1,
                                                style = TextStyle(
                                                    color = Color.Black,
                                                    fontSize = 11.sp,
                                                    fontFamily = fontStyle,
                                                    fontWeight = FontWeight.W400
                                                )
                                            )
                                        }
                                    }

                                    Row(
                                        modifier = Modifier
                                            .background(
                                                color = back,
                                                shape = RoundedCornerShape(5.dp)
                                            )
                                            .width(140.dp)
                                            .padding(5.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Center
                                    ) {
                                        it.driverName?.let { it1 ->
                                            Text(
                                                text = it1,
                                                style = TextStyle(
                                                    color = Color.Black,
                                                    fontSize = 11.sp,
                                                    fontFamily = fontStyle,
                                                    fontWeight = FontWeight.W400
                                                )
                                            )
                                        }
                                    }

                                }

                                Spacer(modifier = Modifier.height(27.dp))

                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .fillMaxHeight(0.5f)
                                    ) {
                                        Row(modifier = Modifier.fillMaxWidth(1f)) {
                                            Column(modifier = Modifier.fillMaxWidth(0.6f)) {
                                                Text(
                                                    text = "Estimated Distance ",
                                                    style = TextStyle(
                                                        color = gry,
                                                        fontSize = 12.sp,
                                                        fontFamily = fontStyle,
                                                        fontWeight = FontWeight.W400
                                                    )
                                                )
                                                val estimatedDistance = it.estDistance.div(1000)
                                                Text(
                                                    text = "$estimatedDistance km",
                                                    style = TextStyle(
                                                        color = Color.Black,
                                                        fontSize = 12.sp,
                                                        fontFamily = fontStyle,
                                                        fontWeight = FontWeight.W400
                                                    )
                                                )
                                            }
                                            Column(modifier = Modifier.fillMaxWidth()) {
                                                Text(
                                                    text = "Distance Covered",
                                                    style = TextStyle(
                                                        color = gry,
                                                        fontSize = 12.sp,
                                                        fontFamily = fontStyle,
                                                        fontWeight = FontWeight.W400
                                                    )
                                                )
                                                val distanceCoveredKm =
                                                    it.distanceCovered.div(1000)
                                                Text(
                                                    text = "$distanceCoveredKm km",
                                                    style = TextStyle(
                                                        color = Color.Black,
                                                        fontSize = 12.sp,
                                                        fontFamily = fontStyle,
                                                        fontWeight = FontWeight.W400
                                                    )
                                                )
                                            }
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(2.dp))

                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .fillMaxHeight(1f)
                                    ) {
                                        Row(modifier = Modifier.fillMaxWidth(1f)) {
                                            Column(modifier = Modifier.fillMaxWidth(0.6f)) {
                                                Text(
                                                    text = "Estimated Time ",
                                                    style = TextStyle(
                                                        color = gry,
                                                        fontSize = 12.sp,
                                                        fontFamily = fontStyle,
                                                        fontWeight = FontWeight.W400
                                                    )
                                                )
                                                val estimatedTimeHours: Int =
                                                    it.estTime.div(60).toInt()

                                                if (estimatedTimeHours == 0) {
                                                    Text(
                                                        text = "${
                                                            it.estTime.rem(
                                                                60
                                                            ).toInt()
                                                        }" + " min",
                                                        style = TextStyle(
                                                            color = Color.Black,
                                                            fontSize = 12.sp,
                                                            fontFamily = fontStyle,
                                                            fontWeight = FontWeight.W400
                                                        )
                                                    )
                                                } else {
                                                    Text(
                                                        text = "${
                                                            it.estTime.div(
                                                                60
                                                            )
                                                        }" + " hr " + "${
                                                            it.estTime.rem(
                                                                60
                                                            )
                                                        }" + " min",
                                                        style = TextStyle(
                                                            color = Color.Black,
                                                            fontSize = 12.sp,
                                                            fontFamily = fontStyle,
                                                            fontWeight = FontWeight.W400
                                                        )
                                                    )
                                                }
                                            }
                                            Column(modifier = Modifier.fillMaxWidth()) {
                                                Text(
                                                    text = "Travel Time",
                                                    style = TextStyle(
                                                        color = gry,
                                                        fontSize = 12.sp,
                                                        fontFamily = fontStyle,
                                                        fontWeight = FontWeight.W400
                                                    )
                                                )
                                                val travelTimeHours: Int =
                                                    it.travelTime.div(60).toInt()

                                                if (travelTimeHours == 0) {
                                                    Text(
                                                        text = "${
                                                            it.travelTime.rem(
                                                                60
                                                            ).toInt()
                                                        }" + " min",
                                                        style = TextStyle(
                                                            color = Color.Black,
                                                            fontSize = 12.sp,
                                                            fontFamily = fontStyle,
                                                            fontWeight = FontWeight.W400
                                                        )
                                                    )
                                                } else {
                                                    Text(
                                                        text = "${
                                                            it.travelTime.div(
                                                                60
                                                            ).toInt()
                                                        }" + " hr " + "${
                                                            it.travelTime.rem(
                                                                60
                                                            ).toInt()
                                                        }" + " min",
                                                        style = TextStyle(
                                                            color = Color.Black,
                                                            fontSize = 12.sp,
                                                            fontFamily = fontStyle,
                                                            fontWeight = FontWeight.W400
                                                        )
                                                    )
                                                }
                                            }

                                        }
                                    }
                                }

                            }
                        }
                    }
                }

            }
        }

    }
}

@Composable
fun GoogleMapView(
    modifier: Modifier,
    operatorId: Int,
    tripCode: String,
    onMapLoaded: () -> Unit,
    vm: parentTripDetail = hiltViewModel()
) {

    Log.d("Google map", "GoogleMapView: $operatorId")
    val context = LocalContext.current
    vm.fetchTripRouteCoordinates(context = context, operatorId, tripCode)
    vm.fetchTripProcessedCoordinates(context, operatorId, tripCode)

    val tripRoute by vm.points.collectAsStateWithLifecycle()
    val tripProcessCoord by vm.processedpoints.collectAsStateWithLifecycle()

    val routePoints: List<LatLng>? =
        tripRoute?.map { LatLng(it.latitude, it.longitude) }
    val processedPoints: List<LatLng>? =
        tripProcessCoord?.map { LatLng(it.latitude, it.longitude) }
    routePoints?.let {
        process(it, processedPoints, onMapLoaded = {})
    }
}

@Composable
fun process(routePoints: List<LatLng>, processedPoints: List<LatLng>?, onMapLoaded: () -> Unit) {
    val mapUiproperties by remember {
        mutableStateOf(
            MapProperties(
                mapType = MapType.NORMAL
            )
        )
    }


    val mapUiSetting by remember {
        mutableStateOf(
            MapUiSettings(
                compassEnabled = false
            )
        )
    }

    Log.d("List", "process:$routePoints ")
    val first = routePoints.first()
    val lastPoint = routePoints.last()
    val cameraPosition = if (processedPoints == null || processedPoints.isEmpty()) {
        val bounds = LatLngBounds.builder().include(first).include(lastPoint).build()
        CameraPosition.Builder()
            .target(bounds.center)
            .zoom(calculateZoomLevel(bounds))
            .build()
    } else {
        val bounds = LatLngBounds.builder().include(processedPoints.last()).build()
        CameraPosition.Builder()
            .target(bounds.center)
            .zoom(calculateZoomLevel(bounds))
            .build()
    }

    val cameraPositionState = rememberCameraPositionState {
        position = cameraPosition
    }

    GoogleMap(
        modifier = Modifier
            .fillMaxWidth(),
        onMapLoaded = onMapLoaded,
        cameraPositionState = cameraPositionState,
        uiSettings = mapUiSetting,
        properties = mapUiproperties
    )
    {
        Polyline(
            points = routePoints,
            color = Color.Gray,
            width = 8f
        )

        if (processedPoints != null) {
            Polyline(
                points = processedPoints,
                color = Color.Blue,
                width = 10f
            )
        }

        Marker(
            state = rememberMarkerState(position = first),
            title = "Starting Position",
        )

        Marker(
            state = rememberMarkerState(position = lastPoint),
            title = "Last Position",
        )

    }
}

fun calculateZoomLevel(bounds: LatLngBounds): Float {
    val ZOOM_LEVEL_CONSTANT = 8.5

    val sw = bounds.southwest
    val ne = bounds.northeast

    val latRatio = abs(sw.latitude - ne.latitude)
    val lngRatio = abs(sw.longitude - ne.longitude)

    val ratio = max(latRatio, lngRatio)

    return (ZOOM_LEVEL_CONSTANT - log2(ratio)).toFloat()
}
