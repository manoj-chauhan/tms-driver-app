package driver.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import driver.AccountsProfile.AccountsProfileManager
import driver.models.AccountProfile
import driver.postUploadManagement.PostUploadManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountsProfileViewModel @Inject constructor(
    private val accountsProfileManager: AccountsProfileManager,
    private val postsUploadManager: PostUploadManager
) : ViewModel() {

    private val _profilesList: MutableStateFlow<List<AccountProfile>?> = MutableStateFlow(null)
    val profileList: StateFlow<List<AccountProfile>?> = _profilesList.asStateFlow()

    private val _uploadPhoto: MutableStateFlow<String?> = MutableStateFlow(null)
    val photoDetails: StateFlow<String?> = _uploadPhoto.asStateFlow()


    fun getProfileList() {
        viewModelScope.launch(Dispatchers.IO) {
            val profiles = accountsProfileManager.getAllProfile()
            _profilesList.update { _ ->
                profiles
            }
        }
    }

    fun addProfile(
        name: String,
        role: String,
        anchor: String,
        mediaId: String,
        selectedPlace: String,
        city: String,
        state: String,
        markerPosition: LatLng?
    ) {

        try {
            CoroutineScope(Dispatchers.IO).launch {
                accountsProfileManager.addProfile(name, role, anchor, mediaId, selectedPlace, city, state, markerPosition)
            }
        } catch (e: Exception) {
            null
        }

    }

    fun uploadPhoto(image: ByteArray?, mimeType: String?){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                if (image != null  && mimeType != null) {
                    val postResponse = postsUploadManager.uploadPosts(image, mimeType)
                    _uploadPhoto.update {
                        postResponse
                    }
                }
            } catch (e: Exception) {
                null
            }
        }
    }

}