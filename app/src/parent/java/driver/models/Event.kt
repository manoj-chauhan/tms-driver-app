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
    val location: Location?,
    val scope: Scope,
    val dateOfEvent: String?,
    val timeOfEvent: String?,
    val coverImage: Image?,
    val descriptionImage: List<Image>?,
    val active: Boolean,
    val createdBy: String
)

@JsonClass(generateAdapter = true)
data class Location(
    val address:String?,
    val city :String?,
    val state:String?,
    val locations: GeoCordinates?
)

@JsonClass(generateAdapter = true)
data class Scope(
    val type: String,
    val targetClasses: List<String>? = null
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




