package driver.ui.pages

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import driver.ui.viewmodels.AccountsProfileViewModel

@Composable
fun AccountsProfile(navController: NavHostController,onProfileSelected: (profile: String) -> Unit) {
    val ap: AccountsProfileViewModel = hiltViewModel()
    ap.getProfileList()

    val profilesList by ap.profileList.collectAsStateWithLifecycle()
    if(profilesList != null) {
        if (profilesList?.size == 0) {
            navController.navigate("add-Profile")
        } else {
            navController.navigate("newHomeScreen")
        }
    }else{
        Log.d("TAG", "AccountsProfile: $profilesList")
    }
}