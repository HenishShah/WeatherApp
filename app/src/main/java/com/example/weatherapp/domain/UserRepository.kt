package com.example.weatherapp.domain

import com.example.weatherapp.model.UserModel
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun insertUser(userModel: UserModel)
    suspend fun deleteUser(userModel: UserModel)
    suspend fun getAllUser(): Flow<List<UserModel>>
}