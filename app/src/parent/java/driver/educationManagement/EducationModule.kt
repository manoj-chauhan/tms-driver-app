package driver.educationManagement

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ExampleModule {

    @Binds
    abstract fun bindEducationManager(educationManager: EducationImpl): EducationManager


}







