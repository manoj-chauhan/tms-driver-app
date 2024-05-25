package driver.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserProfile(
    val name: String,
    val type: String,
    val anchor: String,
    val displayPicture: String,
    val address: Address
)

@JsonClass(generateAdapter = true)
data class Address(
    val city: String?,
    val state: String?,
    val address: String? = null,
    val locations: GeoCordinates? = null
)

