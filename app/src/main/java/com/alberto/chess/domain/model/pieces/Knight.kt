package com.alberto.chess.domain.model.pieces

import com.alberto.chess.domain.model.moves.BoardMove
import com.alberto.chess.domain.model.moves.singleMove
import com.alberto.chess.ui.game.states.GameSnapshotState

class Knight(
    override val side: Side,
) : Piece {
    override val value: Int = 3
    override val asset: Int =
        when (side) {
            Side.WHITE -> com.alberto.chess.R.drawable.ic_knight_white
            Side.BLACK -> com.alberto.chess.R.drawable.ic_knight_black
        }
    override val textSymbol: String =
        when (side) {
            Side.WHITE -> Symbols.WHITE_KNIGHT.fenSymbol
            Side.BLACK -> Symbols.BLACK_KNIGHT.fenSymbol
        }
    override val symbol: String =
        when (side) {
            Side.WHITE -> Symbols.WHITE_KNIGHT.fanSymbol
            Side.BLACK -> Symbols.BLACK_KNIGHT.fanSymbol
        }

    override fun pseudoLegalMoves(
        gameSnapshotState: GameSnapshotState,
        check: Boolean
    ): List<BoardMove> =
        targetLocations.mapNotNull {
            singleMove(
                gameSnapshotState,
                it.fileOffset,
                it.rankOffset
            )
        }

    companion object {
        val targetLocations = listOf(
            Offset.JUMP_UP_RIGHT,
            Offset.JUMP_DOWN_RIGHT,
            Offset.JUMP_UP_LEFT,
            Offset.JUMP_DOWN_LEFT,
            Offset.JUMP_RIGHT_UP,
            Offset.JUMP_LEFT_UP,
            Offset.JUMP_RIGHT_DOWN,
            Offset.JUMP_LEFT_DOWN,
        )
    }
}
