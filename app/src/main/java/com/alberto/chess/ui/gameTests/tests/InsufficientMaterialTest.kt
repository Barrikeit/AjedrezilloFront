package com.alberto.chess.ui.gameTests.tests

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.alberto.chess.domain.model.board.Board
import com.alberto.chess.domain.model.board.Position
import com.alberto.chess.domain.model.pieces.Bishop
import com.alberto.chess.domain.model.pieces.King
import com.alberto.chess.domain.model.pieces.Rook
import com.alberto.chess.domain.model.pieces.Side
import com.alberto.chess.ui.game.controller.GameController
import com.alberto.chess.ui.game.states.GameSnapshotState
import com.alberto.chess.ui.theme.ChessTheme

object InsufficientMaterialTest : Preset {
    override fun apply(gameController: GameController) {
        gameController.apply {
            val board = Board(
                pieces = mapOf(
                    Position.A7 to King(Side.BLACK),
                    Position.G8 to Rook(Side.BLACK),
                    Position.G2 to King(Side.WHITE),
                    Position.D5 to Bishop(Side.WHITE),
                )
            )
            reset(
                GameSnapshotState(
                    board = board,
                    turn = Side.WHITE
                )
            )
            applyMove(Position.D5, Position.G8)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InsufficientMaterialPreview() {
    ChessTheme {
        Test(InsufficientMaterialTest)
    }
}

