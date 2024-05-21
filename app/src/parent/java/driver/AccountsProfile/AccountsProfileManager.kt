package driver.AccountsProfile

import driver.models.AccountProfile

interface AccountsProfileManager {

    fun getAllProfile(): List<AccountProfile>?

    fun addProfile(
        name: String,
        role: String,
        anchor: String,
        mediaId:String,
        standard: String,
        section: String,
        session: String,
        instituteId: String,
        description: String,
        childClass:String,
        schoolName: String
    )
}