package driver.models
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Event(
    val id: String,
    val instituteName: String,
    val institute: String,
    val instituteDp: String,
    val title: String,
    val description: String,
    val location: Location,
    val scope: Scope,
    val dateOfEvent: String?,
    val timeOfEvent: String?,
    val coverImage: Image?,
    val descriptionImage: List<String>,
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
)

@JsonClass(generateAdapter = true)
data class Image(
    val type: String?,
    val mediaUrl: String,
    val caption: String?
)

@JsonClass(generateAdapter = true)
data class EventRegistration(
    val id: String,
    val institute: String,
    val instituteName: String,
    val instituteDp: String,
    val title: String,
    val description: String,
    val location: String,
    val scope: String,
    val dateOfEvent: String?,
    val timeOfEvent: String?,
    val coverImage: ImagesInfo?,
    val descriptionImage: List<ImagesInfo?>,
    val active: Boolean,
    val createdBy: String
)

@JsonClass(generateAdapter = true)
data class ImagesInfo(
    val type: String?,
    val mediaId: String,
    val caption: String?
)




