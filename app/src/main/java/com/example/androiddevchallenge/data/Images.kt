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
        "https://unsplash.com/photos/4i5ToPi4K_c/download?force=true&w=640",
        "https://unsplash.com/photos/-82DDsxA4WI/download?force=true&w=640",
        "https://unsplash.com/photos/9RP6kahLkIQ/download?force=true&w=640",
        "https://unsplash.com/photos/zkRu-T5paY0/download?force=true&w=640"
    )
    private val hail = listOf(
        "https://unsplash.com/photos/JbH0Cp0rYys/download?force=true&w=640",
        "https://unsplash.com/photos/ALbOhQFx3Wk/download?force=true&w=640",
        "https://unsplash.com/photos/hAzPNKftUpY/download?force=true&w=640"
    )
    private val thunderstorm = listOf(
        "https://unsplash.com/photos/k6WXcWZMd-k/download?force=true&w=640",
        "https://unsplash.com/photos/Hgx1jpfYTqc/download?force=true&w=640",
        "https://unsplash.com/photos/-b4aTvt80aM/download?force=true&w=640",
        "https://unsplash.com/photos/uTvLmMLNNwo/download?force=true&w=640"
    )
    private val rain = listOf(
        "https://unsplash.com/photos/bxSLlYyhimE/download?force=true&w=640",
        "https://unsplash.com/photos/FobwhDUgdrk/download?force=true&w=640",
        "https://unsplash.com/photos/Ix1TiS-E17E/download?force=true&w=640",
        "https://unsplash.com/photos/1YHXFeOYpN0/download?force=true&w=640"
    )
    private val cloud = listOf(
        "https://unsplash.com/photos/v5KGZTY82Xk/download?force=true&w=640",
        "https://unsplash.com/photos/Fp1aM1RhWsQ/download?force=true&w=640",
        "https://unsplash.com/photos/UmrhKkHQHAQ/download?force=true&w=640"
    )
    private val clear = listOf(
        "https://unsplash.com/photos/MUZNpl0Gdp0/download?force=true&w=640",
        "https://unsplash.com/photos/qjHGy-GbWe0/download?force=true&w=640",
        "https://unsplash.com/photos/bDsYeo_7YFo/download?force=true&w=640",
        "https://unsplash.com/photos/wRZMDJWPuBI/download?force=true&w=640"
    )
    private val random = Random(Instant.now().toEpochMilli())
    fun WeatherState.getImageUrl(): String {
        return when (this) {
            WeatherState.Clear -> clear.random(random)
            WeatherState.Cloud -> cloud.random(random)
            WeatherState.Hail -> hail.random(random)
            WeatherState.Rain -> rain.random(random)
            WeatherState.Snow -> snow.random(random)
            WeatherState.Thunderstorm -> thunderstorm.random(random)
        }
    }
}
