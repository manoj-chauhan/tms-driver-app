package driver.ui.pages

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
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
import dagger.hilt.android.AndroidEntryPoint
import driver.ui.viewmodels.parentTripAssigned
import driver.ui.viewmodels.placeDetailViewModel
import kotlin.math.abs
import kotlin.math.log2
import kotlin.math.max

@AndroidEntryPoint
class MapsActivity : AppCompatActivity() {
    private var operatorId: Int = 0
    private var tripCode: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        operatorId = intent.getIntExtra("operatorId", 0)
        tripCode = intent.getStringExtra("tripCode").toString()

        super.onCreate(savedInstanceState)

        setContent {

            val vm: parentTripAssigned = hiltViewModel()
            val context = LocalContext.current
            Surface(
                modifier = Modifier.fillMaxSize(),
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .height(300.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(13.dp, top = 36.dp),
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Button(
                                onClick = { vm.callApiAgain(context = context, operatorId, tripCode) },
                                modifier = Modifier.padding(end = 16.dp)
                            ) {
                                Text("Reload")
                            }
                        }
                        GoogleMapView(
                            modifier = Modifier
                                .fillMaxWidth(),
                            operatorId= operatorId,
                            tripCode = tripCode,
                            onMapLoaded = {}
                        )

                    }
                }
            }
        }
    }
}
@Composable
fun MapsActivityContent(navController: NavHostController, operatorId: Int, tripCode: String) {

    val vm: parentTripAssigned = hiltViewModel()
    val context = LocalContext.current

    val pt: placeDetailViewModel = hiltViewModel()
    val currentPlaceInfo by pt.placeInfo.collectAsStateWithLifecycle()
    pt.fetchPlaceCoordinates("MPS")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .height(300.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(13.dp, top = 36.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Button(
                    onClick = { vm.callApiAgain(context = context, operatorId, tripCode) },
                    modifier = Modifier.padding(end = 16.dp)
                ) {
                    Text("Reload")
                }
            }
            GoogleMapView(
                modifier = Modifier
                    .fillMaxWidth(),
                operatorId= operatorId,
                tripCode = tripCode,
                onMapLoaded = {}
            )
        }
    }
}
@Composable
fun GoogleMapView(
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
                process(it, processedPoints, onMapLoaded = {}, )
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

        Marker(state = rememberMarkerState(position = first),
            draggable = true,
            title = "Starting Position",
        )

        Marker(state = rememberMarkerState(position = lastPoint),
            draggable = true,
            title = "Last Position",
        )

    }
}

@Composable
fun ReloadButton(onReloadClicked: () -> Unit) {
    Button(
        onClick = onReloadClicked,
        modifier = Modifier.padding(end = 16.dp)
    ) {
        Text("Reload")
    }
}

fun calculateZoomLevel(bounds: LatLngBounds): Float {
    val ZOOM_LEVEL_CONSTANT = 10

    val sw = bounds.southwest
    val ne = bounds.northeast

    val latRatio = abs(sw.latitude - ne.latitude)
    val lngRatio = abs(sw.longitude - ne.longitude)

    val ratio = max(latRatio, lngRatio)

    return (ZOOM_LEVEL_CONSTANT - log2(ratio)).toFloat()
}

