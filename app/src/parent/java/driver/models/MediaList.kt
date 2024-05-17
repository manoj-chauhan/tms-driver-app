package driver.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MediaList (
    val type:String?,
    val mediaUrl:String,
    val caption: String?
)
