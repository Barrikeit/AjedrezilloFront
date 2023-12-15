package com.alberto.chess.domain.model.moves

import com.alberto.chess.domain.model.board.Position
import com.alberto.chess.domain.model.pieces.Pawn
import com.alberto.chess.domain.model.pieces.Piece
import java.util.EnumSet

data class BoardMove(
    val move: SimpleMove,
    val preMove: PreMove? = null,
    val consequence: Consequence? = null,
    val ambiguity: EnumSet<Ambiguity> = EnumSet.noneOf(Ambiguity::class.java)
) {
    val from: Position = move.from
    val to: Position = move.to
    val piece: Piece = move.piece

    override fun toString(): String =
        toString(useFigurineNotation = true)

    fun toString(useFigurineNotation: Boolean): String {
        if (move is KingSideCastle) return "O-O"
        if (move is QueenSideCastle) return "O-O-O"
        val isCapture = preMove is Capture
        val symbol = when {
            piece !is Pawn -> if (useFigurineNotation) piece.symbol else piece.textSymbol
            isCapture -> from.file().notation
            else -> ""
        }
        val file = if (ambiguity.contains(Ambiguity.AMBIGUOUS_FILE)) from.file().toString() else ""
        val rank = if (ambiguity.contains(Ambiguity.AMBIGUOUS_RANK)) from.rank.toString() else ""
        val capture = if (isCapture) "x" else ""
        val promotion = if (consequence is Promotion) "=${consequence.piece.textSymbol}" else ""

        return "$symbol$file$rank$capture$to$promotion"
    }

    enum class Ambiguity {
        AMBIGUOUS_FILE, AMBIGUOUS_RANK,
    }
}
