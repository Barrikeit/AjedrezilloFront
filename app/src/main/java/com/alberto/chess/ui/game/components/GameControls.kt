package com.alberto.chess.ui.game.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.alberto.chess.R
import com.alberto.chess.ui.game.states.GameScreenState
import com.alberto.chess.ui.theme.MainColorPalette

@Composable
fun GameControls(
    gameScreenState: GameScreenState,
    onStepBack: () -> Unit,
    onStepForward: () -> Unit,
    onFlipBoard: () -> Unit,
    onOpenGameDialog: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {

        Button(
            onClick = onStepBack,
            enabled = gameScreenState.gameState.hasPrevIndex
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_left),
                tint = MainColorPalette.text,
                contentDescription = stringResource(R.string.action_previous_move)
            )
        }
        Spacer(Modifier.size(4.dp))
        Button(
            onClick = onStepForward,
            enabled = gameScreenState.gameState.hasNextIndex
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_right),
                tint = MainColorPalette.text,
                contentDescription = stringResource(R.string.action_next_move)
            )
        }
        Spacer(Modifier.size(4.dp))
        Button(
            onClick = onFlipBoard,
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_loop),
                tint = MainColorPalette.text,
                contentDescription = stringResource(R.string.action_flip)
            )
        }
        Spacer(Modifier.size(4.dp))
        Button(
            onClick = onOpenGameDialog,
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_menu),
                tint = MainColorPalette.text,
                contentDescription = stringResource(R.string.action_game_dialog)
            )
        }
    }
}