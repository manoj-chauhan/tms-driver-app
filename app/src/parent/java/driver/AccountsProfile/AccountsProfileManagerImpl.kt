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

    override fun addProfile(
        name: String,
        role: String,
        anchor: String,
        standard: String,
        section: String,
        session: String,
        instituteId: String,
        description: String,
        childClass:String,
        schoolName: String
    ) {
        return accountNetRepository.addProfile(role, name, anchor, standard, section, session, instituteId, description,childClass, schoolName)
    }


}