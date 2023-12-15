package com.alberto.chess.ui.common

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.alberto.chess.ui.theme.MainColorPalette

@Composable
fun LoadingSpinner() {
    MaterialTheme {
        Dialog(
            onDismissRequest = {},
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false
            )
        ) {
            CircularProgressIndicator(
                color = MainColorPalette.loading
            )
        }
    }
}

