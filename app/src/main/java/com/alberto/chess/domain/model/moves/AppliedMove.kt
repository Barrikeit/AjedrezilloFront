package com.alberto.chess.domain.model.moves

import com.alberto.chess.domain.model.board.Position
import com.alberto.chess.domain.model.pieces.Piece
import com.alberto.chess.domain.model.pieces.Side

/**
 * Un movimiento aplicado al tablero.
 */
data class AppliedMove(
    val boardMove: BoardMove,
    val effect: MoveEffect? = null
) {
    val move: SimpleMove = boardMove.move
    val from: Position = move.from
    val to: Position = move.to
    val piece: Piece = move.piece

    override fun toString(): String =
        toString(
            useFigurineNotation = true,
            includeResult = true
        )

    fun toString(
        useFigurineNotation: Boolean,
        includeResult: Boolean
    ): String {
        val postFix = when {
            effect == MoveEffect.CHECK -> "+"
            includeResult && effect == MoveEffect.CHECKMATE -> "#  ${if (boardMove.move.piece.side == Side.WHITE) "1-0" else "0-1"}"
            includeResult && effect == MoveEffect.DRAW -> "  ½ - ½"
            else -> ""
        }
        return "${boardMove.toString(useFigurineNotation)}$postFix"
    }
}
