package driver.ui.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import driver.models.Event

import java.time.format.DateTimeFormatter
import androidx.compose.material3.*
import androidx.compose.ui.graphics.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.tooling.preview.Preview
import com.drishto.driver.R
import driver.models.getDummyEvents
import java.time.LocalDate

@Composable
fun EventCard(event: Event, onRegisterClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.LightGray),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {

        Column() {
            Box(modifier= Modifier
                .fillMaxWidth()
               ){
                Image(
                    painter = painterResource(id = event.imageResId),
                    contentDescription = "Event Image",
                    modifier = Modifier
                        .fillMaxWidth()

//                        .height(200.dp),
                )

            }

            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(modifier = Modifier.fillMaxWidth(0.6f) , verticalAlignment = Alignment.CenterVertically) {


                    Column(modifier = Modifier.fillMaxWidth()) {

                        Text(
                            text = event.eventName,
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            text = "Date: ${event.eventDate.format(DateTimeFormatter.ofPattern("dd MMM yyyy"))}",
                            style = MaterialTheme.typography.bodySmall
                        )
                        Text(
                            text = "Institution: ${event.institutionName}",
                            style = MaterialTheme.typography.bodySmall
                        )

                    }
                }
//                Spacer(modifier = Modifier.width(10.dp))
                Row( verticalAlignment = Alignment.CenterVertically){
                    Button(onRegisterClick,
                       )
                         {
                        Text("Register")
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
        verticalArrangement = Arrangement.spacedBy(8.dp)
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

@Preview(showBackground = true)
@Composable
fun AddEventPagePreview() {
    val sampleEvents = listOf(
        Event(
            imageResId = R.drawable.volley,
            eventName = "Event One",
            eventDate = LocalDate.now(),
            institutionName = "Institution One"
        ),
        Event(
            imageResId = R.drawable.cricket,
            eventName = "Event Two",
            eventDate = LocalDate.now().plusDays(7),
            institutionName = "Institution Two"
        )
    )

    AddEventPage(
        events = sampleEvents,
        onRegisterClick = { event -> }
    )
}
