package com.alberto.chess.ui.gameTests.tests

import androidx.compose.runtime.Composable
import com.alberto.chess.ui.game.GameScreen
import com.alberto.chess.ui.game.controller.GameController
import com.alberto.chess.ui.game.states.GameScreenState

@Composable
fun Test(
    preset: Preset
) {
    GameScreen(
        state = GameScreenState(),
        preset = preset
    )
}

fun interface Preset {
    fun apply(gameController: GameController)
}