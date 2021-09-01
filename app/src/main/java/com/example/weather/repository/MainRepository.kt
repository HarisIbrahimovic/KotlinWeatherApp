package com.example.weather.repository

import com.example.weather.api.ApiService
import com.example.weather.model.CurrentWeather
import com.example.weather.model.UpcomingWeather
import com.example.weather.utill.Constants
import com.example.weather.utill.Resource
import java.lang.Exception
import javax.inject.Inject

class MainRepository @Inject constructor(private val apiService: ApiService){

    suspend fun getWeather(cityName : String):Resource<CurrentWeather> {
        return  try {
            val response = apiService.getCurrentWeather(cityName,Constants.API_KEY)
            val result = response.body()
            if(response.isSuccessful&&result!=null){
                Resource.Success(result)
            }else Resource.Error("Unexpected error occurred.")
        }catch (e:Exception){
            Resource.Error("Network error occurred.")
        }
    }

    suspend fun getUpcomingWeather(latitude: String,longitude: String):Resource<UpcomingWeather>{
        return try {
            val response = apiService.getUpcomingWeather(latitude,longitude,"minutely", Constants.API_KEY)
            val result = response.body()
            if(response.isSuccessful&&result!=null){
                Resource.Success(result)
            }else Resource.Error("Unexpected error occurred.")
        }catch (e:Exception){
            Resource.Error("Network Error occurred.")
        }
    }

}