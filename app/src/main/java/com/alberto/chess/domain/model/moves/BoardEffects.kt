package com.alberto.chess.domain.model.moves

import com.alberto.chess.domain.model.board.Board
import com.alberto.chess.domain.model.board.Position
import com.alberto.chess.domain.model.pieces.Piece

interface BoardEffect {
    val piece: Piece
    fun applyOn(board: Board): Board
}

interface SimpleMove : BoardEffect {
    val from: Position
    val to: Position
}

interface PreMove : BoardEffect
interface Consequence : BoardEffect

enum class MoveEffect {
    CHECK, CHECKMATE, DRAW
}

data class Move(
    override val piece: Piece,
    override val from: Position,
    override val to: Position
) : SimpleMove, Consequence {
    override fun applyOn(board: Board): Board =
        board.copy(
            pieces = board.pieces
                .minus(from)
                .plus(to to piece)
        )
}

data class Capture(
    override val piece: Piece,
    val position: Position
) : PreMove {
    override fun applyOn(board: Board): Board =
        board.copy(
            pieces = board.pieces.minus(position)
        )
}

data class Promotion(
    val position: Position,
    override val piece: Piece
) : Consequence {
    override fun applyOn(board: Board): Board =
        board.copy(
            pieces = board.pieces
                .minus(position)
                .plus(position to piece)
        )
}

data class KingSideCastle(
    override val piece: Piece,
    override val from: Position,
    override val to: Position
) : SimpleMove {

    override fun applyOn(board: Board): Board =
        board.copy(
            pieces = board.pieces
                .minus(from)
                .plus(to to piece)
        )
}

data class QueenSideCastle(
    override val piece: Piece,
    override val from: Position,
    override val to: Position
) : SimpleMove {
    override fun applyOn(board: Board): Board =
        board.copy(
            pieces = board.pieces
                .minus(from)
                .plus(to to piece)
        )
}