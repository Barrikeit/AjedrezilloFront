package com.alberto.chess.domain.model.pieces

import com.alberto.chess.domain.model.moves.BoardMove
import com.alberto.chess.domain.model.moves.lineMoves
import com.alberto.chess.ui.game.states.GameSnapshotState

class Rook(
    override val side: Side,
) : Piece {
    override val value: Int = 5
    override val asset: Int =
        when (side) {
            Side.WHITE -> com.alberto.chess.R.drawable.ic_rook_white
            Side.BLACK -> com.alberto.chess.R.drawable.ic_rook_black
        }
    override val textSymbol: String =
        when (side) {
            Side.WHITE -> Symbols.WHITE_ROOK.fenSymbol
            Side.BLACK -> Symbols.BLACK_ROOK.fenSymbol
        }
    override val symbol: String =
        when (side) {
            Side.WHITE -> Symbols.WHITE_ROOK.fanSymbol
            Side.BLACK -> Symbols.BLACK_ROOK.fanSymbol
        }

    override fun pseudoLegalMoves(
        gameSnapshotState: GameSnapshotState,
        check: Boolean
    ): List<BoardMove> = lineMoves(gameSnapshotState, offsets)

    companion object {
        val offsets = listOf(
            Offset.UP,
            Offset.DOWN,
            Offset.LEFT,
            Offset.RIGHT
        )
    }
}