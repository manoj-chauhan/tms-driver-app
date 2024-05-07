package driver.models

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.drishto.driver.R

data class Event(
    @DrawableRes val imageResId: Int,
    val eventName: String,
    val eventDate: String,
    val institutionName: String
)


fun getDummyEvents(): List<Event> {
    return listOf(
        Event(
            imageResId = R.drawable.volley,
            eventName = "Inter School Volley Tournament",
            eventDate = "12 jul 2023",
            institutionName = "Greenwood High"
        ),
        Event(
            imageResId = R.drawable.chess,
            eventName = "Inter School Chess Tournament",
            eventDate = "12 jul 2023",
            institutionName = "Maplewood Academy"
        ),
        Event(
            imageResId = R.drawable.cricket,
            eventName = "Inter School Cricket Tournament",
            eventDate = "12 jul 2023",
            institutionName = "Lakeside School"
        ),
        Event(
            imageResId = R.drawable.tt,
            eventName = "Inter School Table Tennis Tournament",
            eventDate = "12 jul 2023",
            institutionName = "Riverdale Institute"
        )
    )
}


@Composable
fun getImagePainter(@DrawableRes imageResId: Int): Painter {
    return painterResource(id = imageResId)
}