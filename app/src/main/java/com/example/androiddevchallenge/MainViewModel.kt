package com.example.androiddevchallenge

import androidx.lifecycle.ViewModel
import com.example.androiddevchallenge.data.Weather
import com.example.androiddevchallenge.data.WeatherData
import java.time.LocalDate
import java.time.LocalDateTime
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow

class MainViewModel : ViewModel() {
    private var refreshNeeded = false
    val weatherData: Flow<Weather> = flow {
        while (true) {
            getWeatherDataFlow()
        }
    }
    private val dayWeather = WeatherData.getRandomDayWeather(LocalDate.now())

    private suspend fun FlowCollector<Weather>.getWeatherDataFlow() {
        emit(WeatherData.getRandomWeather(LocalDateTime.now(), dayWeather))
        for (i in 1..300) {
            delay(100)
            if (refreshNeeded) {
                refreshNeeded = false
                break
            }
        }
    }

    fun refresh() {
        refreshNeeded = true
    }
}