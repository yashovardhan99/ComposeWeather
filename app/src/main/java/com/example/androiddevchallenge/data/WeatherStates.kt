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

import androidx.annotation.FloatRange
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.lerp
import androidx.compose.ui.graphics.Path
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

sealed class WeatherState(val name: String, val icon: WeatherIcons) {
    object Snow : WeatherState("Snow", WeatherIcons.Snow)
    object Thunderstorm : WeatherState("Thunderstorm", WeatherIcons.Thunderstorm)
    object Rain : WeatherState("Rain", WeatherIcons.Rain)
    object Cloud : WeatherState("Cloud", WeatherIcons.Cloud)
    object Clear : WeatherState("Clear", WeatherIcons.Clear)
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
    abstract fun getPath(size: Size, progress: Float): Path

    object Snow : WeatherIcons() {
        override fun getPath(size: Size, progress: Float): Path {
            val center = Offset(size.width / 2f, size.height / 2f)
            val pathProgress = progress * 360
//            Log.d("WeatherStates", "Path prog = $pathProgress")
            return Path().apply {
                for (i in 0 until 360 step 45) {
                    if (pathProgress <= i) break
                    val scale = if (pathProgress < i + 45) (pathProgress - i) / 45f
                    else 1f
                    val angle = 2 * Math.PI.toFloat() * i / 360
                    val x = scale * 0.7f * size.width / 2f * cos(angle)
                    val y = scale * 0.7f * size.height / 2f * sin(angle)
                    moveTo(center.x, center.y)
                    relativeLineTo(x, y)
                    val internalAngle = 45f / 180 * Math.PI.toFloat()
                    relativeMoveTo(-2 * x / 7, -2 * y / 7)
                    val ix1 = scale * 0.1f * size.width * cos(angle - internalAngle)
                    val ix2 = scale * 0.1f * size.width * cos(angle + internalAngle)
                    val iy1 = scale * 0.1f * size.height * sin(angle - internalAngle)
                    val iy2 = scale * 0.1f * size.height * sin(angle + internalAngle)
                    relativeLineTo(ix1, iy1)
                    relativeMoveTo(-ix1, -iy1)
                    relativeLineTo(ix2, iy2)
                }
            }
        }
    }

    protected fun Path.animatedLineTo(
        start: Offset,
        end: Offset,
        @FloatRange(from = 0.0, to = 1.0) fraction: Float
    ) {
        val current = lerp(start, end, fraction)
        lineTo(current.x, current.y)
    }

    private fun Path.animatedCubicTo(
        start: Offset,
        a1: Offset,
        a2: Offset,
        end: Offset,
        @FloatRange(from = 0.0, to = 1.0) fraction: Float
    ) {
        val lerp01 = lerp(start, a1, fraction)
        val lerp12 = lerp(a1, a2, fraction)
        val useA2 = lerp(lerp01, lerp12, fraction)
        val lerp23 = lerp(a2, end, fraction)
        val lerp13 = lerp(lerp12, lerp23, fraction)
        val useEnd = lerp(useA2, lerp13, fraction)
        cubicTo(
            lerp01.x,
            lerp01.y,
            useA2.x,
            useA2.y,
            useEnd.x,
            useEnd.y
        )
    }

    protected fun getCloud(size: Size, @FloatRange(from = 0.0, to = 1.0) progress: Float): Path {
        return Path().apply {
            val center = Offset(size.width / 2, size.height / 2)
            val (startX, startY) = Offset(center.x * 0.5f, center.y * 1.4f)
            moveTo(startX, startY)
            if (progress > 0f) {
                val scale = (progress / 0.3f).coerceAtMost(1f)
                // grows along y (0 = 1.45 ->1 = 0.9)
                val a1 = Offset(0f, startY)
                val a2 = Offset(0f, center.y * 0.9f)
                val end = Offset(startX, center.y * 0.9f)
                animatedCubicTo(Offset(startX, startY), a1, a2, end, scale)
            }
            if (progress > 0.3f) {
                val scale = ((progress - 0.3f) / 0.4f).coerceAtMost(1f)
                // grows along x
                val start = Offset(startX, center.y * 0.9f)
                val a1 = Offset(startX, center.y * 0.5f)
                val a2 = Offset(center.x * 1.5f, center.y * 0.5f)
                val end = Offset(center.x * 1.5f, center.y)
                animatedCubicTo(start, a1, a2, end, scale)
            }
            if (progress > 0.7f) {
                val scale = (progress - 0.7f) / 0.3f
                // reduces along y
                val start = Offset(center.x * 1.5f, center.y)
                val a1 = Offset(size.width, center.y)
                val a2 = Offset(size.width, startY)
                val end = Offset(center.x * 1.5f, startY)
                animatedCubicTo(start, a1, a2, end, scale)
            }
        }
    }

