package com.example.weatherapp.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.weatherapp.model.UserModel
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Upsert
    suspend fun insert(userModel: UserModel)

    @Query("SELECT * FROM user_table")
    fun getUserList(): Flow<List<UserModel>>

    @Delete
    suspend fun delete(userModel: UserModel)
}