package com.example.weatherapp.di.module

import com.example.weatherapp.domain.UserRepository
import com.example.weatherapp.domain.WeatherRepository
import com.example.weatherapp.repository.UserRepositoryImpl
import com.example.weatherapp.repository.WeatherRepositoryImpl
import com.example.weatherapp.retrofit.ApiService
import com.example.weatherapp.room.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideWeatherRepository(apiService: ApiService): WeatherRepository {
        return WeatherRepositoryImpl(apiService)
    }

    @Provides
    fun provideUserRepository(userDao: UserDao): UserRepository {
        return UserRepositoryImpl(userDao)
    }
}