    object Thunderstorm : WeatherIcons() {
        override fun getPath(size: Size, progress: Float): Path {
            val center = Offset(size.width / 2, size.height / 2)
            return getCloud(size, (progress / 0.7f).coerceAtMost(1f)).apply {
                if (progress > 0.7f) {
                    val a1 = Offset(center.x, center.y * 1.1f)
                    moveTo(a1.x, a1.y)
                    val a2 = Offset(center.x * 0.7f, center.y * 1.5f)
                    animatedLineTo(a1, a2, ((progress - 0.7f) / 0.1f).coerceAtMost(1f))
                    val a3 = Offset(center.x * 1.1f, center.y * 1.6f)
                    if (progress > 0.8f) animatedLineTo(
                        a2,
                        a3,
                        ((progress - 0.8f) / 0.1f).coerceAtMost(1f)
                    )
                    val a4 = Offset(center.x * 0.9f, center.y * 1.9f)
                    if (progress > 0.9f) animatedLineTo(
                        a3,
                        a4,
                        ((progress - 0.9f) / 0.1f).coerceAtMost(1f)
                    )
                }
            }
        }
    }

    object Rain : WeatherIcons() {
        override fun getPath(size: Size, progress: Float): Path {
            val center = Offset(size.width / 2, size.height / 2)
            return getCloud(size, (progress / 0.7f).coerceAtMost(1f)).apply {
                if (progress > 0.7f) {
                    val scale = ((progress - 0.7f) / 0.3f).coerceAtMost(1f)
                    val start1 = Offset(center.x * 0.9f, center.y * 1.2f)
                    val end1 = Offset(center.x * 0.5f, center.y * 1.7f)
                    moveTo(start1.x, start1.y)
                    animatedLineTo(start1, end1, scale)
                    val start2 = Offset(center.x * 1.15f, center.y * 1.15f)
                    val end2 = Offset(center.x * 0.75f, center.y * 1.65f)
                    moveTo(start2.x, start2.y)
                    animatedLineTo(start2, end2, scale)
                    val start3 = Offset(center.x * 1.4f, center.y * 1.1f)
                    val end3 = Offset(center.x * 1f, center.y * 1.6f)
                    moveTo(start3.x, start3.y)
                    animatedLineTo(start3, end3, scale)
                }
            }
        }
    }

    object Cloud : WeatherIcons() {
        override fun getPath(size: Size, progress: Float): Path {
            val center = Offset(size.width / 2, size.height / 2)
            return getCloud(size, (progress / 0.8f).coerceAtMost(1f)).apply {
                if (progress > 0.8f) {
                    val scale = ((progress - 0.8f) / 0.2f).coerceAtMost(1f)
                    animatedLineTo(
                        Offset(center.x * 1.5f, center.y * 1.45f),
                        Offset(center.x * 0.5f, center.y * 1.4f),
                        scale
                    )
                }
            }
        }
    }

    object Clear : WeatherIcons() {
        override fun getPath(size: Size, progress: Float): Path {
            val center = Offset(size.width / 2, size.height / 2)
            return Path().apply {
                val progressDegrees = progress * 360f
                addArc(Rect(center, size.minDimension * 0.25f), 0f, progressDegrees)
                for (i in 0 until 360 step 45) {
                    if (progressDegrees <= i) break
                    val scale = if (progressDegrees < i + 45) (progressDegrees - i) / 45f
                    else 1f
                    moveTo(center.x, center.y)
                    val angle = 2 * Math.PI.toFloat() * i / 360
                    val x1 = 0.6f * size.width / 2f * cos(angle)
                    val y1 = 0.6f * size.height / 2f * sin(angle)
                    relativeMoveTo(x1, y1)
                    val x2 = scale * 0.3f * size.width / 2f * cos(angle)
                    val y2 = scale * 0.3f * size.height / 2f * sin(angle)
                    relativeLineTo(x2, y2)
                }
            }
        }
    }
}
