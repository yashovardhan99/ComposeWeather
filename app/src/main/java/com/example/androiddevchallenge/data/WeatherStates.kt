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

import androidx.compose.ui.graphics.Color
import kotlin.random.Random

sealed class WeatherState(val name: String, val primaryColor: Color, val colorOnPrimary: Color) {
    object Snow : WeatherState("Snow", Color(0xFFE2E1E2), Color.Black)
    object Hail : WeatherState("Hail", Color(0xFFDAE0E1), Color.Black)
    object Thunderstorm : WeatherState("Thunderstorm", Color(0xFF252021), Color.White)
    object Rain : WeatherState("Rain", Color(0xFF464570), Color.White)
    object Cloud : WeatherState("Cloud", Color(0xFFDFDFD0), Color.White)
    object Clear : WeatherState("Clear", Color(0xFFBEC9E1), Color.Black)
    companion object {
        fun getRandomState(): WeatherState {
            return when (Random.nextInt(0, 6)) {
                0 -> Snow
                1 -> Hail
                2 -> Thunderstorm
                3 -> Rain
                4 -> Cloud
                else -> Clear
            }
        }
    }
}
