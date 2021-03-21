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
import java.time.Instant
import kotlin.random.Random

object Images {
    private val snow = listOf(
        "https://source.unsplash.com/4i5ToPi4K_c" to Color(0xFFDBDCE1),
        "https://source.unsplash.com/-82DDsxA4WI" to Color(0xFFE2E1E2),
        "https://source.unsplash.com/9RP6kahLkIQ" to Color(0xFFEDEEEF),
        "https://source.unsplash.com/zkRu-T5paY0" to Color(0xFFE2E0E2)
    )

    //    private val hail = listOf(
//        "https://source.unsplash.com/JbH0Cp0rYys",
//        "https://source.unsplash.com/ALbOhQFx3Wk",
//        "https://source.unsplash.com/hAzPNKftUpY"
//    )
    private val thunderstorm = listOf(
        "https://source.unsplash.com/k6WXcWZMd-k" to Color(0xFF252021),
        "https://source.unsplash.com/Hgx1jpfYTqc" to Color(0xFF2D2C2E),
        "https://source.unsplash.com/-b4aTvt80aM" to Color(0xFF1D2426),
//        "https://source.unsplash.com/uTvLmMLNNwo"
    )
    private val rain = listOf(
        "https://source.unsplash.com/bxSLlYyhimE" to Color(0xFF313A40),
        "https://source.unsplash.com/FobwhDUgdrk" to Color(0xFF126685),
//        "https://source.unsplash.com/Ix1TiS-E17E",
        "https://source.unsplash.com/1YHXFeOYpN0" to Color(0xFF464570)
    )
    private val cloud = listOf(
        "https://source.unsplash.com/v5KGZTY82Xk" to Color(0xFFB9C5CC),
        "https://source.unsplash.com/Fp1aM1RhWsQ" to Color(0xFFDDE4DF),
        "https://source.unsplash.com/UmrhKkHQHAQ" to Color(0xFFDFDFD0)
    )
    private val clear = listOf(
        "https://source.unsplash.com/MUZNpl0Gdp0" to Color(0xFFBEC9E1),
        "https://source.unsplash.com/qjHGy-GbWe0" to Color(0xFF75A441),
        "https://source.unsplash.com/bDsYeo_7YFo" to Color(0xFF7388C0),
        "https://source.unsplash.com/wRZMDJWPuBI" to Color(0xFFE1E0E8)
    )
    private val random = Random(Instant.now().toEpochMilli())
    fun WeatherState.getImageData(): Pair<String, Color> {
        return when (this) {
            WeatherState.Clear -> clear.random(random)
            WeatherState.Cloud -> cloud.random(random)
            WeatherState.Rain -> rain.random(random)
            WeatherState.Snow -> snow.random(random)
            WeatherState.Thunderstorm -> thunderstorm.random(random)
        }
    }
}
