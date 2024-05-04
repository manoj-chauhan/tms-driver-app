package driver.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import driver.AccountsProfile.AccountsProfileManager
import driver.models.AccountProfile
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
) : ViewModel() {

    private val _profilesList: MutableStateFlow<List<AccountProfile>?> = MutableStateFlow(null)
    val profileList: StateFlow<List<AccountProfile>?> = _profilesList.asStateFlow()

    fun getProfileList() {
        viewModelScope.launch(Dispatchers.IO) {
            val profiles = accountsProfileManager.getAllProfile()
            Log.d("checking1", "$profiles")
            _profilesList.update { _ ->
                profiles
            }

        }


    }

}