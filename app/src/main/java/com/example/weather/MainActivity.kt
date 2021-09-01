package com.example.weather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weather.adapters.DayAdapter
import com.example.weather.adapters.HourAdapter
import com.example.weather.databinding.ActivityMainBinding
import com.example.weather.model.CurrentWeather
import com.example.weather.model.UpcomingWeather
import com.example.weather.utill.CurrentWeatherEvent
import com.example.weather.utill.UpcomingWeatherEvent
import com.example.weather.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel : MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private lateinit var hourAdapter:HourAdapter
    private lateinit var dayAdapter: DayAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.navigationBarColor = ContextCompat.getColor(applicationContext,R.color.myDarkBlue)
        setUpObservers()

    }

    private fun setUpObservers() {
        viewModel.setValues("Tuzla","44.56","18.70")
        viewModel.currentWeather.observe(this, { currentWeatherEvent ->
            when (currentWeatherEvent) {
                is CurrentWeatherEvent.Success -> {
                    binding.progressBarCyclic.visibility = View.GONE
                    setUpView(currentWeatherEvent.currentWeather)
                }
                is CurrentWeatherEvent.Failure -> {
                    binding.progressBarCyclic.visibility = View.GONE
                    Toast.makeText(applicationContext, currentWeatherEvent.errorMessage, Toast.LENGTH_SHORT).show()
                }
                is CurrentWeatherEvent.Loading ->
                    binding.progressBarCyclic.visibility = View.VISIBLE
                else -> Unit
            }
        })

        viewModel.upcomingWeather.observe(this, { upcomingWeatherEvent ->
            when (upcomingWeatherEvent) {
                is UpcomingWeatherEvent.Success -> {
                    binding.progressBarCyclic.visibility = View.GONE
                    setUpRecView(upcomingWeatherEvent.upcomingWeather)
                }
                is UpcomingWeatherEvent.Failure ->{
                    binding.progressBarCyclic.visibility = View.GONE
                    Toast.makeText(applicationContext, upcomingWeatherEvent.errorMessage, Toast.LENGTH_SHORT).show()
                }
                is UpcomingWeatherEvent.Loading ->
                    binding.progressBarCyclic.visibility = View.VISIBLE
                else -> Unit
            }
        })
    }

    private fun setUpRecView(upcomingWeather: UpcomingWeather) {
        hourAdapter=HourAdapter()
        dayAdapter= DayAdapter()
        hourAdapter.setList(upcomingWeather.hourly)
        dayAdapter.setList(upcomingWeather.daily)
        binding.recylcerViewUpcomingHours.apply {
            layoutManager=LinearLayoutManager(applicationContext,LinearLayoutManager.HORIZONTAL,false)
            adapter=hourAdapter
        }
        binding.recyclerViewUpcomingDays.apply {
            layoutManager=LinearLayoutManager(applicationContext)
            adapter=dayAdapter
        }

    }

    private fun setUpView(currentWeather: CurrentWeather) {
        val awq = "AWQ ${currentWeather.main.humidity}"
        binding.apply {
            val currentDegrees = "${currentWeather.main.temp.toInt()-273}°C"
            currentCityName.text = currentWeather.name
            currentDegreeNum.text = currentDegrees
            currentWeatherText.text = currentWeather.weather[0].main
            awmText.text=awq
        }
    }

}