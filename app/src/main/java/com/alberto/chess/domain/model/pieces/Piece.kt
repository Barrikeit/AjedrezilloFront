package com.alberto.chess.domain.model.pieces

import com.alberto.chess.domain.model.moves.BoardMove
import com.alberto.chess.ui.game.states.GameSnapshotState

interface Piece {
    val side: Side
    val value: Int
    val asset: Int
    val symbol: String
    val textSymbol: String

    /**
     * List of all possible moves for this piece without applying pin / check constraints
     */
    fun pseudoLegalMoves(gameSnapshotState: GameSnapshotState, check: Boolean): List<BoardMove> = emptyList()
}