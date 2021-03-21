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
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.transform.BlurTransformation
import com.example.androiddevchallenge.data.Weather
import com.example.androiddevchallenge.data.WeatherData
import com.example.androiddevchallenge.ui.theme.MyTheme
import dev.chrisbanes.accompanist.coil.CoilImage
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.math.roundToInt

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
        IconWithTemperature(weather = weather)
    }
}

@Composable
fun IconWithTemperature(weather: Weather) {
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .wrapContentHeight(Alignment.Top),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally)
    ) {
        val drawColor = MaterialTheme.colors.onBackground
        val temperature = weather.temperature.roundToInt()
        Box(
            modifier = Modifier
                .weight(0.6f)
                .aspectRatio(1f, false)
        ) {
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                drawPath(
                    weather.state.icon.getPath(size),
                    color = drawColor,
                    style = Stroke(
                        width = 4.dp.toPx(),
                        cap = StrokeCap.Round, join = StrokeJoin.Bevel
                    )
                )
            }
        }
        Text(
            text = "$temperature°",
            style = MaterialTheme.typography.h1.copy(
                fontSize = 96.sp
            ),
            modifier = Modifier
                .weight(1f)
                .padding(16.dp),
        )
    }
}

@Preview(heightDp = 640, widthDp = 360)
@Composable
fun WeatherScreenPreview() {
    val dayWeather = WeatherData.getRandomDayWeather(LocalDate.now())
    val weather = WeatherData.getRandomWeather(LocalDateTime.now(), dayWeather)
    MyTheme(weather.state) {
        Surface {
            WeatherScreen(weather = weather)
        }
    }
}
