package com.example.weatherapp.retrofit

import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("onecall?lat=12.9082847623315&lon=77.65197822993314&units=imperial&appid=b143bb707b2ee117e62649b358207d3e")
    suspend fun getData(): Response<String>
}