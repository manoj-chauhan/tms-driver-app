package com.samrish.driver.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TelemetryRepository {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocation(vararg telemetry: Telemetry)

    @Query("SELECT * from telemetry")
    suspend fun loadMatrices(): List<Telemetry>

}

