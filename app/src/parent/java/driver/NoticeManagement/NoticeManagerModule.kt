package driver.NoticeManagement

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import driver.NoticeManagement.NoticeManager

@Module
@InstallIn(SingletonComponent::class)
abstract class ExampleModule {

    @Binds
    abstract fun NoticeManager(NoticeManager: NoticeManagerImpl): NoticeManager


}
