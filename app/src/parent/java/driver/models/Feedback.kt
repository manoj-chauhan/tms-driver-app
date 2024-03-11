package driver.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)

data class Feedback (
    var userId: Int,
    var feedback: String
)