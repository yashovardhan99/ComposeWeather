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

sealed class WeatherState(val name: String, val primaryColor: Color, val colorOnPrimary: Color) {
    object Snow : WeatherState("Snow", Color.White, Color.Black)
    object Hail : WeatherState("Hail", Color.White, Color.Black)
    object Thunderstorm : WeatherState("Thunderstorm", Color.DarkGray, Color.White)
    object Rain : WeatherState("Rain", Color.Black, Color.White)
    object Cloud : WeatherState("Cloud", Color.LightGray, Color.White)
    object Clear : WeatherState("Clear", Color.Blue, Color.Black)
}
