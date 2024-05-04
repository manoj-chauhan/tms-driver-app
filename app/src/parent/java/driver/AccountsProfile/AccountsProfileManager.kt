package driver.AccountsProfile

import driver.models.AccountProfile

interface AccountsProfileManager {

    fun getAllProfile():List<AccountProfile>?
}