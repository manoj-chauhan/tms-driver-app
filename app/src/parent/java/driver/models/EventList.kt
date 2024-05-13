package driver.models

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.drishto.driver.R

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Event(
    val id: String,
    val institute: String,
    val title: String,
    val description: String,
    val location: Location,
    val scope: Scope,
    val dateOfEvent: String?,
    val timeOfEvent: String?,
    val coverImage: String?,
    val descriptionImage: String?,
    val active: Boolean,
    val createdBy: String
)

@JsonClass(generateAdapter = true)
data class Location(
    val location: String,
    val latitude: Double,
    val longitude: Double
)

@JsonClass(generateAdapter = true)
data class Scope(
    val type: String,
    val targetClasses: List<String>?
//    val targetUsers: List<String>?
)




