package com.alberto.chess.ui.gameTests

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.alberto.chess.ui.game.controller.GameController
import com.alberto.chess.ui.game.controller.GameScreenEvent
import com.alberto.chess.ui.game.states.GameScreenState
import com.alberto.chess.ui.game.states.GameUiState
import com.alberto.chess.ui.game.states.withResolution
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GameScreenViewModel @Inject constructor() : ViewModel() {
    private val _state = mutableStateOf(GameScreenState())
    val state: State<GameScreenState> = _state

    private val gameController = GameController(
        getGameScreenState = { _state.value },
        setGameScreenState = { _state.value = it },
    )

    fun handleEvent(event: GameScreenEvent) {
        when (event) {
            else -> {}
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