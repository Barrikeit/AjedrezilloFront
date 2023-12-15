package com.alberto.chess.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.alberto.chess.ui.common.Constants

sealed class Screen(
    val route: String,
    val title: String,
    val icon: ImageVector,
) {
    object MainScreen : Screen(
        route = Constants.MAIN_MENU_SCREEN_ROUTE,
        title = Constants.MAIN_MENU_SCREEN_TITLE,
        icon = Icons.Filled.Home
    )

    object GameScreen : Screen(
        route = Constants.GAME_SCREEN_ROUTE,
        title = Constants.GAME_SCREEN_TITLE,
        icon = Icons.Filled.Home
    )

    object GameScreen2 : Screen(
        route = Constants.GAME_SCREEN2_ROUTE,
        title = Constants.GAME_SCREEN2_TITLE,
        icon = Icons.Filled.Home
    )
}