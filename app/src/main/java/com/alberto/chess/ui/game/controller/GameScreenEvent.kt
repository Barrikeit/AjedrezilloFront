package com.alberto.chess.ui.game.controller

import com.alberto.chess.domain.model.board.Position
import com.alberto.chess.domain.model.moves.BoardMove
import com.alberto.chess.domain.model.pieces.Piece
import com.alberto.chess.ui.game.states.GameMetaData
import com.alberto.chess.ui.game.states.GameSnapshotState

sealed interface GameScreenEvent {
    object StepForward : GameScreenEvent
    object StepBackward : GameScreenEvent
    class GoToMove(val moveIndex: Int) : GameScreenEvent
    class ResetTo(val gameSnapshotState: GameSnapshotState, val gameMetaData: GameMetaData) : GameScreenEvent
    class ToggleSelectPosition(val position: Position) : GameScreenEvent
    class ApplyMove(val boardMove: BoardMove) : GameScreenEvent
    object OpenGameDialog: GameScreenEvent
    object CloseGameDialog: GameScreenEvent
    class RequestPromotion(val at: Position) : GameScreenEvent
    class PromoteTo(val piece: Piece) : GameScreenEvent
}