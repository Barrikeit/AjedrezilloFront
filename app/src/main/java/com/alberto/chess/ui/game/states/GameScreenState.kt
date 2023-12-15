package com.alberto.chess.ui.game.states

data class GameScreenState(
    val gameState: GameState = GameState(GameMetaData.createWithDefaults()),
    val gameUiState: GameUiState = GameUiState(gameState.currentSnapshotState),
    val promotionState: PromotionState = PromotionState.None
)