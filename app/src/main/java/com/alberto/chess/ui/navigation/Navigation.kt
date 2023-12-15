package com.alberto.chess.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.alberto.chess.ui.common.StatusBar
import com.alberto.chess.ui.game.GameScreen
import com.alberto.chess.ui.game.states.GameScreenState
import com.alberto.chess.ui.gameTests.Game
import com.alberto.chess.ui.gameTests.GameScreenViewModel
import com.alberto.chess.ui.menu.MainMenuScreen
import com.alberto.chess.ui.theme.MainColorPalette
import timber.log.Timber

@Composable
fun Navigation(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = Screen.MainScreen.route,
    ) {
        composable(
            route = Screen.MainScreen.route,
        ) {
            StatusBar(color = MainColorPalette.background)
            MainMenuScreen(
                onNavigateToGame = { navController.navigate(Screen.GameScreen.route) },
                onNavigateToGame2 = { navController.navigate(Screen.GameScreen.route) },
            )
        }
        composable(
            route = Screen.GameScreen.route,
        ) {
            GameScreen(state = GameScreenState(),) {}
        }
        composable(
            route = Screen.GameScreen2.route,
        ) {
            Timber.tag("Chess").d("Socket client connecting to addr:port ...")
            val viewModel: GameScreenViewModel = hiltViewModel()
            Game(viewModel = viewModel)
        }
    }
}