package com.samrish.driver.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class, Matrix::class, Trip::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userRepository(): UserRepository
    abstract fun matrixRepository(): MatrixRepository
    abstract fun tripRepository(): TripRepository

    companion object {
        private var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE =
                        Room.databaseBuilder(context, AppDatabase::class.java, "drishto")
                            .build()
                }
            }
            return INSTANCE!!
        }
    }
}
