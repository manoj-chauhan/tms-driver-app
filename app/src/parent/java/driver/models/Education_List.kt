package driver.models

import com.squareup.moshi.JsonClass
@JsonClass(generateAdapter = true)
class EducationList (
    val name: String,
    val standard: String,
    val guardianName: String,
    val dateOfBirth: String,
    val gender: String,
    val schoolName: String,
    val schoolAddress: String,
    val primaryPhoneNumber: String,
    val secondaryPhoneNumber: String?,
    val status: String,
    val createdBy: String,
    val createdAt: String,
    val updatedBy: String,
    val lastUpdatedAt: String
)


    @JsonClass(generateAdapter = true)
    data class ProfileImage(
        val image: String
    )
