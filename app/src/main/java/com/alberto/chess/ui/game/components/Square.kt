package com.alberto.chess.ui.game.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alberto.chess.domain.model.board.Coordinate
import com.alberto.chess.domain.model.board.Position
import com.alberto.chess.ui.theme.BoardColorPalette
import java.util.UUID

@Composable
fun Squares(
    boardProperties: BoardProperties,
) {
    Position.values().forEach { position ->
        key(position) {
            val squareProperties = remember(boardProperties) {
                SquareProperties(
                    position = position,
                    isHighlighted = position in boardProperties.gameUiState.highlightedPositions,
                    clickable = position in boardProperties.gameUiState.clickablePositions,
                    isPossibleMove = position in boardProperties.gameUiState.possibleMovesWithoutCaptures,
                    isPossibleCapture = position in boardProperties.gameUiState.possibleCaptures,
                    onClick = { boardProperties.onClick(position) },
                    boardProperties = boardProperties
                )
            }
            Square(
                squareProperties = squareProperties
            )
        }
    }
}

@Composable
fun Square(
    squareProperties: SquareProperties,
) {
    Box(
        modifier = Modifier
            .offset(
                Dp((squareProperties.coordinate.x - 1) * squareProperties.boardProperties.squareSize.value),
                Dp((squareProperties.coordinate.y - 1) * squareProperties.boardProperties.squareSize.value)
            ).pointerInput(UUID.randomUUID()) {
                detectTapGestures(
                    onPress = { if (squareProperties.clickable) squareProperties.onClick() },
                )
            }
    ) {
        /**
         * Square background color
         */
        Canvas(squareProperties.sizeModifier) {
            drawRect(color = if (squareProperties.position.isDarkSquare()) BoardColorPalette.dark else BoardColorPalette.light)
        }

        /**
         * Square highlight
         */
        if (squareProperties.isHighlighted) {
            Canvas(squareProperties.sizeModifier) {
                drawRect(
                    color = if (squareProperties.position.isDarkSquare()) BoardColorPalette.highlightedOnDark else BoardColorPalette.highlightedOnLight,
                    size = size,
                    alpha = 0.75f
                )
            }
        }

        /**
         * Square position labels
         */
        if (squareProperties.coordinate.y == Coordinate.max.y) {
            PositionLabel(
                text = squareProperties.position.file().notation,
                textColor = BoardColorPalette.text,
                alignment = Alignment.BottomEnd,
                modifier = squareProperties.sizeModifier
            )
        }
        if (squareProperties.coordinate.x == Coordinate.min.x) {
            PositionLabel(
                text = squareProperties.position.rank.toString(),
                textColor = BoardColorPalette.text,
                alignment = Alignment.TopStart,
                modifier = squareProperties.sizeModifier
            )
        }

        /**
         * Square possible move or capture
         */
        if (squareProperties.isPossibleMove) {
            CircleDecoratedSquare(
                onClick = squareProperties.onClick,
                radius = { size.minDimension / 6f },
                drawStyle = { Fill },
                modifier = squareProperties.sizeModifier
            )
        } else if (squareProperties.isPossibleCapture) {
            CircleDecoratedSquare(
                onClick = squareProperties.onClick,
                radius = { size.minDimension / 3f },
                drawStyle = { Stroke(width = size.minDimension / 12f) },
                modifier = squareProperties.sizeModifier
            )
        }
    }
}

@Composable
private fun CircleDecoratedSquare(
    onClick: () -> Unit,
    radius: DrawScope.() -> Float,
    drawStyle: DrawScope.() -> DrawStyle,
    modifier: Modifier,
) {
    Canvas(
        modifier = modifier
            .pointerInput(UUID.randomUUID()) {
                detectTapGestures(
                    onPress = { onClick() },
                )
            }
    ) {
        drawCircle(
            color = Color.DarkGray,
            radius = radius(this),
            alpha = 0.25f,
            style = drawStyle(this)
        )
    }
}

@Composable
fun PositionLabel(
    text: String,
    textColor: Color,
    alignment: Alignment,
    modifier: Modifier,
) {
    Box(
        contentAlignment = alignment,
        modifier = modifier.padding(start = 2.dp, end = 2.dp)
    ) {
        Text(
            text = text,
            color = textColor,
            fontSize = 10.sp
        )
    }
}