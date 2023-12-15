package com.alberto.chess.ui.game.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alberto.chess.R
import com.alberto.chess.domain.model.pieces.Side
import com.alberto.chess.ui.common.StatusBar
import com.alberto.chess.ui.game.controller.GameStatus
import com.alberto.chess.ui.game.states.GameScreenState
import com.alberto.chess.ui.game.states.GameState
import com.alberto.chess.ui.theme.BoardColorPalette

@Composable
fun Status(
    gameState: GameState
) {
    if (gameState.resolution == GameStatus.IN_PROGRESS) {
        StatusBar(color = if (gameState.currentSnapshotState.turn == Side.WHITE) BoardColorPalette.light else BoardColorPalette.dark)
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(36.dp)
            .background(
                color = if (gameState.currentSnapshotState.turn == Side.WHITE) BoardColorPalette.light else BoardColorPalette.dark,
            ),
        contentAlignment = Alignment.Center
    ) {
        val id = when (gameState.resolution) {
            GameStatus.IN_PROGRESS -> when (gameState.toMove) {
                Side.WHITE -> R.string.gameStatus_white_to_move
                Side.BLACK -> R.string.gameStatus_black_to_move
            }
            GameStatus.CHECKMATE, GameStatus.RESIGNATION -> when (gameState.toMove) {
                Side.WHITE -> R.string.gameStatus_black_wins
                Side.BLACK -> R.string.gameStatus_white_wins
            }
            GameStatus.DRAW -> R.string.gameStatus_draw
            GameStatus.STALEMATE -> R.string.gameStatus_stalemate
            GameStatus.DRAW_BY_REPETITION -> R.string.gameStatus_draw_by_repetition
            GameStatus.INSUFFICIENT_MATERIAL -> R.string.gameStatus_insufficient_material
        }
        Text(
            text = stringResource(id),
            color = BoardColorPalette.text,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
fun StatusPreview() {
    var state = GameScreenState().gameState
    Status(
        gameState = state
    )
}
