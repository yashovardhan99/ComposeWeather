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
package com.example.androiddevchallenge

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.Crossfade
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import com.example.androiddevchallenge.data.Weather
import com.example.androiddevchallenge.data.WeatherData
import com.example.androiddevchallenge.ui.layout.WeatherScreen
import com.example.androiddevchallenge.ui.theme.MyTheme
import java.time.LocalDateTime

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val weather by viewModel.weatherData.collectAsState(initial = null)
            Crossfade(targetState = weather) {
                if (it != null) {
                    MyTheme(it) {
                        MyApp(it)
                    }
                }
            }
        }
    }
}

// Start building your app here!
@Composable
fun MyApp(weather: Weather) {
    Surface(color = MaterialTheme.colors.background) {
        WeatherScreen(weather)
    }
}

@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun LightPreview() {
    val weather = WeatherData.getRandomWeather(LocalDateTime.now())
    MyTheme(weather) {
        MyApp(weather)
    }
}
