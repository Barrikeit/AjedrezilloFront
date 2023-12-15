package com.alberto.chess.ui.theme

import androidx.compose.ui.graphics.Color

object MainColorPalette {
    val background = Color(0xFF2F2E2A)
    val button = Color(0xFF789954)
    val text = Color(0xffffffff)
    val loading = Color(0xFFEDF2F4)
    val grey1 = Color(0xFF3b3936)
    val grey2 = Color(0xFF302E2B)
    val grey3 = Color(0xFF262522)
    val grey4 = Color(0xFF1d1c1a)
    val alert = Color(0xFFF0965D)
    val error = Color(0xFFBB4C2A)
}

object BoardColorPalette {
    val backGround = Color(0xFF2F2E2A) //color del fondo
    val text = Color(0xFF2F2E2A) //color del fondo
    val light = Color(0xFFE9EDCB) //color de las casillas claras
    val legalMoveLight = Color(0xFFD1D5B7) //color de las casillas claras cuando se puede mover
    val highlightedOnLight = Color(0XFFF4F680) //color de las casillas claras cuando estan seleccionadas
    val dark = Color(0xFF789954) //color de las casillas oscuras
    val legalMoveDark = Color(0xFF6B894B) //color de las casillas oscuras cuando se puede mover
    val highlightedOnDark = Color(0xFFBBCC44) //color de las casillas oscuras cuando estan seleccionadas
    val alert = Color(0xFFF0965D)
    val error = Color(0xFFBB4C2A) //color de la cassilla del rey cuando esta en jaque mate
}

object ColorPalette {
    val primary = Color(0xFF1d1c1a) // El color principal se mantiene en negro.
    val primaryContainer = Color(0xFF000000) // El fondo de los contenedores primarios también es negro.
    val onPrimary = Color(0xFF000000) // El texto en el color principal es blanco.
    val onPrimaryContainer = Color(0xFF000000) // El texto en los contenedores primarios es blanco.
    val inversePrimary = Color(0xFF000000) // Un color que contrasta con el color principal.

    val secondary = Color(0xFF000000) // El color secundario se mantiene en negro.
    val secondaryContainer = Color(0xFF000000) // El fondo de los contenedores secundarios también es negro.
    val onSecondary = Color(0xFF000000) // El texto en el color secundario es blanco.
    val onSecondaryContainer = Color(0xFF000000) // El texto en los contenedores secundarios es blanco.

    val tertiary = Color(0xFF000000) // El color terciario se mantiene en negro.
    val tertiaryContainer = Color(0xFF000000) // El fondo de los contenedores terciarios también es negro.
    val onTertiary = Color(0xFF000000) // El texto en el color terciario es blanco.
    val onTertiaryContainer = Color(0xFF000000) // El texto en los contenedores terciarios es blanco.

    val background = Color(0xFF000000) // El fondo de la aplicación se toma de la paleta de colores del tablero.
    val onBackground = Color(0xFFE9EDCB) // El texto en el fondo es de un color claro (puedes ajustar este color).

    val surface = Color(0xFF000000) // El color de las superficies se mantiene en negro.
    val onSurface = Color(0xFF000000) // El texto en las superficies es blanco.
    val surfaceVariant = Color(0xFF000000) // El color de las superficies variantes se mantiene en negro.
    val onSurfaceVariant = Color(0xFF000000) // El texto en las superficies variantes es blanco.

    val surfaceTint = Color(0xFF000000) // Un tono o sombra para el color de las superficies.
    val inverseSurface = Color(0xFF000000) // Un color que contrasta con el color de las superficies.
    val inverseOnSurface = Color(0xFF000000) // El texto en el color de superficie inversa es blanco.

    val error = Color(0xFFBB4C2A) // El color utilizado para indicar errores.
    val onError = Color(0xFF000000) // El texto en elementos relacionados con errores es negro.
    val errorContainer = Color(0xFF000000) // El fondo de los contenedores de errores se mantiene en negro.
    val onErrorContainer = Color(0xFF000000) // El texto en los contenedores de errores es negro.
    val outline = Color(0xFF000000) // Un color utilizado para contornos o bordes.
    val outlineVariant = Color(0xFF000000) // Una variante del color de contorno para elementos específicos de la interfaz de usuario.
    val scrim = Color(0xFF000000) // Una superposición semitransparente utilizada para modales o diálogos.
}

