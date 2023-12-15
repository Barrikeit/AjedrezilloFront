package com.alberto.chess.ui.game.states

import com.alberto.chess.domain.model.moves.AppliedMove
import com.alberto.chess.domain.model.pieces.Side
import com.alberto.chess.ui.game.controller.GameStatus

/**
 * Al contrario que GameSnapshotState, GameState es toda la informacion de la partida
 * Tiene los metadatos de la partida
 * El indice del estado actual, no es la ronda xq la ronda es cada 2 movimientos.
 * Tiene una lista de estados de la partida, cada estado es un movimiento
 * El ultimo estado activo, es el ultimo estado que se ha jugado, es el estado actual.
 */
data class GameState(
    val gameMetaData: GameMetaData,
    val currentIndex: Int = 0,
    val states: List<GameSnapshotState> = listOf(GameSnapshotState()),
    val lastActiveState: GameSnapshotState = states.first(),
) {
    val hasPrevIndex: Boolean get() = currentIndex > 0
    val hasNextIndex: Boolean get() = currentIndex < states.lastIndex
    val currentSnapshotState: GameSnapshotState get() = states[currentIndex]
    val toMove: Side get() = currentSnapshotState.turn
    val resolution: GameStatus get() = currentSnapshotState.gameStatus
    fun moves(): List<AppliedMove> = states.mapNotNull { gameState -> gameState.move }
}