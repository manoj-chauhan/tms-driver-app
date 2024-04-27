package driver.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Comment(
    val content:String
)
