package com.samrish.driver.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserRepository {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(vararg users: User)

    @Update
    suspend fun updateUsers(vararg users: User)

    @Delete
    suspend fun deleteUsers(vararg users: User)

    @Query("SELECT * FROM user WHERE uid = :uid")
    suspend fun loadUserById(uid: Int): User

    @Query("SELECT * from user")
    suspend fun loadUsers(): List<User>
}

@Dao
interface MatrixRepository {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocation(vararg location: Matrix)

    @Query("SELECT * from matrix")
    suspend fun loadMatrices(): List<Matrix>

}

