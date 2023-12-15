package com.alberto.chess.ui.gameTests.tests

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.alberto.chess.domain.model.board.Board
import com.alberto.chess.domain.model.board.Position
import com.alberto.chess.domain.model.pieces.King
import com.alberto.chess.domain.model.pieces.Knight
import com.alberto.chess.domain.model.pieces.Pawn
import com.alberto.chess.domain.model.pieces.Side
import com.alberto.chess.ui.game.controller.GameController
import com.alberto.chess.ui.game.states.GameSnapshotState
import com.alberto.chess.ui.theme.ChessTheme

object PromotionTest : Preset {
    override fun apply(gameController: GameController) {
        gameController.apply {
            val board = Board(
                pieces = mapOf(
                    Position.A7 to King(Side.BLACK),
                    Position.F8 to Knight(Side.BLACK),
                    Position.G2 to King(Side.WHITE),
                    Position.G7 to Pawn(Side.WHITE),
                )
            )
            reset(
                GameSnapshotState(
                    board = board,
                    turn = Side.WHITE
                )
            )
            applyMove(Position.G7, Position.F8)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PromotionPreview() {
    ChessTheme {
        Test(PromotionTest)
    }
}

