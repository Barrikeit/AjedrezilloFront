package com.alberto.chess.ui.game.states

import com.alberto.chess.domain.model.moves.AppliedMove

/**
 * Representa la transicion de un estado a otro.
 * del estado de la partida al nuevo estado de la partida.
 * y el movimiento que se ha realizado.
 */
data class GameStateTransition(
    val fromGameState: GameSnapshotState,
    val toGameState: GameSnapshotState,
    val move: AppliedMove
)
