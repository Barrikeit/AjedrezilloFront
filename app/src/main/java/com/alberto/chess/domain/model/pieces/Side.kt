package com.alberto.chess.domain.model.pieces

enum class Side {
    WHITE, BLACK;

    fun opposite() = when (this) {
        WHITE -> BLACK
        BLACK -> WHITE
    }
}