package com.alberto.chess.ui.game

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.alberto.chess.domain.model.board.Position
import com.alberto.chess.domain.model.pieces.Side
import com.alberto.chess.ui.common.Dialogs
import com.alberto.chess.ui.game.components.Board
import com.alberto.chess.ui.game.components.CapturedPieces
import com.alberto.chess.ui.game.components.GameControls
import com.alberto.chess.ui.game.components.MoveList
import com.alberto.chess.ui.game.components.Status
import com.alberto.chess.ui.game.controller.GameController
import com.alberto.chess.ui.game.states.GameScreenState
import com.alberto.chess.ui.gameTests.tests.Preset
import com.alberto.chess.ui.theme.BoardColorPalette
import com.alberto.chess.ui.theme.ChessTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(
    state: GameScreenState,
    preset: Preset? = null
) {
    var isFlipped by remember { mutableStateOf(false) }
    val gameScreenState = remember { mutableStateOf(state) }

    val gameController = remember {
        GameController(
            getGameScreenState = gameScreenState::value,
            setGameScreenState = { gameScreenState.value = it },
            preset = preset
        )
    }
    Scaffold(
        topBar = {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Status(gameScreenState.value.gameState)
            }
        },
        bottomBar = {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                GameControls(
                    gameScreenState = gameScreenState.value,
                    onStepBack = { gameController.stepBackward() },
                    onStepForward = { gameController.stepForward() },
                    onFlipBoard = { isFlipped = !isFlipped },
                    onOpenGameDialog = { gameController.openGameDialog() }
                )
                MoveList(
                    moves = gameScreenState.value.gameState.moves(),
                    selectedItemIndex = gameScreenState.value.gameState.currentIndex - 1,
                ) {
                    gameController.goToMove(it)
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BoardColorPalette.backGround)
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
        ) {
            CapturedPieces(
                gameState = gameScreenState.value.gameState,
                capturedBy = if (isFlipped) Side.WHITE else Side.BLACK,
                arrangement = Arrangement.Start,
                scoreAlignment = Alignment.End,
            )
            Board(
                gameScreenState = gameScreenState.value,
                gameController = gameController,
                isFlipped = isFlipped
            )
            CapturedPieces(
                gameState = gameScreenState.value.gameState,
                capturedBy = if (isFlipped) Side.BLACK else Side.WHITE,
                arrangement = Arrangement.End,
                scoreAlignment = Alignment.Start
            )
        }
        Dialogs(
            gamePlayState = gameScreenState.value,
            gameController = gameController,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GamePreview() {
    ChessTheme {
        var gameScreenState = GameScreenState()
        GameController(
            { gameScreenState },
            { gameScreenState = it }
        ).apply {
            applyMove(Position.E2, Position.E4)
            applyMove(Position.D7, Position.D5)
            applyMove(Position.D2, Position.D3)
            applyMove(Position.E7, Position.E6)
            applyMove(Position.B1, Position.C3)
            applyMove(Position.F8, Position.B4)
            applyMove(Position.C1, Position.G5)
            applyMove(Position.B4, Position.C3)
            onClick(Position.A1)
            onClick(Position.G5)
            applyMove(Position.G5, Position.D2)
            applyMove(Position.C3, Position.D2)
            applyMove(Position.D1, Position.D2)
            applyMove(Position.G8, Position.F6)
            onClick(Position.E1)
            applyMove(Position.E1, Position.C1)
            onClick(Position.E8)
            applyMove(Position.E8, Position.G8)
            onClick(Position.D2)
        }

        GameScreen(state = gameScreenState)
    }
}