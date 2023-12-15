package com.alberto.chess.ui.game.states

import com.alberto.chess.domain.model.board.Board
import com.alberto.chess.domain.model.board.Position
import com.alberto.chess.domain.model.moves.BoardMove
import com.alberto.chess.domain.model.pieces.King
import com.alberto.chess.domain.model.pieces.Rook
import com.alberto.chess.domain.model.pieces.Side

/**
 * CastlingInfo es la informacion necesaria para saber si se puede hacer enroque o no.
 * Tiene un map de los dos lados, y para cada uno de ellos, si el rey se ha movido, si la torre de lado largo o de lado corto se ha movido.
 * en base al Game
 */
data class CastlingState(
    val states: Map<Side, CastlingInfo> = mapOf(
        Side.WHITE to CastlingInfo(),
        Side.BLACK to CastlingInfo()
    )
) {
    data class CastlingInfo(
        val kingHasMoved: Boolean = false,
        val kingSideRookHasMoved: Boolean = false,
        val queenSideRookHasMoved: Boolean = false,
    ) {
        fun canCastleKingSide(): Boolean = !kingHasMoved && !kingSideRookHasMoved
        fun canCastleQueenSide(): Boolean = !kingHasMoved && !queenSideRookHasMoved
    }

    operator fun get(side: Side) = states[side]!!

    fun apply(boardMove: BoardMove): CastlingState {
        val move = boardMove.move
        val piece = boardMove.piece
        val side = piece.side
        val state = states[side]!!

        val kingSideRookInitialPosition = if (side == Side.WHITE) Position.H1 else Position.H8
        val queenSideRookInitialPosition = if (side == Side.WHITE) Position.A1 else Position.A8

        val updatedHolder = state.copy(
            kingHasMoved = state.kingHasMoved || piece is King,
            kingSideRookHasMoved = state.kingSideRookHasMoved || piece is Rook && move.from == kingSideRookInitialPosition,
            queenSideRookHasMoved = state.queenSideRookHasMoved || piece is Rook && move.from == queenSideRookInitialPosition,
        )

        return copy(states = states.minus(side).plus(side to updatedHolder))
    }

    companion object {
        fun from(board: Board): CastlingState {
            val white = board.pieces(Side.WHITE)
            val whiteCastlingInfo = CastlingInfo(
                kingHasMoved = white[Position.E1] !is King,
                kingSideRookHasMoved = white[Position.H1] !is Rook,
                queenSideRookHasMoved = white[Position.A1] !is Rook,
            )
            val black = board.pieces(Side.BLACK)
            val blackCastlingInfo = CastlingInfo(
                kingHasMoved = black[Position.E8] !is King,
                kingSideRookHasMoved = black[Position.H8] !is Rook,
                queenSideRookHasMoved = black[Position.A8] !is Rook,
            )

            return CastlingState(
                mapOf(
                    Side.WHITE to whiteCastlingInfo,
                    Side.BLACK to blackCastlingInfo
                )
            )
        }
    }
}