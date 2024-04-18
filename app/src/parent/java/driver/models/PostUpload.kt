package driver.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PostUpload(
    val mediaId: String,
    val type: String = "Image",
    val caption:String=""
)