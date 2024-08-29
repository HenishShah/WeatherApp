package com.example.weatherapp.repository

import com.example.weatherapp.domain.UserRepository
import com.example.weatherapp.model.UserModel
import com.example.weatherapp.room.UserDao
import kotlinx.coroutines.flow.Flow

class UserRepositoryImpl(private val userDao: UserDao) : UserRepository {

    override suspend fun insertUser(userModel: UserModel) {
        userDao.insert(userModel)
    }

    override suspend fun deleteUser(userModel: UserModel) {
        userDao.delete(userModel)
    }

    override suspend fun getAllUser(): Flow<List<UserModel>> {
        return userDao.getUserList()
    }
}