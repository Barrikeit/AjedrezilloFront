package com.alberto.chess.ui.game.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alberto.chess.domain.model.board.Position
import com.alberto.chess.domain.model.moves.AppliedMove
import com.alberto.chess.ui.game.controller.GameController
import com.alberto.chess.ui.game.states.GameScreenState
import com.alberto.chess.ui.theme.ChessTheme
import com.alberto.chess.ui.theme.MainColorPalette

@Composable
fun MoveList(
    moves: List<AppliedMove>,
    selectedItemIndex: Int,
    onMoveSelected: (Int) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(36.dp)
            .background(MainColorPalette.grey3),
        contentAlignment = Alignment.CenterStart
    ) {
        val listState = rememberLazyListState()
        LazyRow(
            state = listState,
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            moves.forEachIndexed { index, move ->
                val isSelected = index == selectedItemIndex

                if (index % 2 == 0) {
                    item {
                        StepNumber(index / 2 + 1)
                        Spacer(modifier = Modifier.width(2.dp))
                    }
                }

                item("$index$isSelected") {
                    Move(move, isSelected) { onMoveSelected(index) }
                }

                if (index % 2 == 1) {
                    item {
                        Spacer(modifier = Modifier.width(10.dp))
                    }
                }
            }
        }

        LaunchedEffect(moves, selectedItemIndex) {
            if (moves.isNotEmpty()) {
                listState.animateScrollToItem(moves.size - 1)
            }
        }
    }
}

@Composable
private fun StepNumber(stepNumber: Int) {
    Text(
        text = "$stepNumber.",
        color = MainColorPalette.text,
        fontWeight = FontWeight.Bold
    )
}

@Composable
private fun Move(
    move: AppliedMove,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    Text(
        text = move.toString(),
        color = MainColorPalette.text,
        modifier = (if (isSelected) modifier.pill(move.effect != null) else modifier)
            .padding(start = 3.dp, end = 3.dp)
            .clickable(onClick = onClick)
    )
}

private fun Modifier.pill(isHighlighted: Boolean) =
    background(
        color = if (isHighlighted) MainColorPalette.error else MainColorPalette.grey1,
        shape = RoundedCornerShape(6.dp)
    )

@Preview
@Composable
fun MovesPreview() {
    ChessTheme {
        var gameScreenState = GameScreenState()
        GameController({ gameScreenState } , { gameScreenState = it }).apply {
            applyMove(Position.E2, Position.E4)
            applyMove(Position.D7, Position.D5)
            applyMove(Position.D2, Position.D3)
            applyMove(Position.E7, Position.E6)
            applyMove(Position.B1, Position.C3)
            applyMove(Position.F8, Position.B4)
            applyMove(Position.C1, Position.G5)
            applyMove(Position.B4, Position.C3)
            applyMove(Position.G5, Position.D2)
        }

        MoveList(
            moves = gameScreenState.gameState.moves(),
            selectedItemIndex = gameScreenState.gameState.currentIndex - 1
        ) {}
    }
}