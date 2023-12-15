package com.alberto.chess.ui.game.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import com.alberto.chess.domain.model.board.toOffset
import com.alberto.chess.domain.model.pieces.Piece

@Composable
fun Pieces(
    boardProperties: BoardProperties,
) {
    boardProperties.toState.board.pieces.forEach { (position, piece) ->
        key(piece) {
            val fromPosition = boardProperties.fromState.board.find(piece)?.position
            val currentOffset = fromPosition
                ?.toCoordinate(boardProperties.isFlipped)
                ?.toOffset(boardProperties.squareSize)

            val targetOffset = position
                .toCoordinate(boardProperties.isFlipped)
                .toOffset(boardProperties.squareSize)

            val offset = remember {
                Animatable(currentOffset ?: targetOffset, Offset.VectorConverter)
            }
            LaunchedEffect(targetOffset) {
                offset.animateTo(targetOffset, tween(100, easing = LinearEasing))
            }
            LaunchedEffect(boardProperties.isFlipped) {
                offset.snapTo(targetOffset)
            }

            Piece(
                piece = piece,
                squareSize = boardProperties.squareSize,
                modifier = Modifier.offset(Dp(offset.value.x), Dp(offset.value.y))
            )
        }
    }
}

@Composable
fun Piece(
    piece: Piece,
    squareSize: Dp,
    modifier: Modifier = Modifier
) {
    key(piece) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier.size(squareSize, squareSize)
        ) {
            Icon(
                painter = painterResource(id = piece.asset),
                tint = Color.Unspecified,
                contentDescription = "${piece.side} ${piece.javaClass.simpleName}"
            )
        }
    }
}