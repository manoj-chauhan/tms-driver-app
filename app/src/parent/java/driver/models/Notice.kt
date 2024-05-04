package driver.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Notice(
    val title:String,
    val description:String
)
