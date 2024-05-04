package driver.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Events(
    val institute: String

)
