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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.transform.BlurTransformation
import com.example.androiddevchallenge.data.DayWeather
import com.example.androiddevchallenge.data.Weather
import com.example.androiddevchallenge.data.WeatherData
import com.example.androiddevchallenge.data.WeatherIcons
import com.example.androiddevchallenge.ui.theme.MyTheme
import dev.chrisbanes.accompanist.coil.CoilImage
import dev.chrisbanes.accompanist.insets.statusBarsPadding
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import kotlin.math.roundToInt

@Composable
fun WeatherScreen(weather: Weather) {
    val context = LocalContext.current
    var height = 1600
    var width = 900
    Box(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxSize()
            .onGloballyPositioned {
                height = it.size.height
                width = it.size.width
                Log.d("Weather", "Height = $height, width = $width")
            }
            .background(color = weather.imageColor)
    ) {
        CoilImage(
            data = "${weather.imageUrl}/${width}x$height",
            modifier = Modifier.fillMaxSize(),
            contentDescription = null,
            fadeIn = true,
            contentScale = ContentScale.Crop,
//            requestBuilder = {
//                transformations(BlurTransformation(context))
//            },
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
        WeatherDataScreen1(weather)
    }
}

@Composable
fun WeatherDataTop(weather: Weather) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Top
    ) {
        IconWithTemperature(weather)
        MinMaxTemp(weather)
    }
}

@Composable
fun WeatherDataBottom() {
}

@Composable
fun WeatherDataScreen1(weather: Weather) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    0.2f to weather.imageColor,
                    0.6f to Color.Transparent,
                    0.9f to weather.imageColor,
                )
            ),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        WeatherDataTop(weather = weather)
        SunFacts(weather = weather.dayWeather)
    }
}

@Composable
fun SunFacts(weather: DayWeather) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        SunFact(isRise = true, time = weather.sunrise, Modifier.weight(1f))
        SunFact(isRise = false, time = weather.sunset, Modifier.weight(1f))
    }
}

private val timeFormatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)

@Composable
fun SunFact(isRise: Boolean, time: LocalTime, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = if (isRise) Alignment.Start else Alignment.End
    ) {
        Text(
            text = time.format(timeFormatter),
            style = MaterialTheme.typography.caption,
            fontSize = 36.sp,
            textAlign = if (isRise) TextAlign.Start else TextAlign.End
        )
        val drawColor = MaterialTheme.colors.onBackground
        Row(
            horizontalArrangement = if (isRise) Arrangement.Start else Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(24.dp)
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    drawPath(
                        WeatherIcons.Clear.getPath(size),
                        color = drawColor,
                        style = Stroke(width = 2.dp.toPx())
                    )
                }
            }
            Text(
                text = (if (isRise) "Sunrise" else "Sunset").toUpperCase(Locale.current),
                modifier = Modifier
                    .wrapContentSize(),
                style = MaterialTheme.typography.caption,
                fontSize = 24.sp,
                textAlign = TextAlign.End
            )
        }
    }
}

@Composable
fun MinMaxTemp(weather: Weather) {
    Text(
        text = "${weather.dayWeather.minTemp.roundToInt()}/${weather.dayWeather.maxTemp.roundToInt()}°",
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp),
        textAlign = TextAlign.End,
        style = MaterialTheme.typography.h5,
    )
}

@Composable
fun IconWithTemperature(weather: Weather) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .wrapContentHeight(Alignment.Top),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
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
                    .semantics {
                        contentDescription = weather.state.name
                    }
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
            style = MaterialTheme.typography.h1,
            modifier = Modifier
                .weight(1f)
                .semantics {
                    contentDescription = "Current temperature is $temperature degrees"
                }
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.End,
        )
    }
}

@Preview(heightDp = 640, widthDp = 360)
@Composable
fun WeatherScreenPreview() {
    val dayWeather = WeatherData.getRandomDayWeather(LocalDate.now())
    val weather = WeatherData.getRandomWeather(LocalDateTime.now(), dayWeather)
    MyTheme(weather) {
        Surface {
            WeatherScreen(weather = weather)
        }
    }
}
