package driver.tripManagement

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ParentTripManagerModule {
    @Binds
    abstract fun providesTripManager(parentTripManager: ParentTripManagerImpl): ParentTripManager
}