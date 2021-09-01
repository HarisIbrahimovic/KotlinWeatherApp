package com.example.weather.utill

import com.example.weather.model.CurrentWeather
import com.example.weather.model.UpcomingWeather

sealed class Resource<T>(val data:T?,val message:String?) {
    class Success<T>(data:T):Resource<T>(data,null)
    class Error<T>(message: String):Resource<T>(null,message)
}

sealed class CurrentWeatherEvent{
    class Success(val currentWeather: CurrentWeather):CurrentWeatherEvent()
    class Failure(val errorMessage: String):CurrentWeatherEvent()
    object Loading : CurrentWeatherEvent()
}

sealed class UpcomingWeatherEvent{
    class Success(val upcomingWeather: UpcomingWeather):UpcomingWeatherEvent()
    class Failure(val errorMessage: String):UpcomingWeatherEvent()
    object Loading : UpcomingWeatherEvent()
}
