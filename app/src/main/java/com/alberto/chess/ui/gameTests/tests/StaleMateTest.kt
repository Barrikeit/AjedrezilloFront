package com.alberto.chess.ui.gameTests.tests

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.alberto.chess.domain.model.board.Board
import com.alberto.chess.domain.model.board.Position
import com.alberto.chess.domain.model.pieces.King
import com.alberto.chess.domain.model.pieces.Queen
import com.alberto.chess.domain.model.pieces.Side
import com.alberto.chess.ui.game.controller.GameController
import com.alberto.chess.ui.game.states.GameSnapshotState
import com.alberto.chess.ui.theme.ChessTheme

object StaleMateTest : Preset {
    override fun apply(gameController: GameController) {
        gameController.apply {
            reset(
                GameSnapshotState(
                    board = Board(
                        pieces = mapOf(
                            Position.A8 to King(Side.BLACK),
                            Position.G7 to Queen(Side.WHITE),
                            Position.E5 to King(Side.WHITE),
                        )
                    ),
                    turn = Side.WHITE
                )
            )
            applyMove(Position.G7, Position.C7)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StaleMatePreview() {
    ChessTheme {
        Test(StaleMateTest)
    }
}

