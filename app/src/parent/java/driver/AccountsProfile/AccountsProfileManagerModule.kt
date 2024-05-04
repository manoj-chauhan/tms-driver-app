package driver.AccountsProfile

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AccountsProfileManagerModule {

    @Binds
    abstract fun bindEducationManager(accountsManager: AccountsProfileManagerImpl): AccountsProfileManager

}