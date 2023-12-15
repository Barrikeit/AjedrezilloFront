package com.alberto.chess.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

interface ComposeAppColorScheme {
    val materialColorScheme: ColorScheme
    val mainColors: MainColors
    val boardColors: BoardColors

    data class MainColors(
        val background: Color,
        val button: Color,
        val text: Color,
        val loading: Color,
        val grey1: Color,
        val grey2: Color,
        val grey3: Color,
        val grey4: Color,
        val alert: Color,
        val error: Color,
    )

    data class BoardColors(
        val lightSquareColor: Color,
        val legalMoveLight: Color,
        val highlightedOnLight: Color,
        val darkSquareColor: Color,
        val legalMoveDark: Color,
        val highlightedOnDark: Color,
        val alert: Color,
        val error: Color,
    )

    object Standard : ComposeAppColorScheme {
        override val materialColorScheme = darkColorScheme(
            primary = ColorPalette.primary,
            onPrimary = ColorPalette.onPrimary,
            primaryContainer = ColorPalette.primaryContainer,
            onPrimaryContainer = ColorPalette.onPrimaryContainer,
            inversePrimary = ColorPalette.inversePrimary,
            secondary = ColorPalette.secondary,
            secondaryContainer = ColorPalette.secondaryContainer,
            onSecondary = ColorPalette.onSecondary,
            onSecondaryContainer = ColorPalette.onSecondaryContainer,
            tertiary = ColorPalette.tertiary,
            tertiaryContainer = ColorPalette.tertiaryContainer,
            onTertiary = ColorPalette.onTertiary,
            onTertiaryContainer = ColorPalette.onTertiaryContainer,
            background = ColorPalette.background,
            onBackground = ColorPalette.onBackground,
            surface = ColorPalette.surface,
            onSurface = ColorPalette.onSurface,
            surfaceVariant = ColorPalette.surfaceVariant,
            onSurfaceVariant = ColorPalette.onSurfaceVariant,
            surfaceTint = ColorPalette.surfaceTint,
            inverseSurface = ColorPalette.inverseSurface,
            inverseOnSurface = ColorPalette.inverseOnSurface,
            error = ColorPalette.error,
            onError = ColorPalette.onError,
            errorContainer = ColorPalette.errorContainer,
            onErrorContainer = ColorPalette.onErrorContainer,
            outline = ColorPalette.outline,
            outlineVariant = ColorPalette.outlineVariant,
            scrim = ColorPalette.scrim,
        )
        override val mainColors: MainColors = MainColors(
            background = MainColorPalette.background,
            button = MainColorPalette.button,
            text = MainColorPalette.text,
            loading = MainColorPalette.loading,
            grey1 = MainColorPalette.grey1,
            grey2 = MainColorPalette.grey1,
            grey3 = MainColorPalette.grey1,
            grey4 = MainColorPalette.grey1,
            alert = MainColorPalette.alert,
            error = MainColorPalette.error,
        )
        override val boardColors: BoardColors = BoardColors(
            lightSquareColor = BoardColorPalette.light,
            legalMoveLight = BoardColorPalette.legalMoveLight,
            highlightedOnLight = BoardColorPalette.highlightedOnLight,
            darkSquareColor = BoardColorPalette.dark,
            legalMoveDark = BoardColorPalette.legalMoveDark,
            highlightedOnDark = BoardColorPalette.highlightedOnDark,
            alert = BoardColorPalette.alert,
            error = BoardColorPalette.error,
        )
    }
}

@Composable
fun ChessTheme(
    colorScheme: ComposeAppColorScheme = ComposeAppColorScheme.Standard,
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = colorScheme.materialColorScheme,
        shapes = Shapes,
        typography = Typography,
        content = content,
    )
}