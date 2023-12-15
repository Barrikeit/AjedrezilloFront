package com.alberto.chess.domain.model.pieces

enum class SymbolNotation {
    FAN, FEN,
}

enum class Symbols(
    val fanSymbol: String,
    val fenSymbol: String,
) {
    WHITE_PAWN("♙", "P"),
    WHITE_KNIGHT("♘", "N"),
    WHITE_BISHOP("♗", "B"),
    WHITE_ROOK("♖", "R"),
    WHITE_QUEEN("♕", "Q"),
    WHITE_KING("♔", "K"),
    BLACK_PAWN("♟", "p"),
    BLACK_KNIGHT("♞", "n"),
    BLACK_BISHOP("♝", "b"),
    BLACK_ROOK("♜", "r"),
    BLACK_QUEEN("♛", "q"),
    BLACK_KING("♚", "k"),
}
