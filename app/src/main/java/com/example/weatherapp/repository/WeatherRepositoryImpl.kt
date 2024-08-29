package com.example.weatherapp.repository

import android.util.Log
import com.example.weatherapp.domain.WeatherRepository
import com.example.weatherapp.model.WeatherModel
import com.example.weatherapp.retrofit.ApiService
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

class WeatherRepositoryImpl(private val apiService: ApiService):
    WeatherRepository {
    override suspend fun getWeatherDetails(): Result<WeatherModel> {
        return try {
            val apiResponse = apiService.getData()
            if (apiResponse.isSuccessful) {
                val response = apiResponse.body()!!

                val jsonObject = JSONObject(response)
                val current = jsonObject.getJSONObject("current")
                val weather = current.getJSONArray("weather").getJSONObject(0)
                val weatherModel = WeatherModel(
                    temp = current.getString("temp"),
                    weatherType = weather.getString("main"),
                    humidity = current.getString("humidity"),
                    windSpeed = current.getString("wind_speed")
                )

                Result.success(weatherModel)
            } else {
                Result.failure(Throwable("Failed to load weather details"))
            }
        } catch (e: Exception) {
            Log.e("WeatherRepositoryImpl", "Exception - ${e.message}")
            Result.failure(Throwable("Failed to load weather details"))
        }
    }
}