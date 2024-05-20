package driver.ui.pages

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import driver.Destination
import driver.ui.viewmodels.AccountsProfileViewModel

@Composable
fun AccountsProfile(navController: NavHostController,onProfileSelected: (profile: String) -> Unit) {
    val ap: AccountsProfileViewModel = hiltViewModel()
    ap.getProfileList()

    val profilesList by ap.profileList.collectAsStateWithLifecycle()
    if(profilesList != null) {
        if (profilesList?.size == 0) {
            navController.navigate(Destination.AddProfile)
        } else {
            navController.navigate(Destination.NewHomeScreen)
        }
    }else{
        Log.d("TAG", "AccountsProfile: $profilesList")
    }
}