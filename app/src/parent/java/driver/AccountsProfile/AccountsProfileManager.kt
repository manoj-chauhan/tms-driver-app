package driver.AccountsProfile

import com.google.android.gms.maps.model.LatLng
import driver.models.AccountProfile

interface AccountsProfileManager {

    fun getAllProfile(): List<AccountProfile>?

    fun addProfile(
        name: String,
        role: String,
        anchor: String,
        mediaId: String,
        selectedPlace: String,
        city: String,
        state: String,
        markerPosition: LatLng?
    )
}