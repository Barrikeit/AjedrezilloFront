package com.alberto.chess.domain.model.moves

import com.alberto.chess.domain.model.board.Position
import com.alberto.chess.domain.model.pieces.Offset
import com.alberto.chess.domain.model.pieces.Piece
import com.alberto.chess.domain.model.board.Board
import com.alberto.chess.domain.model.board.Square
import com.alberto.chess.ui.game.states.GameSnapshotState

fun List<BoardMove>.targetPositions(): List<Position> = map { it.to }

/**
 * Movimiento simple de una pieza
 */
fun Piece.singleMove(
    gameSnapshotState: GameSnapshotState,
    fileOffset: Int,
    rankOffset: Int
): BoardMove? {
    val board = gameSnapshotState.board
    val square = board.find(this) ?: return null
    val targetFile = square.file + fileOffset
    val targetRank = square.rank + + rankOffset
    val target = board[targetFile, targetRank] ?: return null
    return when {
        target.hasPiece(side) -> null
        else -> BoardMove(
            move = Move(
                piece = this,
                from = square.position,
                to = target.position
            ),
            preMove = when {
                target.isNotEmpty -> Capture(target.piece!!, target.position)
                else -> null
            }
        )
    }
}

/**
 * Todos los movimientos en todas las direcciones de una pieza
 */
fun Piece.lineMoves(
    gameSnapshotState: GameSnapshotState,
    directions: List<Offset>,
): List<BoardMove> {
    val moves = mutableListOf<BoardMove>()
    val board = gameSnapshotState.board
    val square = board.find(this) ?: return emptyList()
    directions.map {
        moves += lineMoves(board, square, it.fileOffset, it.rankOffset)
    }
    return moves
}

private fun lineMoves(
    board: Board,
    square: Square,
    deltaFile: Int,
    deltaRank: Int
): List<BoardMove> {
    requireNotNull(square.piece)
    val set = square.piece.side
    val moves = mutableListOf<BoardMove>()

    var i = 0
    while (true) {
        i++
        val targetFile = square.file + deltaFile * i
        val targetRank = square.rank + deltaRank * i
        val target = board[targetFile, targetRank] ?: break
        if (target.hasPiece(set)) {
            break
        }

        val move = Move(piece = square.piece, from = square.position, to = target.position)
        if (target.isEmpty) {
            moves += BoardMove(move)
            continue
        }
        if (target.hasPiece(set.opposite())) {
            moves += BoardMove(
                move = move,
                preMove = Capture(target.piece!!, target.position)
            )
            break
        }
    }

    return moves
}