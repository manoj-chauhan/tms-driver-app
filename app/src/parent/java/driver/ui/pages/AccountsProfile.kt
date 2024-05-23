package driver.ui.pages

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import driver.Destination
import driver.ui.viewmodels.AccountsProfileViewModel

@Composable
fun AccountsProfile(navigate: (Destination) -> Unit,navHostController: NavHostController) {
    val ap: AccountsProfileViewModel = hiltViewModel()
    LaunchedEffect (Unit){
        ap.getProfileList()
    }
    val profilesList by ap.profileList.collectAsStateWithLifecycle()

    Log.d("AccountsProfile", "profilesList: $profilesList")

    if(profilesList != null) {
        if (profilesList?.size == 0) {
            AddProfileScreen(navHostController)
        } else {
            MainScreen(
                navHostController,
                onCommentClick = {},
                onTripsClick = { /*TODO*/ },
                onEventsClick = { /*TODO*/ },
                onHomeClick = { /*TODO*/ },
                onNoticesClick = { /*TODO*/ }) {
            }
        }
    }
}