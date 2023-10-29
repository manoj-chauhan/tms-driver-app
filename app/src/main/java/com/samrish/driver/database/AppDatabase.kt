package com.samrish.driver.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [User::class, Matrix::class, Trip::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userRepository(): UserRepository
    abstract fun matrixRepository(): MatrixRepository
    abstract fun tripRepository(): TripRepository
}
