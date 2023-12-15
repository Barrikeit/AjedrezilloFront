package com.alberto.chess.ui.game.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alberto.chess.domain.model.pieces.Bishop
import com.alberto.chess.domain.model.pieces.Knight
import com.alberto.chess.domain.model.pieces.Pawn
import com.alberto.chess.domain.model.pieces.Piece
import com.alberto.chess.domain.model.pieces.Queen
import com.alberto.chess.domain.model.pieces.Rook
import com.alberto.chess.domain.model.pieces.Side
import com.alberto.chess.ui.game.states.GameMetaData
import com.alberto.chess.ui.game.states.GameSnapshotState
import com.alberto.chess.ui.game.states.GameState
import com.alberto.chess.ui.theme.ChessTheme
import com.alberto.chess.ui.theme.MainColorPalette
import kotlin.math.absoluteValue

@Composable
fun CapturedPieces(
    gameState: GameState,
    capturedBy: Side,
    arrangement: Arrangement.Horizontal,
    scoreAlignment: Alignment.Horizontal,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(36.dp)
            .padding(top = 2.dp, bottom = 2.dp)
            .background(MainColorPalette.background)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp),
            horizontalArrangement = arrangement,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val capturedPieces = gameState.currentSnapshotState.capturedPieces
                .filter { it.side == capturedBy.opposite() }
                .sortedWith { t1, t2 ->
                    if (t1.value == t2.value) t1.symbol.hashCode() - t2.symbol.hashCode()
                    else t1.value - t2.value
                }

            val score = gameState.currentSnapshotState.score
            val displayScore = (capturedBy == Side.BLACK && score < 0) || (capturedBy == Side.WHITE && score > 0)
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (scoreAlignment == Alignment.Start && displayScore) {
                    Score(score = score)
                }
                CapturedPieceList(
                    capturedPieces = capturedPieces,
                )
                if (scoreAlignment == Alignment.End && displayScore) {
                    Score(score = score)
                }
            }
        }
    }
}

@Composable
private fun CapturedPieceList(
    capturedPieces: List<Piece>
) {
    val stringBuilder = StringBuilder()
    capturedPieces.forEach { piece ->
        stringBuilder.append(piece.symbol)
    }

    Text(
        text = stringBuilder.toString(),
        color = MainColorPalette.text,
        fontSize = 20.sp
    )
}

@Composable
private fun Score(score: Int) {
    Text(
        text = "+${score.absoluteValue}",
        color = MainColorPalette.text,
        fontSize = 12.sp,
        modifier = Modifier.padding(
            start = 8.dp,
            end = 8.dp
        ),
    )
}

@Preview
@Composable
fun TakenPiecesPreview() {
    ChessTheme {
        CapturedPieces(
            gameState = GameState(
                gameMetaData = GameMetaData.createWithDefaults(),
                states = listOf(
                    GameSnapshotState(
                        capturedPieces = listOf(
                            Pawn(Side.WHITE),
                            Pawn(Side.WHITE),
                            Pawn(Side.WHITE),
                            Pawn(Side.WHITE),
                            Knight(Side.WHITE),
                            Knight(Side.WHITE),
                            Bishop(Side.WHITE),
                            Queen(Side.WHITE),

                            Pawn(Side.BLACK),
                            Pawn(Side.BLACK),
                            Pawn(Side.BLACK),
                            Pawn(Side.BLACK),
                            Knight(Side.BLACK),
                            Rook(Side.BLACK),
                        )
                    )
                )
            ),
            capturedBy = Side.BLACK,
            arrangement = Arrangement.Start,
            scoreAlignment = Alignment.End,
        )
    }
}
