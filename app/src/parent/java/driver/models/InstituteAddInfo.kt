package driver.models

import android.location.Address
import androidx.compose.runtime.MutableState
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
    var department: String,
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


