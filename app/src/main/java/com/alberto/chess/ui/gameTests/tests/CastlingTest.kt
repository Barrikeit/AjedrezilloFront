package com.alberto.chess.ui.gameTests.tests

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.alberto.chess.domain.model.board.Position
import com.alberto.chess.ui.game.controller.GameController
import com.alberto.chess.ui.theme.ChessTheme

object CastlingTest : Preset {
    override fun apply(gameController: GameController) {
        gameController.apply {
            reset()
            applyMove(Position.E2, Position.E4)
            applyMove(Position.E7, Position.E5)
            applyMove(Position.D2, Position.D4)
            applyMove(Position.D7, Position.D5)
            applyMove(Position.B1, Position.C3)
            applyMove(Position.B8, Position.C6)
            applyMove(Position.G1, Position.F3)
            applyMove(Position.G8, Position.F6)
            applyMove(Position.C1, Position.E3)
            applyMove(Position.C8, Position.E6)
            applyMove(Position.F1, Position.D3)
            applyMove(Position.F8, Position.D6)
            applyMove(Position.D1, Position.D2)
            applyMove(Position.D8, Position.D7)
            onClick(Position.E1)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CastlingPreview() {
    ChessTheme {
        Test(CastlingTest)
    }
}

