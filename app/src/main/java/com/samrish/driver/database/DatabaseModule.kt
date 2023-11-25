package com.samrish.driver.database

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    fun providesDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "drishto3").build()
    }

    @Provides
    fun providesTripRepository(appDatabase: AppDatabase): TripRepository {
        return appDatabase.tripRepository()
    }

    @Provides
    fun providesUserRepository(appDatabase: AppDatabase): UserRepository {
        return appDatabase.userRepository()
    }

    @Provides
    fun providesMatrixRepository(appDatabase: AppDatabase): TelemetryRepository {
        return appDatabase.telemetryRepository()
    }


}