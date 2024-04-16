package driver.postUploadManagement

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class PostUploadManagerModule {

    @Binds
    abstract fun providesPostUploadModule(parentTripManager: PostUploadManagerImpl): PostUploadManager
}