package com.drishto.driver.errormgmt

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract  class ErrorMgmtModule {
    @Binds
    abstract fun providesErrorManager(errorManage:ErrorMangerImpl):ErrManager
}