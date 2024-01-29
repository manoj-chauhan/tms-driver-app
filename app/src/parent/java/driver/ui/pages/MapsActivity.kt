package driver.ui.pages

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.drishto.driver.models.point
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import dagger.hilt.android.AndroidEntryPoint
import driver.ui.viewmodels.parentTripAssigned

@AndroidEntryPoint
class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    private lateinit var mapView: MapView
    private lateinit var googleMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Surface(
                modifier = Modifier.fillMaxSize()
            ) {
                MapViewContent()
            }
        }
    }

    @Composable
    private fun MapViewContent(vm: parentTripAssigned = hiltViewModel()) {
        val context = LocalContext.current
        vm.fetchTripRouteCoordinates(context = context,27, "8680")
        val tripRoute by vm.points.collectAsStateWithLifecycle()

        mapView = MapView(context).apply {
            onCreate(null)
            onResume()
            getMapAsync { map ->
                googleMap = map
                onMapReady(googleMap)
                tripRoute?.let { onMapReady(googleMap, it) }
            }
        }

        AndroidView({ mapView }) { mapView ->
        }
    }

    override fun onResume() {
        super.onResume()
        super.onResume()
        if (::mapView.isInitialized) {
            mapView.onResume()
        }    }

    override fun onPause() {
        super.onPause()
        if (::mapView.isInitialized) {
            mapView.onPause()
        }    }

    override fun onDestroy() {
        super.onDestroy()
        if (::mapView.isInitialized) {
            mapView.onDestroy()
        }    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (::mapView.isInitialized) {
            mapView.onSaveInstanceState(outState)
        }    }

    override fun onMapReady(googleMap: GoogleMap) {

    }
    private fun onMapReady(googleMap: GoogleMap, routePoints: List<point>) {
        Log.d("TAG", "onMapReady: $routePoints")
        drawRoute(googleMap, routePoints)
    }

    private fun getRoutePoints(tripCoor:List<point>): List<point> {
        val serverResponse = tripCoor

        return getRoutePoints(serverResponse)
    }

}

private fun drawRoute(googleMap: GoogleMap, routePoints: List<point>?) {
    if (routePoints.isNullOrEmpty()) {
        // Handle the case where the list is null or empty
        return
    }
     val polylineOptions = PolylineOptions()
        .width(5f)
        .color(Color.BLUE)

    val firstPoint = routePoints.first()
    val lastPoint = routePoints.last()
    val firstLatLng = LatLng(firstPoint.latitude, firstPoint.longitude)
    val lastLatLng = LatLng(lastPoint.latitude, lastPoint.longitude)
    googleMap.addMarker(MarkerOptions().position(firstLatLng))
    googleMap.addMarker(MarkerOptions().position(lastLatLng))

    for (point in routePoints) {
        val latLng = LatLng(point.latitude, point.longitude)
        polylineOptions.add(latLng)
    }

    googleMap.addPolyline(polylineOptions)

    val bounds = LatLngBounds.builder()
    for (point in routePoints) {
        bounds.include(LatLng(point.latitude, point.longitude))
    }
    googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), 50))
}

