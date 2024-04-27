package driver.postActionManagement

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class PostActionManagerModule {
    @Binds
    abstract fun providesPostActionsModule(postActionManagerImpl: PostActionManagerImpl): PostActionManager
}