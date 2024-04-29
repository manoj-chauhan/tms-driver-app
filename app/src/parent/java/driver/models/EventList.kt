package driver.models

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.drishto.driver.R
import java.time.LocalDate

data class Event(
    @DrawableRes val imageResId: Int,
    val eventName: String,
    val eventDate: LocalDate,
    val institutionName: String
)


fun getDummyEvents(): List<Event> {
    return listOf(
        Event(
            imageResId = R.drawable.volley,
            eventName = "Inter School Volley Tournament",
            eventDate = LocalDate.of(2024, 12, 10),
            institutionName = "Greenwood High"
        ),
        Event(
            imageResId = R.drawable.chess,
            eventName = "Inter School Chess Tournament",
            eventDate = LocalDate.of(2024, 11, 15),
            institutionName = "Maplewood Academy"
        ),
        Event(
            imageResId = R.drawable.cricket,
            eventName = "Inter School Cricket Tournament",
            eventDate = LocalDate.of(2024, 10, 20),
            institutionName = "Lakeside School"
        ),
        Event(
            imageResId = R.drawable.tt,
            eventName = "Inter School Table Tennis Tournament",
            eventDate = LocalDate.of(2024, 9, 25),
            institutionName = "Riverdale Institute"
        )
    )
}


@Composable
fun getImagePainter(@DrawableRes imageResId: Int): Painter {
    return painterResource(id = imageResId)
}