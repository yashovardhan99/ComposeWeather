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
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material.icons.filled.Water
import androidx.compose.material.icons.outlined.WbCloudy
import androidx.compose.material.icons.outlined.WbSunny
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.data.Weather
import com.example.androiddevchallenge.data.WeatherData
import com.example.androiddevchallenge.ui.theme.MyTheme
import dev.chrisbanes.accompanist.coil.CoilImage
import dev.chrisbanes.accompanist.insets.ProvideWindowInsets
import dev.chrisbanes.accompanist.insets.statusBarsPadding
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import kotlin.math.roundToInt

var height = 1600
var width = 900

@Composable
fun WeatherScreen(weather: Weather, onRefresh: () -> Unit) {
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
            error = {
                Text(
                    text = it.throwable.stackTraceToString(),
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Red)
                )
            },
        )
        WeatherDataScreen(weather)
        Button(
            onClick = onRefresh, modifier = Modifier.align(Alignment.TopEnd),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
            elevation = ButtonDefaults.elevation(0.dp, pressedElevation = 0.dp)
        ) {
            Icon(imageVector = Icons.Default.Refresh, contentDescription = "Refresh")
        }
    }
}

@Composable
fun WeatherDataTop(weather: Weather, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Top
    ) {
        IconWithTemperature(weather)
        MinMaxTemp(weather)
    }
}

@Composable
fun WeatherDataScreen(weather: Weather) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(
                Brush.verticalGradient(
                    0.25f to weather.imageColor,
                    0.6f to Color.Transparent,
                    0.85f to weather.imageColor,
                )
            ),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        WeatherDataTop(weather = weather)
        WeatherFacts(weather = weather)
    }
}

@Composable
fun WeatherFacts(weather: Weather) {
    val surfaceColor = weather.imageColor
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(32.dp, Alignment.CenterHorizontally)
    ) {
        WeatherFact(
            value = weather.dayWeather.sunrise.format(timeFormatter),
            label = "Sunrise",
            surfaceColor,
            Icons.Outlined.WbSunny
        )
        WeatherFact(
            value = weather.dayWeather.sunset.format(timeFormatter),
            label = "Sunset",
            surfaceColor,
            Icons.Outlined.WbCloudy
        )
        WeatherFact(
            value = "${weather.wind.speed.roundToInt()} Km/h",
            label = "Wind",
            surfaceColor,
            Icons.Default.Air
        )
        WeatherFact(
            value = "${weather.humidity} %",
            label = "Humidity",
            surfaceColor,
            Icons.Default.Water
        )
        WeatherFact(
            value = "${weather.airPressure} hPa",
            label = "Pressure",
            surfaceColor,
            Icons.Default.Thermostat
        )
    }
}

@Composable
fun WeatherFact(value: String, label: String, surfaceColor: Color, icon: ImageVector) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        color = surfaceColor.copy(alpha = 0.7f)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .wrapContentHeight()
                .widthIn(70.dp, 120.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(imageVector = icon, contentDescription = null)
            Text(text = value, style = MaterialTheme.typography.h5)
            Text(text = label, style = MaterialTheme.typography.subtitle1)
        }
    }
}

private val timeFormatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)

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
    val progress = remember(weather.state) { Animatable(0f) }
    LaunchedEffect(weather.state) {
        progress.snapTo(0f)
        progress.animateTo(1f, animationSpec = tween(500, easing = LinearEasing))
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .wrapContentHeight(Alignment.Top),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        val drawColor = MaterialTheme.colors.onBackground
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
                    weather.state.icon.getPath(size, progress.value),
                    color = drawColor,
                    style = Stroke(
                        width = 4.dp.toPx(),
                        cap = StrokeCap.Round, join = StrokeJoin.Bevel
                    )
                )
            }
        }
        Text(
            text = "${weather.temperature.roundToInt()}°",
            style = MaterialTheme.typography.h1,
            modifier = Modifier
                .weight(1f)
                .semantics {
                    contentDescription =
                        "Current temperature is ${weather.temperature.roundToInt()} degrees"
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
        ProvideWindowInsets() {
            Surface {
                WeatherScreen(weather = weather) {}
            }
        }
    }
}
