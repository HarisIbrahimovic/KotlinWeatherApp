package com.example.weather.api

import com.example.weather.model.CurrentWeather
import com.example.weather.model.UpcomingWeather
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/data/2.5/weather/")
    suspend fun getCurrentWeather(
        @Query("q")q:String,
        @Query("appid")appid:String
    ):Response<CurrentWeather>

    @GET("/data/2.5/onecall")
    suspend fun getUpcomingWeather(
        @Query("lat")lat:String,
        @Query("lon")lon:String,
        @Query("exclude")exclude:String,
        @Query("appid")appid:String
    ):Response<UpcomingWeather>

}