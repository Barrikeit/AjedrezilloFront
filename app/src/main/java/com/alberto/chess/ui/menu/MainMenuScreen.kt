package com.alberto.chess.ui.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alberto.chess.ui.theme.ChessTheme
import com.alberto.chess.ui.theme.ComposeAppColorScheme
import com.alberto.chess.ui.theme.MainColorPalette

@Composable
fun MainMenuScreen(
    onNavigateToGame: () -> Unit,
    onNavigateToGame2: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MainColorPalette.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MenuScreenButton(
            text = "Play Game",
            onClickButton = onNavigateToGame
        )
        MenuScreenButton(
            text = "Puzzles",
            onClickButton = onNavigateToGame2
        )
    }
}

@Composable
fun MenuScreenButton(
    text: String,
    onClickButton: () -> Unit,
) {
    Button(
        onClick = onClickButton,
        colors = ButtonDefaults.buttonColors(
            containerColor = MainColorPalette.button,
            contentColor = MainColorPalette.text
        ),
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Text(text = text)
    }
}


@Preview(showBackground = true)
@Composable
fun ButtonScreenPreview() {
    ChessTheme(
        colorScheme = ComposeAppColorScheme.Standard,
    ) {
        MainMenuScreen(
            onNavigateToGame = {},
            onNavigateToGame2 = {},
        )
    }
}