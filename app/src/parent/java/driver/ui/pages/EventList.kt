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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import java.time.format.DateTimeFormatter

@Composable
fun EventCard(event: Event, onRegisterClick:(event: Event) -> Unit) {

    val primary = Color(0xFF92A3FD)
    val secondary = Color(0XFF9DCEFF)

    val fontFamily = FontFamily.SansSerif

    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(10.dp)
            .shadow(elevation = (1).dp, RoundedCornerShape(2.dp)),
        content = {
            Column(modifier = Modifier.fillMaxSize()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()

                ) {
                    Image(
                        painter = painterResource(id = event.imageResId),
                        contentDescription = "",
                        modifier = Modifier.fillMaxWidth().height(200.dp),
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

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier
                        .fillMaxWidth(1f)
                        .padding(8.dp),) {
                        Text(
                            text = event.eventName,
                            style = TextStyle(fontSize = 16.sp, fontFamily = fontFamily, color = Color.Gray, fontWeight = FontWeight.Normal)
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically){
                            Column(modifier = Modifier.fillMaxWidth(0.7f)) {
                                Text(
                                    text = "${event.eventDate.format(DateTimeFormatter.ofPattern("dd MMM yyyy"))}",
                                    style = TextStyle(fontSize = 12.sp, fontFamily = fontFamily, color = Color.Gray, fontWeight = FontWeight.Normal)
                                )
                                Text(
                                    text = "${event.institutionName}",
                                    style = TextStyle(fontSize = 14.sp, fontFamily = fontFamily, color = Color.Gray, fontWeight = FontWeight.Normal)
                                )
                            }

                            Button(
                                modifier = Modifier
                                    .height(35.dp)
                                    .width(140.dp),
                                enabled = true,
                                onClick = {
                                    onRegisterClick(event)
                                },
                                contentPadding = PaddingValues(),
                                colors = ButtonDefaults.buttonColors(
                                    Color.Transparent
                                ),
                                shape = RoundedCornerShape(15.dp)
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

                    }
                }
            }

        }
    )
}




@Composable
fun EventListPage(events: List<Event>, onRegisterClick: (event: Event) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        events.forEach{event ->
            EventCard(event,  onRegisterClick)
        }
    }
}
@Composable
fun Eventpage(onRegisterClick: (Event) -> Unit){
    val events:List<Event> = getDummyEvents()
    EventListPage(events = events, onRegisterClick = onRegisterClick)
}
