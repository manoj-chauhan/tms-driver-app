package driver.instituteManagement

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class ExampleModule {

    @Binds
    abstract fun InstitueManager(InstituteManager: InstituteImpl): InstitueManager


}
