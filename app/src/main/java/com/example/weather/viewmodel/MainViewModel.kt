package com.example.weather.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.repository.MainRepository
import com.example.weather.utill.CurrentWeatherEvent
import com.example.weather.utill.Resource
import com.example.weather.utill.UpcomingWeatherEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val mainRepository: MainRepository) :ViewModel() {
    private val _currentWeather = MutableLiveData<CurrentWeatherEvent>()
    private val _upcomingWeather = MutableLiveData<UpcomingWeatherEvent>()
    val currentWeather: LiveData<CurrentWeatherEvent> get() =_currentWeather
    val upcomingWeather: LiveData<UpcomingWeatherEvent> = _upcomingWeather

    private fun getUpcomingWeather(latitude: String, longitude : String) =viewModelScope.launch {
        _upcomingWeather.value = UpcomingWeatherEvent.Loading
        when(val response = mainRepository.getUpcomingWeather(latitude,longitude)){
            is Resource.Error-> _upcomingWeather.value= UpcomingWeatherEvent.Failure(response.message!!)
            is Resource.Success-> _upcomingWeather.value= UpcomingWeatherEvent.Success(response.data!!)
        }
    }

    private fun getWeather(cityName:String) = viewModelScope.launch {
        _currentWeather.value= CurrentWeatherEvent.Loading
        when(val response = mainRepository.getWeather(cityName)){
            is Resource.Error-> _currentWeather.value= CurrentWeatherEvent.Failure(response.message!!)
            is Resource.Success-> _currentWeather.value= CurrentWeatherEvent.Success(response.data!!)
        }
    }

    fun setValues(cityName: String, latitude: String, longitude : String) {
        if(currentWeather.value!=null&&upcomingWeather.value!=null)
            return
        getWeather(cityName)
        getUpcomingWeather(latitude, longitude)
    }


}