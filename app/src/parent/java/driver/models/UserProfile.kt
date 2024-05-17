package driver.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserProfile(
    val name: String,
    val type: String,
    val anchor: String,
    val studentDetails: StudentDetails? = null,
    val parentDetails: TeacherDetails?= null,
    val schoolDetails: SchoolDetails? = null
)

@JsonClass(generateAdapter = true)
data class StudentDetails(
    val standard: String,
    val section: String,
    val session: String,
    val instituteId: String
)

@JsonClass(generateAdapter = true)
data class TeacherDetails(
    val description: String,
    val institute: InstituteInfo
)
@JsonClass(generateAdapter = true)
data class InstituteInfo (
    val currentInstitute: String,
    val session:String
)

@JsonClass(generateAdapter = true)
data class SchoolDetails(
    val schoolName: String
)
