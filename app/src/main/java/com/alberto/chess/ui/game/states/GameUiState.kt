package com.alberto.chess.ui.game.states

import com.alberto.chess.domain.model.board.Position
import com.alberto.chess.domain.model.moves.BoardMove
import com.alberto.chess.domain.model.moves.Capture
import com.alberto.chess.domain.model.moves.targetPositions

data class GameUiState(
    private val gameSnapshotState: GameSnapshotState,
    val selectedPosition: Position? = null,
    val isFlipped: Boolean = false,
    val showPromotionDialog: Boolean = false,
    val showGameDialog: Boolean = false,
) {
    private val lastMovePositions: List<Position> =
        gameSnapshotState.lastMove?.let { listOf(it.from, it.to) } ?: emptyList()

    private val uiSelectedPositions: List<Position> =
        selectedPosition?.let { listOf(it) } ?: emptyList()

    val highlightedPositions: List<Position> =
        lastMovePositions + uiSelectedPositions

    private val ownPiecePositions: List<Position> =
        gameSnapshotState.board.pieces
            .filter { (_, piece) -> piece.side == gameSnapshotState.turn }
            .map { it.key }

    val possibleCaptures: List<Position> = possibleMoves { it.preMove is Capture }.targetPositions()
    val possibleMovesWithoutCaptures: List<Position> = possibleMoves { it.preMove !is Capture }.targetPositions()

    fun possibleMoves(predicate: (BoardMove) -> Boolean = { true }) =
        selectedPosition?.let {
            gameSnapshotState.legalMovesFrom(it)
                .filter(predicate)
        } ?: emptyList()

    val clickablePositions: List<Position> = ownPiecePositions + possibleCaptures + possibleMovesWithoutCaptures

    fun deselect(): GameUiState = copy(
        selectedPosition = null
    )

    fun select(position: Position): GameUiState =
        if (selectedPosition == position) {
            deselect()
        } else {
            copy(
                selectedPosition = position
            )
        }
}