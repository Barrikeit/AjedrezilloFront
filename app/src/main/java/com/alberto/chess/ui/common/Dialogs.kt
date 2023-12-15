package com.alberto.chess.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.alberto.chess.R
import com.alberto.chess.domain.model.pieces.Bishop
import com.alberto.chess.domain.model.pieces.Knight
import com.alberto.chess.domain.model.pieces.Piece
import com.alberto.chess.domain.model.pieces.Queen
import com.alberto.chess.domain.model.pieces.Rook
import com.alberto.chess.domain.model.pieces.Side
import com.alberto.chess.ui.game.components.Piece
import com.alberto.chess.ui.game.controller.GameController
import com.alberto.chess.ui.game.controller.GameStatus
import com.alberto.chess.ui.game.states.GameScreenState
import com.alberto.chess.ui.theme.MainColorPalette

@Composable
fun Dialogs(
    gamePlayState: GameScreenState,
    gameController: GameController,
) {
    ManagedGameDialog(
        gameScreenState = gamePlayState,
        gameController = gameController,
    )
    ManagedPromotionDialog(
        gameScreenState = gamePlayState,
        gameController = gameController
    )
}

@Composable
fun ManagedGameDialog(
    gameScreenState: GameScreenState,
    gameController: GameController,
) {
    if (gameScreenState.gameUiState.showGameDialog) {
        GameDialog(
            onDismiss = {
                gameController.closeGameDialog()
            },
            onNewGame = {
                gameController.closeGameDialog()
                gameController.reset()
            },
            onDraw = {
                gameController.closeGameDialog()
                gameScreenState.gameState.currentSnapshotState.gameStatus = GameStatus.DRAW
                gameController.reset()
            },
            onResignation = {
                gameController.closeGameDialog()
                gameScreenState.gameState.currentSnapshotState.gameStatus = GameStatus.RESIGNATION
                gameController.reset()
            },
        )
    }
}


@Composable
fun GameDialog(
    onDismiss: () -> Unit,
    onResignation: () -> Unit,
    onDraw: () -> Unit,
    onNewGame: () -> Unit,
) {
    ClickableListItemsDialog(
        onDismiss = onDismiss,
        items = listOf(
            stringResource(R.string.game_resignation) to onResignation,
            stringResource(R.string.game_draw) to onDraw,
            stringResource(R.string.game_new) to onNewGame,
        )
    )
}


@Composable
fun ClickableListItemsDialog(
    onDismiss: () -> Unit,
    items: List<Pair<String, () -> Unit>>
) {
    MaterialTheme {
        Dialog(
            onDismissRequest = onDismiss,
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            )
        ) {
            VerticalClickableList(items)
        }
    }
}

@Composable
private fun VerticalClickableList(items: List<Pair<String, () -> Unit>>) {
    Column(
        modifier = Modifier
            .background(
                color = MainColorPalette.grey1,
                shape = MaterialTheme.shapes.medium
            )
            .width(IntrinsicSize.Min)
            .padding(8.dp)
    ) {
        items.forEach { (text, onClick) ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = onClick),
            ) {
                Box(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = text,
                        color = MainColorPalette.text,
                    )
                }
            }
        }

    }
}

@Preview
@Composable
private fun GameDialogContent() {
    GameDialog(
        onDismiss = {},
        onResignation = {},
        onDraw = {},
        onNewGame = {},
    )
}


@Composable
fun ManagedPromotionDialog(
    gameScreenState: GameScreenState,
    gameController: GameController,
) {
    if (gameScreenState.gameUiState.showPromotionDialog) {
        PromotionDialog(gameController.toMove) {
            gameController.onPromotionPieceSelected(it)
        }
    }
}

@Composable
fun PromotionDialog(
    side: Side = Side.WHITE,
    onPieceSelected: (Piece) -> Unit,
) {
    MaterialTheme {
        Dialog(
            onDismissRequest = { },
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false
            )
        ) {
            PromotionDialogContent(side) {
                onPieceSelected(it)
            }
        }
    }
}

@Preview
@Composable
private fun PromotionDialogContent(
    side: Side = Side.WHITE,
    onClick: (Piece) -> Unit = {}
) {
    val promotionPieces = listOf(
        Queen(side),
        Rook(side),
        Bishop(side),
        Knight(side)
    )
    Column(
        modifier = Modifier
            .background(
                color = if (side == Side.WHITE)
                    MainColorPalette.grey1 else MainColorPalette.text,
                shape = MaterialTheme.shapes.medium
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        promotionPieces.forEach { piece ->
            Piece(
                piece = piece,
                squareSize = 48.dp,
                modifier = Modifier.clickable(onClick = { onClick(promotionPieces.first()) })
            )
        }
    }
}
