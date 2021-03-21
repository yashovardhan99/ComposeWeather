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

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

sealed class WeatherState(val name: String, val primaryColor: Color, val icon: WeatherIcons) {
    object Snow : WeatherState("Snow", Color(0xFFE2E1E2), WeatherIcons.Snow)
    object Thunderstorm : WeatherState("Thunderstorm", Color(0xFF252021), WeatherIcons.Thunderstorm)
    object Rain : WeatherState("Rain", Color(0xFF464570), WeatherIcons.Rain)
    object Cloud : WeatherState("Cloud", Color(0xFFDFDFD0), WeatherIcons.Cloud)
    object Clear : WeatherState("Clear", Color(0xFFBEC9E1), WeatherIcons.Clear)
    companion object {
        fun getRandomState(): WeatherState {
            return when (Random.nextInt(0, 5)) {
                0 -> Snow
                1 -> Thunderstorm
                2 -> Rain
                3 -> Cloud
                else -> Clear
            }
        }
    }
}

sealed class WeatherIcons() {
    abstract fun getPath(size: Size): Path

    object Snow : WeatherIcons() {
        override fun getPath(size: Size): Path {
            val center = Offset(size.width / 2f, size.height / 2f)
            return Path().apply {
                for (i in 0 until 360 step 45) {
                    val angle = 2 * Math.PI.toFloat() * i / 360
                    val x = 0.7f * size.width / 2f * cos(angle)
                    val y = 0.7f * size.height / 2f * sin(angle)
                    moveTo(center.x, center.y)
                    relativeLineTo(x, y)
                    val internalAngle = 45f / 180 * Math.PI.toFloat()
                    relativeMoveTo(-2 * x / 7, -2 * y / 7)
                    val ix1 = 0.1f * size.width * cos(angle - internalAngle)
                    val ix2 = 0.1f * size.width * cos(angle + internalAngle)
                    val iy1 = 0.1f * size.height * sin(angle - internalAngle)
                    val iy2 = 0.1f * size.height * sin(angle + internalAngle)
                    relativeLineTo(ix1, iy1)
                    relativeMoveTo(-ix1, -iy1)
                    relativeLineTo(ix2, iy2)
                }
            }
        }
    }

    protected fun getCloud(size: Size): Path {
        return Path().apply {
            val center = Offset(size.width / 2, size.height / 2)
            val (startX, startY) = Offset(center.x * 0.5f, center.y * 1.4f)
            moveTo(startX, startY)
            cubicTo(0f, startY, 0f, center.y * 0.9f, startX, center.y * 0.9f)
            cubicTo(
                startX,
                center.y * 0.5f,
                center.x * 1.5f,
                center.y * 0.5f,
                center.x * 1.5f,
                center.y
            )
            cubicTo(size.width, center.y, size.width, startY, center.x * 1.5f, startY)
        }
    }

    object Thunderstorm : WeatherIcons() {
        override fun getPath(size: Size): Path {
            val center = Offset(size.width / 2, size.height / 2)
            return getCloud(size).apply {
                moveTo(center.x, center.y * 1.1f)
                lineTo(center.x * 0.7f, center.y * 1.5f)
                lineTo(center.x * 1.1f, center.y * 1.6f)
                lineTo(center.x * 0.9f, center.y * 1.9f)
            }
        }
    }

    object Rain : WeatherIcons() {
        override fun getPath(size: Size): Path {
            val center = Offset(size.width / 2, size.height / 2)
            return getCloud(size).apply {
                moveTo(center.x * 0.9f, center.y * 1.2f)
                lineTo(center.x * 0.5f, center.y * 1.7f)
                moveTo(center.x * 1.15f, center.y * 1.15f)
                lineTo(center.x * 0.75f, center.y * 1.65f)
                moveTo(center.x * 1.4f, center.y * 1.1f)
                lineTo(center.x * 1f, center.y * 1.6f)
            }
        }

    }

    object Cloud : WeatherIcons() {
        override fun getPath(size: Size): Path {
            val center = Offset(size.width / 2, size.height / 2)
            return getCloud(size).apply {
                lineTo(center.x * 0.5f, center.y * 1.4f)
            }
        }
    }

    object Clear : WeatherIcons() {
        override fun getPath(size: Size): Path {
            val center = Offset(size.width / 2, size.height / 2)
            return Path().apply {
                addOval(Rect(center, size.minDimension * 0.25f))
                for (i in 0 until 360 step 45) {
                    moveTo(center.x, center.y)
                    val angle = 2 * Math.PI.toFloat() * i / 360
                    val x1 = 0.6f * size.width / 2f * cos(angle)
                    val y1 = 0.6f * size.height / 2f * sin(angle)
                    relativeMoveTo(x1, y1)
                    val x2 = 0.3f * size.width / 2f * cos(angle)
                    val y2 = 0.3f * size.height / 2f * sin(angle)
                    relativeLineTo(x2, y2)
                }
            }
        }
    }
}