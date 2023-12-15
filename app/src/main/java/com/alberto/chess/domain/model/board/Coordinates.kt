package com.alberto.chess.domain.model.board

enum class File(
    val notation: String
) {
    A("A"),
    B("B"),
    C("C"),
    D("D"),
    E("E"),
    F("F"),
    G("G"),
    H("H");
    companion object {
        fun from(notation: String): File = values().first { it.notation == notation }
        fun from(notation: Int): File = when (notation) {
            1 -> A
            2 -> B
            3 -> C
            4 -> D
            5 -> E
            6 -> F
            7 -> G
            8 -> H
            else -> throw IllegalArgumentException("Invalid file number: $notation")
        }
    }
}

enum class Rank(
    val notation: String
) {
    ONE("1"),
    TWO("2"),
    THREE("3"),
    FOUR("4"),
    FIVE("5"),
    SIX("6"),
    SEVEN("7"),
    EIGHT("8");
    companion object {
        fun from(notation: String): Rank = values().first { it.notation == notation }
        fun from(notation: Int): Rank = when (notation) {
            1 -> ONE
            2 -> TWO
            3 -> THREE
            4 -> FOUR
            5 -> FIVE
            6 -> SIX
            7 -> SEVEN
            8 -> EIGHT
            else -> throw IllegalArgumentException("Invalid rank number: $notation")
        }
    }
}