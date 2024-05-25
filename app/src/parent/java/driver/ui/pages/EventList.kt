package driver.ui.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import driver.Destination
import driver.buttonColor
import driver.headingColor
import driver.models.Event
import driver.subHeadingColor
import driver.textColor
import driver.ui.viewmodels.EventsViewModel

@Composable
fun EventCard(event: Event, onRegisterClick: (event: String) -> Unit, navigation: NavHostController) {
    val fontFamily = FontFamily.SansSerif


    ElevatedCard(
        colors = CardDefaults.cardColors(Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .shadow(3.dp, RoundedCornerShape(12.dp)),
    ) {
        Box(
            modifier = Modifier
                .height(150.dp)
        ) {


            event.coverImage?.let { coverImage ->
                AsyncImage(

                    model = coverImage.mediaUrl,
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )
            }
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .padding(12.dp)
                    .align(Alignment.TopEnd)
            ) {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .background(Color.Transparent)

//                        .shadow(5.dp, CircleShape)
                ) {

                    Icon(
                        imageVector = Icons.Outlined.BookmarkBorder,
                        contentDescription = "Save",
                        tint = Color.Black,
                        modifier = Modifier
                            .size(46.dp)
                            .align(Alignment.Center)
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(Color.White.copy(alpha = 0.7f), Color.White),
                                    startY = 0f,
                                    endY = 1f
                                ),
                                shape = CircleShape
                            )
                    )
                }
            }

        }
        Spacer(modifier = Modifier.height(6.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(1f),
            ) {
                Text(
                    text = event.title,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = fontFamily,

                        color = headingColor,
                        fontWeight = FontWeight.Normal
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.fillMaxWidth(0.72f)) {
                        Text(
                            text = "${event.dateOfEvent}",
                            style = TextStyle(
                                fontSize = 12.sp,
                                fontFamily = fontFamily,
                                color = subHeadingColor,
                                fontWeight = FontWeight.W400
                            )
                        )
                        Spacer(modifier = Modifier.height(3.dp))
                        Text(
                            text = event.instituteName,
                            modifier = Modifier.clickable {navigation.navigate(Destination.SchoolProfile)} ,

                            style = TextStyle(
                                fontSize = 14.sp,
                                fontFamily = fontFamily,
                                color = textColor,
                                fontWeight = FontWeight.W400
                            )
                        )
                    }

                    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.End) {
                        Button(
                            modifier = Modifier
                                .height(35.dp)
                                .width(90.dp),
                            enabled = true,
                            onClick = {
                                onRegisterClick(event.id)
                            },
                            contentPadding = PaddingValues(),
                            colors = ButtonDefaults.buttonColors(
                                Color.Transparent
                            ),
                            shape = RoundedCornerShape(20.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .heightIn(35.dp)
                                    .align(Alignment.Bottom)
                                    .background(
                                        brush = Brush.horizontalGradient(
                                            listOf(
                                                buttonColor,
                                                buttonColor
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
                }
            }
        }
    }
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(10.dp)
    )
}


@Composable
fun EventListPage(
    events: List<Event>,
    onRegisterClick: (event: String) -> Unit,
    navigation: NavHostController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
    ) {
        events.forEach { event ->
            EventCard(event, onRegisterClick , navigation)
        }
        
        Spacer(modifier = Modifier.height(60.dp))
    }
}

@Composable
fun Eventpage(
    navigation: NavHostController,
    onRegisterClick: (String) -> Unit,
    onAddEventClick: () -> Unit
) {
    val eventsViewModel: EventsViewModel = hiltViewModel()
    val events by eventsViewModel.eventList.collectAsStateWithLifecycle()

    eventsViewModel.getAllEvents()

    events?.let {
        EventListPage(events = it, onRegisterClick = onRegisterClick, navigation = navigation)
    }
}


