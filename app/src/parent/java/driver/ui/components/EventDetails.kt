package driver.ui.components

import android.util.Log
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.tooling.data.EmptyGroup.location
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import driver.models.Event
import driver.models.Location
import driver.ui.pages.colortext
import driver.ui.viewmodels.EventsViewModel

@Composable
fun EventDetail(eventId: String?, navController: NavHostController) {

    val eventsViewModel: EventsViewModel = hiltViewModel()

    val eventDetail by eventsViewModel.eventDetail.collectAsState()

    val onBackPressed: () -> Unit = {
        navController.navigateUp()
    }

    LaunchedEffect(Unit) {
        eventId?.let { id ->
            eventsViewModel.getEventById(id)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        LazyColumn(
            modifier = Modifier.padding(12.dp)
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                ) {
                    AsyncImage(
                        model = eventDetail?.descriptionImage,
                        contentDescription = null,
                        modifier = Modifier.fillMaxWidth(),
                        contentScale = ContentScale.Crop
                    )
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .padding(8.dp)
                            .align(Alignment.TopEnd)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Bookmark,
                            contentDescription = "Save",
                            tint = Color.White,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                eventDetail?.let {
                    Text(
                        text = it.title,
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontFamily = FontFamily.SansSerif,
                            color = colortext,

                        )
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Date: 13-05-2024",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = FontFamily.SansSerif,
                        color = Color.LightGray,
                        fontWeight = FontWeight.Normal
                    )
                )

                Text(
                    text = "Institution: ${eventDetail?.instituteName}",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = FontFamily.SansSerif,
                        color = Color.LightGray,
                        fontWeight = FontWeight.Normal
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        modifier = Modifier
                            .height(25.dp),
                        enabled = true,
                        onClick = {  },
                        contentPadding = PaddingValues(),
                        colors = ButtonDefaults.buttonColors(
                            Color.Transparent
                        ),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .heightIn(35.dp)
                                .width(100.dp)
                                .align(Alignment.Bottom)
                                .background(
                                    brush = Brush.horizontalGradient(
                                        listOf(
                                            Color(0xFF92A3FD),
                                            Color(0XFF9DCEFF)
                                        )
                                    ),
                                    shape = RoundedCornerShape(1.dp)
                                ), contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Register",
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))


                eventDetail?.description?.let {
                    Text(
                        text = it,
                        style = TextStyle(
                            color = Color.Gray,
                            textAlign = TextAlign.Justify,
                            fontFamily = FontFamily.SansSerif
                        )
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                PhotoView()

                Spacer(modifier = Modifier.height(16.dp))

                eventDetail?.location?.let { location ->
                    ShowGoogleMap(event = location)
                }
            }
        }


        IconButton(
            onClick = onBackPressed,
            modifier = Modifier
                .size(60.dp)
                .padding(8.dp)
                .align(Alignment.TopStart)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color.Black,
                modifier = Modifier.size(30.dp)
            )
        }
    }
}

@Composable
fun ShowGoogleMap(event: Location) {

    val mapUiSettings by remember { mutableStateOf(MapUiSettings(compassEnabled = false)) }
    val mapProperties by remember { mutableStateOf(MapProperties(mapType = MapType.NORMAL)) }


    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Location",
            style = TextStyle(fontSize = 12.sp, color = colortext)
        )

    }
    Spacer(modifier = Modifier.height(6.dp))
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = event.location,
            style = TextStyle(fontSize = 14.sp, color = Color.Gray)
        )

    }




    val marker = LatLng(event.latitude, event.longitude)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(marker, 20f)

    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
    ) {
        GoogleMap(
            modifier = Modifier
                .fillMaxWidth()
                .height(800.dp),
            onMapLoaded = { },
            uiSettings = mapUiSettings,
            cameraPositionState = cameraPositionState,
            properties = mapProperties,
            onMapClick = {

            }
        ) {
            if (marker != null) {
                Marker(
                    state = MarkerState(
                        position = marker!!,
                    ),
                    draggable = false,
                    title = event.location,

                )
            }
        }
    }
}


@Composable
fun PhotoView() {
    var selectedImageUri by remember {
        mutableStateOf<List<String?>>(emptyList())
    }

    selectedImageUri = listOf(
        "https://www.planetware.com/wpimages/2020/02/france-in-pictures-beautiful-places-to-photograph-eiffel-tower.jpg",
        "https://www.planetware.com/wpimages/2020/02/france-in-pictures-beautiful-places-to-photograph-eiffel-tower.jpg",
        "https://www.planetware.com/wpimages/2020/02/france-in-pictures-beautiful-places-to-photograph-eiffel-tower.jpg",
        "https://www.planetware.com/wpimages/2020/02/france-in-pictures-beautiful-places-to-photograph-eiffel-tower.jpg",
        "https://www.planetware.com/wpimages/2020/02/france-in-pictures-beautiful-places-to-photograph-eiffel-tower.jpg",
        "https://www.planetware.com/wpimages/2020/02/france-in-pictures-beautiful-places-to-photograph-eiffel-tower.jpg",

        )

    val columns = when (selectedImageUri.size) {
        1 -> 1
        2 -> 2
        3 -> 2
        else -> 2
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .heightIn(max = 450.dp)
    ) {
        LazyVerticalGrid(
            modifier = Modifier.fillMaxWidth(),
            columns = GridCells.Fixed(columns),
        ) {
            val imageCount = selectedImageUri.size

            items(imageCount, key = { it }) { index ->
                val uri = selectedImageUri[index]
                if (uri != null) {

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        if (uri != null) {
                            if (index < 4) {
                                AsyncImage(
                                    model = uri,
                                    contentDescription = "Selected Image",
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .fillMaxHeight()
                                        .align(Alignment.TopStart)
                                )
                            }
                        }
                    }
                }
                if (index == 3) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .zIndex(1f)
                            .background(Color.Black.copy(alpha = 0.4f))
                    ) {
                        Text(
                            text = "+${imageCount - (index + 1)}",
                            color = Color.White,
                            fontSize = 18.sp,
                            modifier = Modifier
                                .padding(top = 50.dp, bottom = 40.dp)
                                .align(Alignment.Center)
                        )
                    }
                }
            }
        }
    }
}