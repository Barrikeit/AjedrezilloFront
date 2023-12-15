package com.alberto.chess.ui.game.states

import com.alberto.chess.domain.model.board.Board
import com.alberto.chess.domain.model.board.Position
import com.alberto.chess.domain.model.moves.AppliedMove
import com.alberto.chess.domain.model.moves.BoardMove
import com.alberto.chess.domain.model.moves.Capture
import com.alberto.chess.domain.model.moves.MoveEffect
import com.alberto.chess.domain.model.moves.targetPositions
import com.alberto.chess.domain.model.pieces.King
import com.alberto.chess.domain.model.pieces.Piece
import com.alberto.chess.domain.model.pieces.Side
import com.alberto.chess.ui.game.controller.GameStatus

/**
 * Representa el estado de la partida en un momento dado.
 * El tablero, tiene un map de  las casillas ocupadas por piezas.
 * Quien tiene el turno.
 * El estado de la partida.
 * Las piezas capturadas.
 * La informacion necesaria para el enroque, de cada bando, si el rey se ha movido, si la torre de lado largo o de lado corto se ha movido.
 * el ultimo movimiento realizado.
 * el movimiento actual.
 */
data class GameSnapshotState(
    val board: Board = Board(),
    val turn: Side = Side.WHITE,
    var gameStatus: GameStatus = GameStatus.IN_PROGRESS,
    val capturedPieces: List<Piece> = emptyList(),
    val castlingState: CastlingState = CastlingState.from(board),
    val lastMove: AppliedMove? = null,
    val move: AppliedMove? = null
) {
    val score: Int = board.pieces.values.sumOf { it.value * if (it.side == Side.WHITE) 1 else -1 }

    fun hasCheck(): Boolean =
        hasCheckFor(turn)

    fun hasCheckFor(side: Side): Boolean {
        val position = board.find<King>(side).firstOrNull()?.position ?: return false
        return board.pieces.any { (_, piece) ->
            val otherPieceCaptures: List<BoardMove> = piece.pseudoLegalMoves(this, true)
                .filter { it.preMove is Capture }

            position in otherPieceCaptures.targetPositions()
        }
    }

    fun hasCheckFor(position: Position): Boolean =
        board.pieces.any { (_, piece) ->
            val otherPieceCaptures: List<BoardMove> = piece.pseudoLegalMoves(this, true)
                .filter { it.preMove is Capture }

            position in otherPieceCaptures.targetPositions()
        }

    fun legalMovesFrom(position: Position): List<BoardMove> =
        board[position]
            .piece
            ?.pseudoLegalMoves(this, false)
            ?.applyCheckConstraints()
            ?: emptyList()

    private fun List<BoardMove>.applyCheckConstraints(): List<BoardMove> = filter { move -> !tempGameState(move).hasCheckFor(move.piece.side) }

    fun calculateAppliedMove(
        boardMove: BoardMove,
        statesSoFar: List<GameSnapshotState>
    ): GameStateTransition {
        val tempNewGameState = tempGameState(boardMove)
        val validMoves = with(tempNewGameState) {
            board.pieces(turn).filter { (position, _) ->
                tempNewGameState.legalMovesFrom(position).isNotEmpty()
            }
        }
        val isCheck = tempNewGameState.hasCheck()
        val isCheckNoMate = validMoves.isNotEmpty() && isCheck
        val isCheckMate = validMoves.isEmpty() && isCheck
        val isStaleMate = validMoves.isEmpty() && !isCheck
        val insufficientMaterial = tempNewGameState.board.pieces.hasInsufficientMaterial()
        val threefoldRepetition = (statesSoFar + tempNewGameState).hasThreefoldRepetition()

        val appliedMove = AppliedMove(
            boardMove = boardMove.applyAmbiguity(this),
            effect = when {
                isCheckNoMate -> MoveEffect.CHECK
                isCheckMate -> MoveEffect.CHECKMATE
                isStaleMate -> MoveEffect.DRAW
                insufficientMaterial -> MoveEffect.DRAW
                threefoldRepetition -> MoveEffect.DRAW
                else -> null
            },
        )

        return GameStateTransition(
            fromGameState = this.copy(
                move = appliedMove
            ),
            toGameState = tempNewGameState.copy(
                gameStatus = when {
                    isCheckMate -> GameStatus.CHECKMATE
                    isStaleMate -> GameStatus.STALEMATE
                    threefoldRepetition -> GameStatus.DRAW_BY_REPETITION
                    insufficientMaterial -> GameStatus.INSUFFICIENT_MATERIAL
                    else -> GameStatus.IN_PROGRESS
                },
                move = null,
                lastMove = appliedMove,
                capturedPieces = (boardMove.preMove as? Capture)?.let { capturedPieces + it.piece }
                    ?: capturedPieces,
                castlingState = castlingState.apply(boardMove),
            ),
            move = appliedMove,
        )
    }

    private fun tempGameState(boardMove: BoardMove): GameSnapshotState {
        val updatedBoard = board
            .apply(boardMove.preMove)
            .apply(boardMove.move)
            .apply(boardMove.consequence)

        return copy(
            board = updatedBoard,
            turn = turn.opposite(),
            move = null,
            lastMove = AppliedMove(
                boardMove = boardMove,
                effect = null
            )
        )
    }
}