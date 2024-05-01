package driver.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PostUpload(
    val mediaId: String,
    val type: String,
    val caption:String=""
)

@JsonClass(generateAdapter = true)
data class UploadPosts(
    val createdBy: String,
    val media: List<PostUpload?>,
    val message:String,
    val scope : String = "School",
)

