package com.alberto.chess.ui.game.components

import androidx.compose.foundation.layout.size
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.alberto.chess.domain.model.board.Coordinate
import com.alberto.chess.domain.model.board.Position
import com.alberto.chess.ui.game.states.GameSnapshotState
import com.alberto.chess.ui.game.states.GameUiState

data class BoardProperties(
    val fromState: GameSnapshotState,
    val toState: GameSnapshotState,
    val gameUiState: GameUiState,
    val isFlipped: Boolean,
    val onClick: (Position) -> Unit,
    val squareSize: Dp = 0.dp,
)

data class SquareProperties(
    val position: Position,
    val isHighlighted: Boolean,
    val clickable: Boolean,
    val isPossibleMove: Boolean,
    val isPossibleCapture: Boolean,
    val onClick: () -> Unit,
    val boardProperties: BoardProperties
) {
    val coordinate: Coordinate =
        position.toCoordinate(boardProperties.isFlipped)

    val sizeModifier: Modifier
        get() = Modifier.size(boardProperties.squareSize)
}