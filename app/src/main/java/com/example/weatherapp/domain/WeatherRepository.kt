package com.example.weatherapp.domain

import com.example.weatherapp.model.WeatherModel

interface WeatherRepository {
    suspend fun getWeatherDetails(): Result<WeatherModel>
}