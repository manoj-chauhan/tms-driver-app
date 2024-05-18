package driver.models


import com.squareup.moshi.JsonClass
@JsonClass(generateAdapter = true)
data class Notice_List(
    val id: String,
    val institute: String,
    val instituteName: String,
    val instituteDp: String,
    val title: String,
    val description: String,
    val scope: Scope,
    val dateOfNotice: String,
    val timeOfNotice: String,
    val media: Media?,
    val createdBy: String,
    val active: Boolean
)



@JsonClass(generateAdapter = true)
data class Media(
    val type: String?,
    val mediaUrl: String,
    val caption: String?
)
