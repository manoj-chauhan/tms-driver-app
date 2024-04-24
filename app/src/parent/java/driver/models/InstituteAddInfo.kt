package driver.models

import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class InstituteAddInfo(
    val name: String,
    val coverPicture:String?,
    val displayPicture:String?,
    val contacts: List<ContactList>,
    val description: String,
    val address: AddressInfo,
    val facilities: List<String>


    )

@JsonClass(generateAdapter = true)
data class ContactList(
    var description: String,
    var number: String
)

@JsonClass(generateAdapter = true)
data class AddressInfo(
    val address: String,
    val city: String,
    val state: String,
    val geocordinates: GeoCordinates


)

@JsonClass(generateAdapter = true)
data class GeoCordinates(
    val latitude: String,
    val longitude: String
)


data class AddInstituteSavedData(
    val instituteName: String,
    val description: String,
    val address: String,
    val city: String,
    val state: String,
    val contactEntries: List<ContactList>
)

