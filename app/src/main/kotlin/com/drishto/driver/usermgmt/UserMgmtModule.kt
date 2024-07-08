package com.drishto.driver.usermgmt

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UserMgmtModule {
        @Binds
        abstract fun providesUserManagement(userManager: UserManagerImpl): UserManager
}