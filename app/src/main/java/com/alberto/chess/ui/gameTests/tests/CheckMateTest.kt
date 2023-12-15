package com.alberto.chess.ui.gameTests.tests

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.alberto.chess.domain.model.board.Position
import com.alberto.chess.ui.game.controller.GameController
import com.alberto.chess.ui.theme.ChessTheme

object CheckMateInOneMoveTest : Preset {
    override fun apply(gameController: GameController) {
        gameController.apply {
            applyMove(Position.E2, Position.E4)
            applyMove(Position.E7, Position.E5)
            applyMove(Position.D1, Position.H5)
            applyMove(Position.B8, Position.C6)
            applyMove(Position.F1, Position.C4)
            applyMove(Position.F8, Position.C5)
            onClick(Position.H5)
            applyMove(Position.H5, Position.F7)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CheckMatePreview() {
    ChessTheme {
        Test(CheckMateInOneMoveTest)
    }
}

