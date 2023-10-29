package com.samrish.driver.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface MatrixRepository {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocation(vararg location: Matrix)

    @Query("SELECT * from matrix")
    suspend fun loadMatrices(): List<Matrix>

}

