package driver.AccountsProfile

import com.google.android.gms.maps.model.LatLng
import driver.models.AccountProfile
import driver.network.AccountsProfileNetRespository
import javax.inject.Inject

class AccountsProfileManagerImpl @Inject constructor(
    private val accountNetRepository: AccountsProfileNetRespository,
): AccountsProfileManager {
    override fun getAllProfile(): List<AccountProfile>? {
        return accountNetRepository.fetchAccountProfile()
    }

    override fun addProfile(
        name: String,
        role: String,
        anchor: String,
        mediaId: String,
        selectedPlace: String,
        city: String,
        state: String,
        markerPosition: LatLng?
    ) {
        return accountNetRepository.addProfile(role, name, anchor, mediaId, selectedPlace, city, state, markerPosition)
    }


}