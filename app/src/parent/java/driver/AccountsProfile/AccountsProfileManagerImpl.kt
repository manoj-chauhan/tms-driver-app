package driver.AccountsProfile

import driver.models.AccountProfile
import driver.network.AccountsProfileNetRespository
import javax.inject.Inject

class AccountsProfileManagerImpl @Inject constructor(
    private val accountNetRepository: AccountsProfileNetRespository,
): AccountsProfileManager {
    override fun getAllProfile(): List<AccountProfile>? {
        return accountNetRepository.fetchAccountProfile()
    }


}