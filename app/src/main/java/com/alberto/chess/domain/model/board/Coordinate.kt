package com.alberto.chess.domain.model.board

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.Dp

/**
 * Describes board coordinates as 1,1 -> top left, 8,8 -> bottom right, regardless of
 * whether the board is flipped
 */
data class Coordinate(
    val x: Float,
    val y: Float,
) {
    operator fun plus(other: Coordinate): Coordinate =
        Coordinate(x + other.x, y + other.y)

    operator fun minus(other: Coordinate): Coordinate =
        Coordinate(x - other.x, y - other.y)

    operator fun times(factor: Float): Coordinate =
        Coordinate(x * factor, y * factor)

    companion object {
        val min = Coordinate(1f, 1f)
        val max = Coordinate(8f, 8f)
    }
}

fun Coordinate.toOffset(squareSize: Dp): Offset =
    Offset(
        x = (x - 1) * squareSize.value,
        y = (y - 1) * squareSize.value
    )
