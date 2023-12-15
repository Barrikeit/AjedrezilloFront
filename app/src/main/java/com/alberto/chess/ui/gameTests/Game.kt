package com.alberto.chess.ui.gameTests

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.alberto.chess.ui.theme.BoardColorPalette

@Composable
fun Game(
    viewModel: GameScreenViewModel,
) {
    val gameScreenState by remember { mutableStateOf(viewModel.state) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BoardColorPalette.backGround)
    ) {
        /**
         * Status necesita el estado de la partida de gameScreenState
         */
        /**
         * CapturedPieces necesita el estado de la partida de gameScreenState
         * y si el tablero esta dado la vuelta o no
         */
        /**
         * Board necesita el estado de la partida de gameScreenState y el controlador de la partida del viewmodel
         * y si el tablero esta dado la vuelta o no
         */
        /**
         * MoveList necesita la lista de movimientos del gameState del gameScreenState
         * y el indice del movimiento seleccionado del gameState del gameScreenState
         * y se la funcion goToMove del controlador de la partida del viewmodel
         */
        /**
         * GameControls necesita el estado de la partida de gameScreenState
         * y la funcion stepBackward del controlador de la partida del viewmodel
         * y la funcion stepForward del controlador de la partida del viewmodel
         * y la funcion flipBoard del controlador de la partida del viewmodel
         *
         * Extra: añadir boton que ejecute el dialogo para rendirse, pedir tablas o pedir nueva partida
         */
//        Status()
//        CapturedPieces()
//        Board()
//        CapturedPieces()
//        MoveList() {}
//        GameControls()
    }
    /**
     * Dialogs necesita el estado de la partida de gameScreenState
     * y el controlador de la partida del viewmodel
     * y el estado de si se muestra el dialogo de la partida de gameScreenState (es un booleano igual que el de las promociones, eso deberia de hacerse igual que el de las promociones)
     *
     * Tip: ahora mismo el dialogo de las promociones sale segun el gameUiState del gameScreenState
     */
//    Dialogs()
}