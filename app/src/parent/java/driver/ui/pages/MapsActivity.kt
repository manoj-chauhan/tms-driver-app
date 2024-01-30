package driver.ui.pages

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import dagger.hilt.android.AndroidEntryPoint
import driver.ui.viewmodels.parentTripAssigned

val indiaState = LatLng(20.5937, 70.9629)
val defaultcameraPosition = CameraPosition.fromLatLngZoom(indiaState, 4f)

@AndroidEntryPoint
class MapsActivity : AppCompatActivity() {
    private var operatorId: Int = 0
    private var tripCode: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        operatorId = intent.getIntExtra("operatorId", 0)
        tripCode = intent.getStringExtra("tripCode").toString()

        super.onCreate(savedInstanceState)

        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
            ) {
                val cameraPositionState = rememberCameraPositionState {
                    position = defaultcameraPosition
                }
                Box(modifier = Modifier.height(300.dp)) {
                    GoogleMapView(
                        modifier = Modifier.fillMaxSize(),
                        cameraPositionState = cameraPositionState,
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
fun GoogleMapView(
    modifier: Modifier,
    cameraPositionState: CameraPositionState,
    operatorId: Int,
    tripCode: String,
    onMapLoaded: () -> Unit,
    vm: parentTripAssigned = hiltViewModel()

) {
    val context = LocalContext.current
    vm.fetchTripRouteCoordinates(context = context, operatorId, tripCode)
    vm.fetchTripProcessedCoordinates(context, 1, "3838")

    val tripRoute by vm.points.collectAsStateWithLifecycle()
    val tripProcessCoord by vm.processedpoints.collectAsStateWithLifecycle()

    Log.d("fetch", "GoogleMapView: $tripRoute")
    Log.d("fetch", "GoogleMapView: $tripProcessCoord")
    val routePoints: List<LatLng> =
        tripRoute?.map { LatLng(it.latitude, it.longitude) } ?: emptyList()
    val processedPoints: List<LatLng> =
        tripProcessCoord?.map { LatLng(it.latitude, it.longitude) } ?: emptyList()

    process(routePoints, processedPoints, cameraPositionState, onMapLoaded = {}, )
//    val firstPoint = routePoints.firstOrNull()
//    val lastPoint = routePoints.last()
//    val firstLatLng = LatLng(firstPoint.latitude, firstPoint.longitude)
//    val lastLatLng = LatLng(lastPoint.latitude, lastPoint.longitude)
//    val mapUiproperties by remember {
//        mutableStateOf(
//            MapProperties(
//                mapType = MapType.NORMAL
//            )
//        )
//    }
//
//    val mapUiSetting by remember {
//        mutableStateOf(
//            MapUiSettings(
//                compassEnabled = false
//            )
//        )
//    }

//    cameraPositionState.position = CameraPosition.Builder()
//        .target(LatLngBounds.builder().include(firstPoint).include(lastPoint).build().center)
//        .zoom(12f)
//        .build()

//    GoogleMap(
//        modifier = Modifier,
//        onMapLoaded = onMapLoaded,
//        cameraPositionState = cameraPositionState,
//        uiSettings = mapUiSetting,
//        properties = mapUiproperties
//    )
//    {
//        Polyline(
//            points = routePoints,
//            color = Color.Gray,
//            width = 5f
//        )
//
//        Polyline(
//            points = processedPoints,
//            color = Color.Blue,
//            width = 10f
//        )
//
//        if (firstPoint != null) {
//            Marker(state = rememberMarkerState(position = firstPoint))
//        }
//
//    }

}
@Composable
fun process(routePoints: List<LatLng>, processedPoints: List<LatLng>, cameraPositionState: CameraPositionState, onMapLoaded: () -> Unit) {
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


    GoogleMap(
        modifier = Modifier,
        onMapLoaded = onMapLoaded,
        cameraPositionState = cameraPositionState,
        uiSettings = mapUiSetting,
        properties = mapUiproperties
    )
    {
        Polyline(
            points = routePoints,
            color = Color.Gray,
            width = 5f
        )

        Polyline(
            points = processedPoints,
            color = Color.Blue,
            width = 10f
        )

    }
}
