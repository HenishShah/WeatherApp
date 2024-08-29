package com.example.weatherapp.di

import com.example.weatherapp.common.DataStoreManager
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface DataStoreEntryPoint {
    fun getDataStoreManager(): DataStoreManager
}