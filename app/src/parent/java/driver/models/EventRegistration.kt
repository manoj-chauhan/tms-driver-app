package driver.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EventRegistration(
    val title: String,
    val description: String,
    val location: String,
    val scope: String,
    val dateOfEvent: String?,
    val timeOfEvent:  String?,
    val coverImage:ImagesInfo?,
    val descriptionImage: List<ImagesInfo?>,
    val institute:  String
)

@JsonClass(generateAdapter = true)
data class locationInfo(

    val latitude:String,
    val longitude:String

)
@JsonClass(generateAdapter = true)
data class ImagesInfo(
    val type :  String,
    val mediaId :  String,
    val caption :String?

)
