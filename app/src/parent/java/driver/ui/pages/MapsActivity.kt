package driver.ui.pages

import android.graphics.Color
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions

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
    private fun MapViewContent() {
        val context = LocalContext.current
        mapView = MapView(context).apply {
            onCreate(null)
            onResume()
            getMapAsync { map ->
                googleMap = map
                onMapReady(googleMap)
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
        val routePoints = getRoutePoints()
        drawRoute(googleMap, routePoints)
    }

    private fun getRoutePoints(): List<LatLng> {
        // Replace this with your logic to fetch route points from the server
        return listOf(
            LatLng(28.705827921844318, 77.0974697720288),
            LatLng(28.70584, 77.09748),
            LatLng(28.70525, 77.09843),
            LatLng(28.70402, 77.10057),
            LatLng(28.704013551598216, 77.10056386317629)
        )
    }

    private fun drawRoute(googleMap: GoogleMap, routePoints: List<LatLng>) {
        val polylineOptions = PolylineOptions()
            .width(5f) // Set the width of the polyline
            .color(Color.BLUE) // Set the color of the polyline

        for (point in routePoints) {
            googleMap.addMarker(MarkerOptions().position(point))
            polylineOptions.add(point)
        }

        // Add the polyline to the map
        googleMap.addPolyline(polylineOptions)

        // Move camera to the bounds of the polyline
        val bounds = LatLngBounds.builder()
        for (point in routePoints) {
            bounds.include(point)
        }
        googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), 50))
    }
}

