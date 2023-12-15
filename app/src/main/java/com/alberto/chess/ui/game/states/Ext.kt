package com.alberto.chess.ui.game.states

import com.alberto.chess.domain.model.board.Position
import com.alberto.chess.domain.model.moves.BoardMove
import com.alberto.chess.domain.model.pieces.Bishop
import com.alberto.chess.domain.model.pieces.King
import com.alberto.chess.domain.model.pieces.Knight
import com.alberto.chess.domain.model.pieces.Piece
import com.alberto.chess.domain.model.pieces.Side
import java.util.EnumSet

fun List<GameSnapshotState>.hasThreefoldRepetition(): Boolean =
    any { state -> count { it == state} > 2}

fun Map<Position, Piece>.hasInsufficientMaterial(): Boolean =
    when {
        size == 2 && hasWhiteKing() && hasBlackKing() -> true
        size == 3 && hasWhiteKing() && hasBlackKing() && hasBishop() -> true
        size == 3 && hasWhiteKing() && hasBlackKing() && hasKnight() -> true
        size == 4 && hasWhiteKing() && hasBlackKing() && hasBishopsOnSameColor() -> true
        else -> false
    }

private fun Map<Position, Piece>.hasWhiteKing(): Boolean =
    values.find { it.side == Side.WHITE && it is King } != null

private fun Map<Position, Piece>.hasBlackKing(): Boolean =
    values.find { it.side == Side.BLACK && it is King } != null

private fun Map<Position, Piece>.hasBishop(): Boolean =
    values.find { it is Bishop } != null

private fun Map<Position, Piece>.hasKnight(): Boolean =
    values.find { it is Knight } != null

private fun Map<Position, Piece>.hasBishopsOnSameColor(): Boolean {
    val bishops = filter { it.value is Bishop }

    return bishops.size > 1 && (bishops.all { it.key.isLightSquare() } || bishops.all { it.key.isDarkSquare() })
}

fun BoardMove.applyAmbiguity(gameSnapshotState: GameSnapshotState): BoardMove =
    gameSnapshotState.board
        .pieces(gameSnapshotState.turn)
        .filter { (_, piece) -> piece.textSymbol == this.piece.textSymbol }
        .flatMap { (_, piece) -> piece.pseudoLegalMoves(gameSnapshotState, false) }
        .filter { it.to == this.to }
        .map { it.from }
        .distinct() // promotion moves have same `from`, but are different per target piece, we don't need all of those
        .let { similarPiecePositions ->
            val ambiguity = EnumSet.noneOf(BoardMove.Ambiguity::class.java)
            when (similarPiecePositions.size) {
                1 -> this
                else -> {
                    val onSameFile = similarPiecePositions.filter { it.file == from.file }
                    if (onSameFile.size == 1) {
                        ambiguity.add(BoardMove.Ambiguity.AMBIGUOUS_FILE)
                    } else {
                        val onSameRank = similarPiecePositions.filter { it.rank == from.rank }
                        if (onSameRank.size == 1) {
                            ambiguity.add(BoardMove.Ambiguity.AMBIGUOUS_RANK)
                        } else {
                            ambiguity.add(BoardMove.Ambiguity.AMBIGUOUS_FILE)
                            ambiguity.add(BoardMove.Ambiguity.AMBIGUOUS_RANK)
                        }
                    }

                    copy(ambiguity = ambiguity)
                }
            }
        }
