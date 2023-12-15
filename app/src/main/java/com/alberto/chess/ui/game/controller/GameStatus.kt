package com.alberto.chess.ui.game.controller

/**
 * Representa el estado de la partida.
 */
enum class GameStatus {
    IN_PROGRESS, CHECKMATE, STALEMATE, DRAW, RESIGNATION, DRAW_BY_REPETITION, INSUFFICIENT_MATERIAL,
}