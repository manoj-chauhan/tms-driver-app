package driver.ui.pages

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.drishto.driver.network.getProfileId
import com.drishto.driver.network.saveProfileId
import driver.ui.viewmodels.AccountsProfileViewModel

@Composable
fun AccountsProfile(onProfileSelected: (profile: String) -> Unit) {
    val ap: AccountsProfileViewModel = hiltViewModel()
    ap.getProfileList()

    val profilesList by ap.profileList.collectAsStateWithLifecycle()
    Log.d("{Profile}", "AccountsProfile:$profilesList ")
    val context = LocalContext.current

    if (getProfileId(context) == null) {
        profilesList?.firstOrNull()?.let { profile ->
            saveProfileId(context, profile.id)
            onProfileSelected(profile.id)
            Log.d("TAG", "AccountsProfile $profile")
        }
    }else{
        val profile = getProfileId(context)
        if (profile != null) {
            onProfileSelected(profile)
        }
    }
}