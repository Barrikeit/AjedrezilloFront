package com.alberto.chess.domain.model.pieces

import com.alberto.chess.domain.model.moves.BoardMove
import com.alberto.chess.domain.model.moves.lineMoves
import com.alberto.chess.ui.game.states.GameSnapshotState

class Bishop(
    override val side: Side,
) : Piece {
    override val value: Int = 3
    override val asset: Int =
        when (side) {
            Side.WHITE -> com.alberto.chess.R.drawable.ic_bishop_white
            Side.BLACK -> com.alberto.chess.R.drawable.ic_bishop_black
        }
    override val symbol: String =
        when (side) {
            Side.WHITE -> Symbols.WHITE_BISHOP.fanSymbol
            Side.BLACK -> Symbols.BLACK_BISHOP.fanSymbol
        }
    override val textSymbol: String =
        when (side) {
            Side.WHITE -> Symbols.WHITE_BISHOP.fenSymbol
            Side.BLACK -> Symbols.BLACK_BISHOP.fenSymbol
        }

    override fun pseudoLegalMoves(
        gameSnapshotState: GameSnapshotState,
        check: Boolean
    ): List<BoardMove> = lineMoves(gameSnapshotState, offsets)

    companion object {
        val offsets = listOf(
            Offset.DIAGONAL_UP_RIGHT,
            Offset.DIAGONAL_DOWN_RIGHT,
            Offset.DIAGONAL_UP_LEFT,
            Offset.DIAGONAL_DOWN_LEFT,
        )
    }
}