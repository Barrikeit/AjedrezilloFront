package com.alberto.chess.ui.game.controller

import com.alberto.chess.domain.model.board.Position
import com.alberto.chess.domain.model.board.Square
import com.alberto.chess.domain.model.moves.BoardMove
import com.alberto.chess.domain.model.moves.Promotion
import com.alberto.chess.domain.model.moves.targetPositions
import com.alberto.chess.domain.model.pieces.Piece
import com.alberto.chess.domain.model.pieces.Queen
import com.alberto.chess.domain.model.pieces.Side
import com.alberto.chess.ui.game.states.GameMetaData
import com.alberto.chess.ui.game.states.GameScreenState
import com.alberto.chess.ui.game.states.GameSnapshotState
import com.alberto.chess.ui.game.states.PromotionState
import com.alberto.chess.ui.gameTests.tests.Preset

class GameController(
    val getGameScreenState: () -> GameScreenState,
    val setGameScreenState: ((GameScreenState) -> Unit)? = null,
    val preset: Preset? = null
) {
    init {
        preset?.let { applyPreset(it) }
    }

    private val gameScreenState: GameScreenState
        get() = getGameScreenState()

    private val gameSnapshotState: GameSnapshotState
        get() = gameScreenState.gameState.currentSnapshotState

    val toMove: Side
        get() = gameSnapshotState.turn

    fun reset(
        gameSnapshotState: GameSnapshotState = GameSnapshotState(),
        gameMetaData: GameMetaData = GameMetaData.createWithDefaults()
    ) {
        setGameScreenState?.invoke(
            GameScreenEventHandler(gameScreenState, GameScreenEvent.ResetTo(gameSnapshotState, gameMetaData))
        )
    }

    private fun applyPreset(preset: Preset) {
        reset()
        preset.apply(this)
    }


    private fun square(position: Position): Square =
        gameSnapshotState.board[position]

    private fun Position.hasOwnPiece() =
        square(this).hasPiece(gameSnapshotState.turn)

    fun onClick(position: Position) {
        if (gameSnapshotState.gameStatus != GameStatus.IN_PROGRESS)
            return
        if (position.hasOwnPiece()) {
            toggleSelectPosition(position)
        } else if (canMoveTo(position)) {
            val selectedPosition = gameScreenState.gameUiState.selectedPosition
            requireNotNull(selectedPosition)
            applyMove(selectedPosition, position)
        }
    }

    private fun toggleSelectPosition(position: Position) {
        setGameScreenState?.invoke(
            GameScreenEventHandler(gameScreenState, GameScreenEvent.ToggleSelectPosition(position))
        )
    }

    private fun canMoveTo(position: Position) =
        position in gameScreenState.gameUiState.possibleMoves().targetPositions()

    fun applyMove(from: Position, to: Position) {
        val boardMove = findBoardMove(from, to) ?: return
        applyMove(boardMove)
    }

    private fun applyMove(boardMove: BoardMove) {
        setGameScreenState?.invoke(
            GameScreenEventHandler(gameScreenState, GameScreenEvent.ApplyMove(boardMove))
        )
    }

    fun moves(): List<BoardMove> {
        return gameSnapshotState.board.pieces.values.flatMap { piece ->
            piece.pseudoLegalMoves(gameSnapshotState, false)
        }
    }

    fun moves(side: Side): List<BoardMove> {
        return moves().filter { it.piece.side == side }
    }

    private fun findBoardMove(from: Position, to: Position): BoardMove? {
        val legalMoves = gameSnapshotState
            .legalMovesFrom(from)
            .filter { it.to == to }

        return when {
            legalMoves.isEmpty() -> {
                throw IllegalArgumentException("No legal moves exist between $from -> $to")
            }

            legalMoves.size == 1 -> {
                legalMoves.first()
            }

            legalMoves.all { it.consequence is Promotion } -> {
                handlePromotion(to, legalMoves)
            }

            else -> {
                throw IllegalStateException("Legal moves: $legalMoves")
            }
        }
    }

    private fun handlePromotion(at: Position, legalMoves: List<BoardMove>): BoardMove? {
        var promotionState = gameScreenState.promotionState
        if (setGameScreenState == null && promotionState == PromotionState.None) {
            promotionState = PromotionState.ContinueWith(Queen(gameSnapshotState.turn))
        }

        when (val promotion = promotionState) {
            is PromotionState.None -> {
                setGameScreenState?.invoke(
                    GameScreenEventHandler(gameScreenState, GameScreenEvent.RequestPromotion(at))
                )
            }
            is PromotionState.Await -> {
                throw IllegalStateException()
            }
            is PromotionState.ContinueWith -> {
                return legalMoves.find { move ->
                    (move.consequence as Promotion).let {
                        it.piece::class == promotion.piece::class
                    }
                }
            }
        }
        return null
    }

    fun onPromotionPieceSelected(piece: Piece) {
        val state = gameScreenState.promotionState
        if (state !is PromotionState.Await) error("Not in expected state: $state")
        val position = state.position
        setGameScreenState?.invoke(
            GameScreenEventHandler(gameScreenState, GameScreenEvent.PromoteTo(piece))
        )
        onClick(position)
    }

    fun stepForward() {
        setGameScreenState?.invoke(
            GameScreenEventHandler(gameScreenState, GameScreenEvent.StepForward)
        )
    }

    fun stepBackward() {
        setGameScreenState?.invoke(
            GameScreenEventHandler(gameScreenState, GameScreenEvent.StepBackward)
        )
    }

    fun goToMove(index: Int) {
        setGameScreenState?.invoke(
            GameScreenEventHandler(gameScreenState, GameScreenEvent.GoToMove(index))
        )
    }

    fun openGameDialog() {
        setGameScreenState?.invoke(
            GameScreenEventHandler(gameScreenState, GameScreenEvent.OpenGameDialog)
        )
    }

    fun closeGameDialog() {
        setGameScreenState?.invoke(
            GameScreenEventHandler(gameScreenState, GameScreenEvent.CloseGameDialog)
        )
    }
}
