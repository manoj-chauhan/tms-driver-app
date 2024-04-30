package driver.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun MapsView(onMarkerSelected: (marker: LatLng) -> Unit, setShowDialog: (Boolean) -> Unit) {
    val indiaLatLng = LatLng(20.5937, 78.9629)

    val mapUiSettings by remember { mutableStateOf(MapUiSettings(compassEnabled = false)) }
    val mapProperties by remember { mutableStateOf(MapProperties(mapType = MapType.NORMAL)) }
    var markerPosition by remember { mutableStateOf<LatLng?>(null) }


    val defaultCameraPositionState = CameraPosition.fromLatLngZoom(indiaLatLng, 4f)
    val cameraPositionState = rememberCameraPositionState {
        position = defaultCameraPositionState
    }

    Dialog(onDismissRequest = { setShowDialog(false) },     properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Surface(
            modifier = Modifier.fillMaxSize().background(Color.White)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
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
                                            setShowDialog(false)
                                        },
                                )
                            }
                            Box(modifier = Modifier.fillMaxWidth(0.65f)) {
                                Text(
                                    text = "Location Map",
                                    style = TextStyle(
                                        color = Color.Black,
                                        fontSize = 18.sp,
                                        fontFamily = FontFamily.SansSerif,
                                        fontWeight = FontWeight.W600
                                    )
                                )
                            }

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(end = 16.dp), verticalAlignment = Alignment.Bottom
                            ) {
                                Button(
                                    modifier = Modifier
                                        .height(25.dp),
                                    enabled = if (markerPosition != null) {
                                        true
                                    } else {
                                        false
                                    },
                                    onClick = {
                                        markerPosition?.let { onMarkerSelected(it) }
                                        setShowDialog(false)
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
                                                text = "Save",
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
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 16.dp, end = 16.dp, bottom = 10.dp)
                    )
                    {
                        GoogleMap(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(800.dp),
                            onMapLoaded = { },
                            uiSettings = mapUiSettings,
                            cameraPositionState = cameraPositionState,
                            properties = mapProperties,

                            onMapClick = { latLng ->
                                markerPosition = latLng
                            }
                        ) {
                            if (markerPosition != null) {
                                Marker(
                                    state = MarkerState(
                                        position = cameraPositionState.position.target
                                    ),
                                    draggable = true,
                                    title = "Marker Title",
                                    onClick = {
                                        it.showInfoWindow()
                                        true
                                    }
                                )
                            }
                        }

                    }
                }
            }
        }
    }

}