package driver.ui.components

import android.util.Log
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import driver.models.Event
import driver.ui.pages.EventListPage
import driver.ui.viewmodels.EventsViewModel

@Composable
//fun EventRegistration(eventDetail: Event) {
fun EventRegistration(eventId: String?, navController: NavHostController){

    val eventsViewModel: EventsViewModel = hiltViewModel()

    val eventDetail by eventsViewModel.eventDetail.collectAsState()


    LaunchedEffect(Unit) {
        eventId?.let { id ->
            eventsViewModel.getEventById(id)
        }
    }

//    events?.let { EventListPage(events = it, onRegisterClick = onRegisterClick) }

    val fontFamily = FontFamily.SansSerif
    val primary = Color(0xFF92A3FD)
    val secondary = Color(0XFF9DCEFF)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        LazyColumn { item()
            {
        Column(modifier = Modifier) {
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

                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .padding(8.dp)
                        .align(Alignment.TopStart)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Save",
                        tint = Color.Black,
                        modifier = Modifier.size(30.dp)
                    )
                }

            }

            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(12.dp)
            ) {
                Column(modifier = Modifier) {
                    Column(modifier = Modifier) {
                        eventDetail?.let {
                            Text(
                                text = it.title,
                                style = TextStyle(
                                    fontSize = 18.sp,
                                    fontFamily = fontFamily,
                                    color = Color.Gray,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Date: 13-05-2024",
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontFamily = fontFamily,
                                color = Color.LightGray,
                                fontWeight = FontWeight.Normal
                            )
                        )
                        Text(
                            text = "Institution: ${eventDetail?.institute}",
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontFamily = fontFamily,
                                color = Color.LightGray,
                                fontWeight = FontWeight.Normal
                            )
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))


                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Button(
                            modifier = Modifier
                                .height(25.dp),
                            enabled = true,
                            onClick = {
//                                onRegisterClick()
                            },
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
                                                primary,
                                                secondary
                                            )
                                        ),
                                        shape = RoundedCornerShape(1.dp)
                                    ), contentAlignment = Alignment.Center
                            ) {
                                Row(
                                    modifier = Modifier,
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = "Register",
                                        style = TextStyle(
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    )
                                }
                            }
                        }

                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Column(
                        modifier = Modifier,
                        verticalArrangement = Arrangement.SpaceBetween,
                        horizontalAlignment = Alignment.Start
                    ) {
                        val string =
                            "Sports has been hallmark in Maharaja Aggersen Public School. In keeping with Prime Minister Modi's concern about Fit India, Healthy India, Maharaja Aggersen Public School organized the Inter-School Volleyball Tournament 2019-2020 for 2 days 21st September and 23rd September 2019. Many schools all across Delhi came for the Volleyball on Saturday, 21st September, 2019 and Monday, 23rd September 2019. The volleyball matches were played for both categories Sub Junior boys, Junior Boys, Sub Junior Girls and Junior Girls. After a short programme the Chief Guests were welcomed with gifts and saplings. The semifinal matches were played on 21st September and the final matches were played on 23rd September 2019. The winning teams were given gold, silver and bronze medallions and individual trophies by the Director of Sports. The players proudly displayed their hard-earned goodies and went homewards happy and satisfied."
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                text = string,
                                style = TextStyle(color = Color.Gray, textAlign = TextAlign.Justify)
                            )
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        PhotoView()

                        Spacer(modifier = Modifier.height(20.dp))

                        Row(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                text = "Location",
                                style = TextStyle(fontSize = 14.sp, color = Color.Gray)
                            )

                        }
                    }
                }
            }
        }


            }
        }
    }

    Log.d("event id check", "EventRegistration: $eventId")
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