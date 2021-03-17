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

import androidx.annotation.IntRange
import java.time.LocalDateTime

data class Weather(
    val time: LocalDateTime,
    val state: WeatherState,
    val temperature: Float,
    val wind: Wind,
    @IntRange(from = 0, to = 100) val humidity: Int,
    val airPressure: Int,
    val dayWeather: DayWeather
)
