package driver.EventManagement

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class EventManagerModule {
    @Binds
    abstract fun providesEventManager(eventManager: EventManagerImpl): EventManager
}