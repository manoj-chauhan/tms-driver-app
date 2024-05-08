package driver.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PostsFeed(
    val id: String,
    val createdBy: String?,
    val likes: Int?,
    val comments: Int?,
    val scope: PostScope,
    val shares: String?,
    val message: String?,
    val media: List<MediaList>,
    val likedStatus: Boolean
)

@JsonClass(generateAdapter = true)
data class PostScope(
    val type: String,
    val targetClasses: List<String>?,
    val targetUsers: List<String>?
)
