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
package com.example.androiddevchallenge.ui.theme

import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import com.example.androiddevchallenge.data.Weather
import com.example.androiddevchallenge.data.WeatherState

// private val DarkColorPalette = darkColors(
//    primary = purple200,
//    primaryVariant = purple700,
//    secondary = teal200
// )
//
// private val LightColorPalette = lightColors(
//    primary = purple500,
//    primaryVariant = purple700,
//    secondary = teal200

/* Other default colors to override
background = Color.White,
surface = Color.White,
onPrimary = Color.White,
onSecondary = Color.Black,
onBackground = Color.Black,
onSurface = Color.Black,
*/
// )

fun colorScheme(weather: Weather): Colors =
    if (weather.state != WeatherState.Thunderstorm && weather.state != WeatherState.Rain)
        lightColors(background = weather.imageColor)
    else darkColors(background = weather.imageColor)

@Composable
fun MyTheme(weather: Weather, content: @Composable() () -> Unit) {
    val colors = colorScheme(weather)
    if (colors.isLight) {
        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
    } else {
        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
    }

    MaterialTheme(
        colors = colors,
        typography = typography,
        shapes = shapes,
        content = content
    )
}
