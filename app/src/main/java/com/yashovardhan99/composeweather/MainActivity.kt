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
import androidx.core.view.WindowCompat
import com.yashovardhan99.composeweather.data.Weather
import com.yashovardhan99.composeweather.data.WeatherData
import com.yashovardhan99.composeweather.ui.layout.WeatherScreen
import com.yashovardhan99.composeweather.ui.theme.MyTheme
import dev.chrisbanes.accompanist.insets.ProvideWindowInsets
import java.time.LocalDateTime

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            val weather by viewModel.weatherData.collectAsState(initial = null)
            Crossfade(targetState = weather) {
                if (it != null) {
                    MyTheme(it) {
                        ProvideWindowInsets() {
                            MyApp(it, viewModel)
                        }
                    }
                }
            }
        }
    }
}

// Start building your app here!
@Composable
fun MyApp(weather: Weather, viewModel: MainViewModel) {
    Surface(color = MaterialTheme.colors.background) {
        WeatherScreen(weather) {
            viewModel.refresh()
        }
    }
}

@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun LightPreview() {
    val weather = WeatherData.getRandomWeather(LocalDateTime.now())
    MyTheme(weather) {
        MyApp(weather, MainViewModel())
    }
}
