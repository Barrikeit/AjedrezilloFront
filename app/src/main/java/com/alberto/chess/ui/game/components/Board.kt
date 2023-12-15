package com.alberto.chess.ui.game.components

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.alberto.chess.domain.model.board.Position
import com.alberto.chess.ui.game.controller.GameController
import com.alberto.chess.ui.game.states.GameScreenState
import com.alberto.chess.ui.theme.ChessTheme

@Composable
fun Board(
    gameScreenState: GameScreenState,
    gameController: GameController,
    isFlipped: Boolean = false,
) {
    Board(
        BoardProperties(
            fromState = gameScreenState.gameState.lastActiveState,
            toState = gameScreenState.gameState.currentSnapshotState,
            gameUiState = gameScreenState.gameUiState,
            isFlipped = isFlipped,
            onClick = { position -> gameController.onClick(position) }
        )
    )
}

@Composable
fun Board(
    boardProperties: BoardProperties,
) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
    ) {
        val properties = boardProperties.copy(squareSize = maxWidth / 8)
        Squares(boardProperties = properties)
        Pieces(boardProperties = properties)
    }
}

@Preview(showBackground = true)
@Composable
fun BoardPreview() {
    ChessTheme {
        var gameScreenState = GameScreenState()
        val gameController = GameController(
            { gameScreenState },
            { gameScreenState = it }
        ).apply {
            applyMove(Position.A2, Position.A3)
            onClick(Position.A7)
        }

        Board(
            gameScreenState = gameScreenState,
            gameController = gameController,
        )
    }
}