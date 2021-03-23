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
package com.yashovardhan99.composeweather.data

import androidx.annotation.FloatRange
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.East
import androidx.compose.material.icons.filled.North
import androidx.compose.material.icons.filled.NorthEast
import androidx.compose.material.icons.filled.NorthWest
import androidx.compose.material.icons.filled.South
import androidx.compose.material.icons.filled.SouthEast
import androidx.compose.material.icons.filled.SouthWest
import androidx.compose.material.icons.filled.West
import androidx.compose.ui.graphics.vector.ImageVector
import com.yashovardhan99.composeweather.data.CompassDirection.Companion.toCompassDirection

data class Wind(
    @FloatRange(from = 0.0) val speed: Float,
    @FloatRange(from = 0.0, to = 360.0, toInclusive = false)
    val directionDegrees: Float,
    val direction: CompassDirection = directionDegrees.toCompassDirection()
)

sealed class CompassDirection(val icon: ImageVector) {
    object North : CompassDirection(Icons.Default.North)
    object NorthEast : CompassDirection(Icons.Default.NorthEast)
    object East : CompassDirection(Icons.Default.East)
    object SouthEast : CompassDirection(Icons.Default.SouthEast)
    object South : CompassDirection(Icons.Default.South)
    object SouthWest : CompassDirection(Icons.Default.SouthWest)
    object West : CompassDirection(Icons.Default.West)
    object NorthWest : CompassDirection(Icons.Default.NorthWest)
    companion object {
        fun Float.toCompassDirection(): CompassDirection {
            check(0 <= this && this < 360)
            return when (this) {
                in 0f..22.5f -> North
                in 22.5f..67.5f -> NorthEast
                in 67.5f..112.5f -> East
                in 112.5f..157.5f -> SouthEast
                in 157.5f..202.5f -> South
                in 202.5f..247.5f -> SouthWest
                in 247.5f..292.5f -> West
                in 292.5f..337.5f -> NorthWest
                in 337.5f..360f -> North
                else -> throw IllegalStateException()
            }
        }
    }
}
