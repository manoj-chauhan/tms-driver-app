package com.samrish.driver.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import java.time.LocalDateTime

@Dao
interface TelemetryRepository {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocation(vararg telemetry: Telemetry)

    @Query("SELECT * from telemetry")
    suspend fun loadMatrices(): List<Telemetry>

    @Query("UPDATE telemetry SET DataLoaded = :isDataLoaded WHERE id = :telemetryId")
    fun updateTelemetryStatus(telemetryId: Long, isDataLoaded: Boolean)

    @Query("SELECT id FROM telemetry WHERE latitude = :latitude AND longitude = :longitude AND DateTime = :time")
    fun getTelemetryId(latitude: Double, longitude: Double, time: LocalDateTime): Long


    @Query("SELECT * FROM telemetry WHERE DataLoaded  = :isDataLoaded")
    fun getTelemetryWithFalseStatus(isDataLoaded: Boolean): List<Telemetry>


    @Query("SELECT * FROM telemetry WHERE DataLoaded = :isDataLoaded ORDER BY DateTime ASC LIMIT 1")
    fun getOldestTelemetryWithFalseStatus(isDataLoaded: Boolean): Telemetry?

    @Query("DELETE FROM telemetry")
    fun deleteAllTelemetry()
}

