package com.drishto.driver.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface TripRepository {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrip(vararg trip: Trip)

    @Query("SELECT * from trip")
    suspend fun tripList(): List<Trip>

    @Delete
    suspend fun deleteTrip(vararg trip: Trip)

    @Query("DELETE FROM trip")
    suspend fun clearAllTrips()
}
