package com.jnda.openweatherapp.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.jnda.openweatherapp.storage.entity.UserEntity

@Dao
interface UserDAO {
    @Query("SELECT * FROM ${UserEntity.TABLE_NAME} WHERE username = :username")
    suspend fun findByUsername(username: String) : UserEntity

    @Query("SELECT * FROM ${UserEntity.TABLE_NAME} WHERE username = :username AND " +
            "user_pwd = :password")
    suspend fun findByAccount(username: String, password: String) : UserEntity?

    @Insert
    suspend fun insert(user: UserEntity): Long
}