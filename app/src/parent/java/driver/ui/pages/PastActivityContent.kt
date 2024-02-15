package driver.ui.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import driver.ui.viewmodels.parentTripAssigned

@Composable
fun PastActivityContent(
    navController: NavHostController,
    operatorId: Int,
    tripCode: String,
) {

    val vm: parentTripAssigned = hiltViewModel()
    val context = LocalContext.current

    val gradient = Brush.linearGradient(
        listOf(
            Color(android.graphics.Color.parseColor("#FFFFFF")),
            Color(android.graphics.Color.parseColor("#E8F1F8"))
        ), start = Offset(0.0f, 90f), end = Offset(0.0f, 200f)
    )

    val gry=Color(android.graphics.Color.parseColor("#838383"))
    val fontStyle: FontFamily = FontFamily.SansSerif
    val back = Color(android.graphics.Color.parseColor("#F5F5F5"))

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
                            contentDescription = "Edit Icon",
                            modifier = Modifier.height(30.dp).clickable {
                                navController.popBackStack()
                            },
                        )
                    }
                    Text(
                        text = "Past Trip ",
                        style = TextStyle(
                            color = Color.Black,
                            fontSize = 24.sp,
                            fontFamily = fontStyle,
                            fontWeight = FontWeight.W600
                        )
                    )
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
                                    text = "Ankit Verma",
                                    style = TextStyle(
                                        color = Color.Black,
                                        fontSize = 14.sp,
                                        fontFamily = fontStyle,
                                        fontWeight = FontWeight.W700
                                    )
                                )


                                Text(
                                    text = "12 Jun 03:00 am",
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

                            Spacer(modifier = Modifier.height(15.dp))


                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "De-Boarded",
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
                                    text = "10:00 am",
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
                                horizontalArrangement = Arrangement.SpaceAround,
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Row(modifier = Modifier
                                    .background(
                                        color = back,
                                        shape = RoundedCornerShape(5.dp)
                                    )
                                    .width(100.dp)
                                    .padding(5.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                                    Text(
                                        text = "DL1SA1234",
                                        style = TextStyle(
                                            color = Color.Black,
                                            fontSize = 11.sp,
                                            fontFamily = fontStyle,
                                            fontWeight = FontWeight.W400
                                        )
                                    )
                                }

                                Row(modifier = Modifier
                                    .background(
                                        color = back,
                                        shape = RoundedCornerShape(5.dp)
                                    )
                                    .width(140.dp)
                                    .padding(5.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                                    Text(
                                        text = "Suraj Maheshwari",
                                        style = TextStyle(
                                            color = Color.Black,
                                            fontSize = 11.sp,
                                            fontFamily = fontStyle,
                                            fontWeight = FontWeight.W400
                                        )
                                    )
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

                                            Text(
                                                text = "12 km",
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

                                            Text(
                                                text = "12 km",
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


                                            Text(
                                                text = "1 hour",
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
                                                text = "Travel Time",
                                                style = TextStyle(
                                                    color = gry,
                                                    fontSize = 12.sp,
                                                    fontFamily = fontStyle,
                                                    fontWeight = FontWeight.W400
                                                )
                                            )

                                            Text(
                                                text = "1 Hour 12 Mins",
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
                    GoogleMapView(
                        modifier = Modifier.fillMaxWidth(),
                        operatorId = operatorId,
                        tripCode = tripCode,
                        onMapLoaded = {}
                    )
                }

            }

        }

    }
}
@Composable
fun PastGoogleMaps(
    modifier: Modifier,
    operatorId: Int,
    tripCode: String,
    onMapLoaded: () -> Unit,
    vm: parentTripAssigned = hiltViewModel()
) {
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
        processedCoor(it, processedPoints, onMapLoaded = {})
    }
}

@Composable
fun processedCoor(routePoints: List<LatLng>, processedPoints: List<LatLng>?, onMapLoaded: () -> Unit) {
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
