package driver.ui.pages

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BookmarkAdd
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
fun EventCard(event: Event, onRegisterClick: () -> Unit) {

    val primary = Color(0xFF92A3FD)
    val secondary = Color(0XFF9DCEFF)

    val fontFamily = FontFamily.SansSerif

    Card(
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, Color.LightGray),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(8.dp)
    ) {
        Column(modifier = Modifier.fillMaxHeight()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                Image(
                    painter = painterResource(id = event.imageResId),
                    contentDescription = "",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .padding(8.dp)
                        .align(Alignment.TopEnd)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.BookmarkAdd,
                        contentDescription = "Save",
                        tint = Color.White
                    )
                }

            }

            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.fillMaxWidth(0.73f)) {
                    Text(
                        text = event.eventName,
                        style = TextStyle(fontSize = 14.sp, fontFamily = fontFamily, color = Color.LightGray, fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = "Date: ${event.eventDate.format(DateTimeFormatter.ofPattern("dd MMM yyyy"))}",
                        style = TextStyle(fontSize = 12.sp, fontFamily = fontFamily, color = Color.LightGray, fontWeight = FontWeight.Normal)
                    )
                    Text(
                        text = "Institution: ${event.institutionName}",
                        style = TextStyle(fontSize = 14.sp, fontFamily = fontFamily, color = Color.LightGray, fontWeight = FontWeight.Normal)
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))
                Button(
                    modifier = Modifier
                        .height(25.dp),
                    enabled = true,
                    onClick = {
                        onRegisterClick()
                    },
                    contentPadding = PaddingValues(),
                    colors = ButtonDefaults.buttonColors(
                        Color.Transparent
                    ),
                    shape = RoundedCornerShape(10.dp)
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




@Composable
fun AddEventPage(events: List<Event>, onRegisterClick: (Event) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
    ) {
        items(events) { event ->
            EventCard(event) { onRegisterClick(event) }
        }
    }
}
@Composable
fun Eventpage(){
    val events:List<Event> = getDummyEvents()
    AddEventPage(events = events) {

    }
}
