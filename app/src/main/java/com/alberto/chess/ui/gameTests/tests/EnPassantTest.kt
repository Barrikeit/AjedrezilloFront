package com.alberto.chess.ui.gameTests.tests

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.alberto.chess.domain.model.board.Position
import com.alberto.chess.ui.game.controller.GameController
import com.alberto.chess.ui.theme.ChessTheme

object EnPassantTest : Preset {
    override fun apply(gameController: GameController) {
        gameController.apply {
            applyMove(Position.E2, Position.E4)
            applyMove(Position.B8, Position.C6)
            applyMove(Position.E4, Position.E5)
            applyMove(Position.D7, Position.D5)
            onClick(Position.E5)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EnPassantPreview() {
    ChessTheme {
        Test(EnPassantTest)
    }
}

