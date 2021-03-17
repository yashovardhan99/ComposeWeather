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
package com.example.androiddevchallenge.data

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import kotlin.math.min
import kotlin.random.Random

object WeatherData {
    const val City = "Kolkata"
    private val random = Random(Instant.now().toEpochMilli())
    fun getRandomWeather(time: LocalDateTime): Weather {
        val dayWeather = getRandomDayWeather(time.toLocalDate())
        val range = (dayWeather.maxTemp - dayWeather.minTemp).toDouble()
        val state = WeatherState.getRandomState()
        val windSpeed: Float = if (state in listOf(WeatherState.Clear, WeatherState.Cloud)) {
            random.nextDouble(4.0).toFloat()
        } else {
            random.nextDouble(8.0).toFloat()
        }
        val windDir = random.nextDouble(360.0).toFloat()
        val humidity = when (state) {
            WeatherState.Clear -> random.nextInt(60)
            WeatherState.Cloud -> random.nextInt(20, 80)
            WeatherState.Hail -> random.nextInt(60, 100)
            WeatherState.Rain -> random.nextInt(70, 100)
            WeatherState.Snow -> random.nextInt(50, 80)
            WeatherState.Thunderstorm -> random.nextInt(80, 100)
        }
        val temp = dayWeather.minTemp + when (time.hour) {
            in 0..4 -> {
                if (state in listOf(WeatherState.Clear, WeatherState.Cloud))
                    random.nextDouble(min(10.0, range))
                else random.nextDouble(3.0)
            }
            in 5..8 -> {
                if (time.toLocalTime() > dayWeather.sunrise)
                    random.nextDouble(min(range, 5.0), range)
                else random.nextDouble(min(10.0, range))
            }
            in 9..12 -> random.nextDouble(min(range, 5.0), range)
            in 13..16 -> random.nextDouble(min(range, 5.0), range)
            in 17..20 -> {
                if (time.toLocalTime() > dayWeather.sunset)
                    random.nextDouble(3.0, min(range, 6.0))
                else random.nextDouble(min(range, 5.0), min(range, 10.0))
            }
            in 21..23 -> {
                if (state in listOf(WeatherState.Clear, WeatherState.Cloud))
                    random.nextDouble(min(10.0, range))
                else random.nextDouble(3.0)
            }
            else -> dayWeather.minTemp
        }.toFloat()
        return Weather(
            time, state, temp, Wind(windSpeed, windDir),
            humidity, 1024, dayWeather
        )
    }

    private fun getRandomDayWeather(date: LocalDate): DayWeather {
        val minTemp = random.nextDouble(10.0, 30.0).toFloat()
        val maxTemp = minTemp + random.nextDouble(5.0, 15.0).toFloat()
        val sunrise = LocalTime.of(random.nextInt(4, 7), random.nextInt(60))
        val sunset = LocalTime.of(random.nextInt(17, 19), random.nextInt(60))
        return DayWeather(date, minTemp, maxTemp, sunrise, sunset)
    }
}
