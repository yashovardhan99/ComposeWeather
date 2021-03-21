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

import java.time.Instant
import kotlin.random.Random

object Images {
    private val snow = listOf(
        "https://source.unsplash.com/4i5ToPi4K_c",
        "https://source.unsplash.com/-82DDsxA4WI",
        "https://source.unsplash.com/9RP6kahLkIQ",
        "https://source.unsplash.com/zkRu-T5paY0"
    )
//    private val hail = listOf(
//        "https://source.unsplash.com/JbH0Cp0rYys",
//        "https://source.unsplash.com/ALbOhQFx3Wk",
//        "https://source.unsplash.com/hAzPNKftUpY"
//    )
    private val thunderstorm = listOf(
        "https://source.unsplash.com/k6WXcWZMd-k",
        "https://source.unsplash.com/Hgx1jpfYTqc",
//        "https://source.unsplash.com/-b4aTvt80aM",
//        "https://source.unsplash.com/uTvLmMLNNwo"
    )
    private val rain = listOf(
        "https://source.unsplash.com/bxSLlYyhimE",
//        "https://source.unsplash.com/FobwhDUgdrk",
        "https://source.unsplash.com/Ix1TiS-E17E",
        "https://source.unsplash.com/1YHXFeOYpN0"
    )
    private val cloud = listOf(
        "https://source.unsplash.com/v5KGZTY82Xk",
        "https://source.unsplash.com/Fp1aM1RhWsQ",
        "https://source.unsplash.com/UmrhKkHQHAQ"
    )
    private val clear = listOf(
        "https://source.unsplash.com/MUZNpl0Gdp0",
//        "https://source.unsplash.com/qjHGy-GbWe0",
        "https://source.unsplash.com/bDsYeo_7YFo",
        "https://source.unsplash.com/wRZMDJWPuBI"
    )
    private val random = Random(Instant.now().toEpochMilli())
    fun WeatherState.getImageUrl(): String {
        return when (this) {
            WeatherState.Clear -> clear.random(random)
            WeatherState.Cloud -> cloud.random(random)
            WeatherState.Rain -> rain.random(random)
            WeatherState.Snow -> snow.random(random)
            WeatherState.Thunderstorm -> thunderstorm.random(random)
        }
    }
}
