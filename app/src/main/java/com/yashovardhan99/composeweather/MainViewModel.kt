/*
 * Copyright 2021 Yashovardhan Dhanania
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yashovardhan99.composeweather

import androidx.lifecycle.ViewModel
import com.yashovardhan99.composeweather.data.Weather
import com.yashovardhan99.composeweather.data.WeatherData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import java.time.LocalDate
import java.time.LocalDateTime

class MainViewModel : ViewModel() {
    //    private var refreshNeeded = false

    private val dayWeather = WeatherData.getRandomDayWeather(LocalDate.now())
    private val weather = MutableStateFlow<Weather?>(null)
    val weatherData: Flow<Weather?> = weather

    init {
        refresh()
    }
//    private suspend fun FlowCollector<Weather>.getWeatherDataFlow() {
//        emit(WeatherData.getRandomWeather(LocalDateTime.now(), dayWeather))
//        for (i in 1..300) {
//            delay(100)
//            if (refreshNeeded) {
//                refreshNeeded = false
//                break
//            }
//        }
//    }

    fun refresh() {
        weather.value = WeatherData.getRandomWeather(LocalDateTime.now(), dayWeather)
    }
}
