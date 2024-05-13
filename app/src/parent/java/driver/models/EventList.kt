package driver.models

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
    val coverImage: Image?,
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
    val targetClasses: List<String>?,
    val targetUsers:List<String>?
)

@JsonClass(generateAdapter = true)
data class Image(
    val type: String,
    val mediaId: String,
    val caption: String?
)



