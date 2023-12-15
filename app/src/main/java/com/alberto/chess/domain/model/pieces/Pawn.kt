package com.alberto.chess.domain.model.pieces

import com.alberto.chess.domain.model.board.Board
import com.alberto.chess.domain.model.board.Square
import com.alberto.chess.domain.model.moves.BoardMove
import com.alberto.chess.domain.model.moves.Capture
import com.alberto.chess.domain.model.moves.Move
import com.alberto.chess.domain.model.moves.Promotion
import com.alberto.chess.ui.game.states.GameSnapshotState

class Pawn(
    override val side: Side,
) : Piece {
    override val value: Int = 1
    override val asset: Int =
        when (side) {
            Side.WHITE -> com.alberto.chess.R.drawable.ic_pawn_white
            Side.BLACK -> com.alberto.chess.R.drawable.ic_pawn_black
        }
    override val symbol: String =
        when (side) {
            Side.WHITE -> Symbols.WHITE_PAWN.fanSymbol
            Side.BLACK -> Symbols.BLACK_PAWN.fanSymbol
        }
    override val textSymbol: String =
        when (side) {
            Side.WHITE -> Symbols.WHITE_PAWN.fenSymbol
            Side.BLACK -> Symbols.BLACK_PAWN.fenSymbol
        }

    override fun pseudoLegalMoves(
        gameSnapshotState: GameSnapshotState,
        check: Boolean
    ): List<BoardMove> {
        val board = gameSnapshotState.board
        val square = board.find(this) ?: return emptyList()
        val moves = mutableListOf<BoardMove>()

        advanceSingle(board, square)?.let { moves += it }
        advanceTwoSquares(board, square)?.let { moves += it }
        captureDiagonalLeft(board, square)?.let { moves += it }
        captureDiagonalRight(board, square)?.let { moves += it }
        enPassantCaptureLeft(gameSnapshotState, square)?.let { moves += it }
        enPassantCaptureRight(gameSnapshotState, square)?.let { moves += it }

        return moves.flatMap {
            it.checkForPromotion()
        }
    }

    private fun advanceSingle(
        board: Board,
        square: Square
    ): BoardMove? {
        val rankOffset = if (side == Side.WHITE) 1 else -1
        val target = board[square.file, square.rank + rankOffset]
        return if (target?.isEmpty == true) BoardMove(
            move = Move(this, square.position, target.position)
        ) else null
    }

    private fun advanceTwoSquares(
        board: Board,
        square: Square
    ): BoardMove? {
        if ((side == Side.WHITE && square.rank == 2) || (side == Side.BLACK && square.rank == 7)) {
            val rankOffset1 = if (side == Side.WHITE) 1 else -1
            val rankOffset2 = if (side == Side.WHITE) 2 else -2
            val target1 = board[square.file, square.rank + rankOffset1]
            val target2 = board[square.file, square.rank + rankOffset2]
            return if (target1?.isEmpty == true && target2?.isEmpty == true) BoardMove(
                move = Move(this, square.position, target2.position)
            ) else null
        }
        return null
    }

    private fun captureDiagonalLeft(
        board: Board,
        square: Square
    ): BoardMove? = captureDiagonal(board, square, -1)

    private fun captureDiagonalRight(
        board: Board,
        square: Square
    ): BoardMove? = captureDiagonal(board, square, 1)

    private fun captureDiagonal(
        board: Board,
        square: Square,
        fileOffset: Int
    ): BoardMove? {
        val rankOffset = if (side == Side.WHITE) 1 else -1
        val target = board[square.file + fileOffset, square.rank + rankOffset]
        return if (target?.hasPiece(side.opposite()) == true) BoardMove(
            move = Move(this, square.position, target.position),
            preMove = Capture(target.piece!!, target.position)
        ) else null
    }

    private fun enPassantCaptureLeft(
        gameSnapshotState: GameSnapshotState,
        square: Square
    ): BoardMove? = enPassantDiagonal(gameSnapshotState, square, -1)

    private fun enPassantCaptureRight(
        gameSnapshotState: GameSnapshotState,
        square: Square
    ): BoardMove? = enPassantDiagonal(gameSnapshotState, square, 1)

    private fun enPassantDiagonal(
        gameSnapshotState: GameSnapshotState,
        square: Square,
        fileOffset: Int
    ): BoardMove? {
        if (square.position.rank != if (side == Side.WHITE) 5 else 4) return null
        val lastMove = gameSnapshotState.lastMove ?: return null
        if (lastMove.piece !is Pawn) return null
        val fromInitialSquare = (lastMove.from.rank == if (side == Side.WHITE) 7 else 2)
        val twoSquareMove = (lastMove.to.rank == square.position.rank)
        val isOnNextFile = lastMove.to.file == square.file + fileOffset

        return if (fromInitialSquare && twoSquareMove && isOnNextFile) {
            val rankOffset = if (side == Side.WHITE) 1 else -1
            val enPassantTarget =
                gameSnapshotState.board[square.file + fileOffset, square.rank + rankOffset]
            val capturedPieceSquare =
                gameSnapshotState.board[square.file + fileOffset, square.rank]
            requireNotNull(enPassantTarget)
            requireNotNull(capturedPieceSquare)

            BoardMove(
                move = Move(this, square.position, enPassantTarget.position),
                preMove = Capture(capturedPieceSquare.piece!!, capturedPieceSquare.position)
            )
        } else null
    }
}

private fun BoardMove.checkForPromotion(): List<BoardMove> =
    if (move.to.rank == if (piece.side == Side.WHITE) 8 else 1) {
        listOf(
            copy(consequence = Promotion(move.to, Queen(piece.side))),
            copy(consequence = Promotion(move.to, Rook(piece.side))),
            copy(consequence = Promotion(move.to, Bishop(piece.side))),
            copy(consequence = Promotion(move.to, Knight(piece.side))),
        )
    } else listOf(this)