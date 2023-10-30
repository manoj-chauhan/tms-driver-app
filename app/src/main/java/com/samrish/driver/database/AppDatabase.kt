package com.samrish.driver.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [User::class, Telemetry::class, Trip::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userRepository(): UserRepository
    abstract fun telemetryRepository(): TelemetryRepository
    abstract fun tripRepository(): TripRepository
}
