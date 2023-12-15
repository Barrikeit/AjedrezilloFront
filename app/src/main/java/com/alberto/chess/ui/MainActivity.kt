package com.alberto.chess.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.alberto.chess.ui.navigation.Navigation
import com.alberto.chess.ui.theme.ChessTheme
import com.alberto.chess.ui.theme.ComposeAppColorScheme
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag("Chess").d("View Created")
        setContent {
            val navController = rememberNavController()
            ChessTheme(
                colorScheme = ComposeAppColorScheme.Standard,
            ) {
                Navigation(navController = navController)
            }
        }
    }
}
