package driver.ui.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import driver.models.Event
import driver.models.getDummyEvents

@Composable
fun EventCard(event: Event, onRegisterClick: (event: Event) -> Unit) {

    val primary = Color(android.graphics.Color.parseColor("#6750a4"))
    val color = Color(android.graphics.Color.parseColor("#828282"))
    val school = Color(android.graphics.Color.parseColor("#a1a1a1"))
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
            Image(
                painter = painterResource(id = event.imageResId),
                contentDescription = "",
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Crop
            )

            Box(
                modifier = Modifier
                    .size(50.dp)
                    .padding(12.dp)
                    .align(Alignment.TopEnd)
            ) {
                Icon(
                    imageVector = Icons.Outlined.BookmarkBorder,
                    contentDescription = "Save",
                    tint = Color.DarkGray
                )
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
                    text = event.eventName,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = fontFamily,
                        color = color,
                        fontWeight = FontWeight.Normal
                    )
                )
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.fillMaxWidth(0.72f)) {
                        Text(
                            text = "${event.eventDate}",
                            style = TextStyle(
                                fontSize = 12.sp,
                                fontFamily = fontFamily,
                                color = color,
                                fontWeight = FontWeight.W400
                            )
                        )
                        Spacer(modifier = Modifier.height(3.dp))
                        Text(
                            text = event.institutionName,
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontFamily = fontFamily,
                                color = school,
                                fontWeight = FontWeight.W400
                            )
                        )
                    }

                    Button(
                        modifier = Modifier
                            .height(35.dp)
                            .width(90.dp),
                        enabled = true,
                        onClick = {
                            onRegisterClick(event)
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
                                            primary,
                                            primary
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
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(10.dp)
    )
}


@Composable
fun EventListPage(events: List<Event>, onRegisterClick: (event: Event) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
    ) {
        events.forEach { event ->
            EventCard(event, onRegisterClick)
        }
    }
}

@Composable
fun Eventpage(onRegisterClick: (Event) -> Unit) {
    val events: List<Event> = getDummyEvents()
    EventListPage(events = events, onRegisterClick = onRegisterClick)
}
