package com.alberto.chess.ui.game.controller

import com.alberto.chess.ui.game.states.GameScreenState
import com.alberto.chess.ui.game.states.GameState
import com.alberto.chess.ui.game.states.GameUiState
import com.alberto.chess.ui.game.states.PromotionState
import com.alberto.chess.ui.game.states.withResolution

object GameScreenEventHandler {
    operator fun invoke(
        gameScreenState: GameScreenState,
        gameScreenEvent: GameScreenEvent
    ): GameScreenState {
        val gameState = gameScreenState.gameState
        val currentSnapshotState = gameState.currentSnapshotState

        return when (gameScreenEvent) {
            is GameScreenEvent.StepForward -> gameScreenState.stepBy(1)
            is GameScreenEvent.StepBackward -> gameScreenState.stepBy(-1)
            is GameScreenEvent.GoToMove -> gameScreenState.goToSnapshot(gameScreenEvent.moveIndex + 1)
            is GameScreenEvent.ResetTo -> {
                GameScreenState(
                    gameState = GameState(
                        gameMetaData = gameScreenEvent.gameMetaData,
                        states = listOf(gameScreenEvent.gameSnapshotState)
                    ),
                )
            }
            is GameScreenEvent.ToggleSelectPosition -> {
                if (gameScreenState.gameUiState.selectedPosition == gameScreenEvent.position) {
                    gameScreenState.copy(
                        gameUiState = gameScreenState.gameUiState.copy(
                            selectedPosition = null
                        )
                    )
                } else {
                    gameScreenState.copy(
                        gameUiState = gameScreenState.gameUiState.copy(
                            selectedPosition = gameScreenEvent.position
                        )
                    )
                }
            }
            is GameScreenEvent.ApplyMove -> {
                var states = gameState.states.toMutableList()
                val currentIndex = gameState.currentIndex
                val transition = currentSnapshotState.calculateAppliedMove(
                    boardMove = gameScreenEvent.boardMove,
                    statesSoFar = states.subList(0, currentIndex + 1)
                )

                states[currentIndex] = transition.fromGameState
                states = states.subList(0, currentIndex + 1)
                states.add(transition.toGameState)

                gameScreenState.copy(
                    gameState = gameState.copy(
                        states = states,
                        currentIndex = states.lastIndex,
                        lastActiveState = currentSnapshotState,
                        gameMetaData = gameState.gameMetaData.withResolution(
                            gameStatus = transition.toGameState.gameStatus,
                            lastMoveBy = transition.fromGameState.turn
                        )
                    ),
                    gameUiState = GameUiState(transition.toGameState),
                    promotionState = PromotionState.None
                )
            }
            is GameScreenEvent.OpenGameDialog -> {
                gameScreenState.copy(
                    gameUiState = gameScreenState.gameUiState.copy(
                        showGameDialog = true
                    )
                )
            }
            is GameScreenEvent.CloseGameDialog -> {
                gameScreenState.copy(
                    gameUiState = gameScreenState.gameUiState.copy(
                        showGameDialog = false
                    )
                )
            }
            is GameScreenEvent.RequestPromotion -> {
                gameScreenState.copy(
                    gameUiState = gameScreenState.gameUiState.copy(
                        showPromotionDialog = true
                    ),
                    promotionState = PromotionState.Await(gameScreenEvent.at)
                )
            }
            is GameScreenEvent.PromoteTo -> {
                gameScreenState.copy(
                    gameUiState = gameScreenState.gameUiState.copy(
                        showPromotionDialog = false
                    ),
                    promotionState = PromotionState.ContinueWith(gameScreenEvent.piece)
                )
            }
        }
    }

    private fun GameScreenState.stepBy(step: Int): GameScreenState {
        val newIndex = gameState.currentIndex + step
        if (newIndex !in 0..gameState.states.lastIndex) return this
        return goToSnapshot(newIndex)
    }

    private fun GameScreenState.goToSnapshot(index: Int): GameScreenState {
        val states = gameState.states
        if (index !in 0..states.lastIndex) return this
        val newSnapshotState = states[index]
        return copy(
            gameState = gameState.copy(
                currentIndex = index,
                lastActiveState = newSnapshotState,
                gameMetaData = gameState.gameMetaData.withResolution(
                    gameStatus = newSnapshotState.gameStatus,
                    lastMoveBy = newSnapshotState.turn
                )
            ),
            gameUiState = GameUiState(newSnapshotState)
        )
    }
}
