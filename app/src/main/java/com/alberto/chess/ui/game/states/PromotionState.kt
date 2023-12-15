package com.alberto.chess.ui.game.states

import com.alberto.chess.domain.model.board.Position
import com.alberto.chess.domain.model.pieces.Piece

sealed class PromotionState {
    object None : PromotionState()
    data class Await(val position: Position) : PromotionState()
    data class ContinueWith(val piece: Piece) : PromotionState()
}
