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
package com.example.androiddevchallenge.ui.layout

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import coil.transform.BlurTransformation
import com.example.androiddevchallenge.data.Weather
import com.example.androiddevchallenge.data.WeatherData
import com.example.androiddevchallenge.ui.theme.MyTheme
import dev.chrisbanes.accompanist.coil.CoilImage
import java.time.LocalDate
import java.time.LocalDateTime

@Composable
fun WeatherScreen(weather: Weather) {
    val context = LocalContext.current
    var height = 1600
    var width = 900
    Box(
        modifier = Modifier
            .fillMaxSize()
            .onGloballyPositioned {
                height = it.size.height
                width = it.size.width
                Log.d("Weather", "Height = $height, width = $width")
            }
            .background(color = weather.state.primaryColor)
    ) {
        CoilImage(
            data = "${weather.imageUrl}/${width}x$height",
            modifier = Modifier.fillMaxSize(),
            contentDescription = null,
            fadeIn = true,
            contentScale = ContentScale.Crop,
            requestBuilder = {
                transformations(BlurTransformation(context))
            },
            error = {
                Text(
                    text = it.throwable.stackTraceToString(),
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Red)
                )
            },
            loading = {
                CoilImage(
                    data = "${weather.imageUrl}/${width / 100}x${height / 100}",
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize(),
                    requestBuilder = {
                        transformations(BlurTransformation(context))
                    }
                )
            }
        )
        WeatherDataTop(weather)
    }
}

@Composable
fun WeatherDataTop(weather: Weather) {
    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Top) {
        Text(text = "Weather = $weather")
    }
}

@Preview(heightDp = 640, widthDp = 360)
@Composable
fun WeatherScreenPreview() {
    val dayWeather = WeatherData.getRandomDayWeather(LocalDate.now())
    val weather = WeatherData.getRandomWeather(LocalDateTime.now(), dayWeather)
    MyTheme(weather.state) {
        Surface() {
            WeatherScreen(weather = weather)
        }
    }
}
