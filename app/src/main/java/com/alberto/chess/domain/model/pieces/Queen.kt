package com.alberto.chess.domain.model.pieces

import com.alberto.chess.domain.model.moves.BoardMove
import com.alberto.chess.domain.model.moves.lineMoves
import com.alberto.chess.ui.game.states.GameSnapshotState

class Queen(
    override val side: Side,
) : Piece {
    override val value: Int = 9
    override val asset: Int =
        when (side) {
            Side.WHITE -> com.alberto.chess.R.drawable.ic_queen_white
            Side.BLACK -> com.alberto.chess.R.drawable.ic_queen_black
        }
    override val textSymbol: String =
        when (side) {
            Side.WHITE -> Symbols.WHITE_QUEEN.fenSymbol
            Side.BLACK -> Symbols.BLACK_QUEEN.fenSymbol
        }
    override val symbol: String =
        when (side) {
            Side.WHITE -> Symbols.WHITE_QUEEN.fanSymbol
            Side.BLACK -> Symbols.BLACK_QUEEN.fanSymbol
        }

    override fun pseudoLegalMoves(
        gameSnapshotState: GameSnapshotState,
        check: Boolean
    ): List<BoardMove> = lineMoves(gameSnapshotState, directions)

    companion object {
        val directions = Bishop.offsets + Rook.offsets
    }
}
