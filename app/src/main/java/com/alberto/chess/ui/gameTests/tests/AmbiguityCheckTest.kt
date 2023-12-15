package com.alberto.chess.ui.gameTests.tests

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.alberto.chess.domain.model.board.Board
import com.alberto.chess.domain.model.board.Position
import com.alberto.chess.domain.model.pieces.King
import com.alberto.chess.domain.model.pieces.Knight
import com.alberto.chess.domain.model.pieces.Pawn
import com.alberto.chess.domain.model.pieces.Queen
import com.alberto.chess.domain.model.pieces.Rook
import com.alberto.chess.domain.model.pieces.Side
import com.alberto.chess.ui.game.controller.GameController
import com.alberto.chess.ui.game.states.GameSnapshotState
import com.alberto.chess.ui.theme.ChessTheme

object AmbiguityCheckTest : Preset {
    override fun apply(gameController: GameController) {
        gameController.apply {
            val board = Board(
                pieces = mapOf(
                    Position.E2 to King(Side.BLACK),
                    Position.C3 to Pawn(Side.BLACK),
                    Position.H1 to King(Side.WHITE),
                    Position.E4 to Knight(Side.WHITE),
                    Position.A4 to Knight(Side.WHITE),
                    Position.A2 to Knight(Side.WHITE),
                    Position.B1 to Knight(Side.WHITE),
                    Position.D1 to Knight(Side.WHITE),
                    Position.B7 to Rook(Side.WHITE),
                    Position.C8 to Rook(Side.WHITE),
                    Position.F7 to Rook(Side.WHITE),
                    Position.G8 to Queen(Side.WHITE),
                    Position.H8 to Queen(Side.WHITE),
                    Position.H7 to Queen(Side.WHITE),
                )
            )
            reset(
                GameSnapshotState(
                    board = board,
                    turn = Side.WHITE
                )
            )
//            onClick(Position.E4)
//            onClick(Position.H8)
//            onClick(Position.B7)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AmbiguityCheckPreview() {
    ChessTheme {
        Test(AmbiguityCheckTest)
    }
}

