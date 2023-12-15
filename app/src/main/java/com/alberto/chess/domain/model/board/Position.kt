package com.alberto.chess.domain.model.board

enum class Position {
    A1, B1, C1, D1, E1, F1, G1, H1,
    A2, B2, C2, D2, E2, F2, G2, H2,
    A3, B3, C3, D3, E3, F3, G3, H3,
    A4, B4, C4, D4, E4, F4, G4, H4,
    A5, B5, C5, D5, E5, F5, G5, H5,
    A6, B6, C6, D6, E6, F6, G6, H6,
    A7, B7, C7, D7, E7, F7, G7, H7,
    A8, B8, C8, D8, E8, F8, G8, H8;

    val file: Int = ordinal % 8 + 1
    val rank: Int = ordinal / 8 + 1
    fun file(): File = File.from(file)
    fun rank(): Rank = Rank.from(rank)
    fun isLightSquare(): Boolean = (file + rank) % 2 == 1
    fun isDarkSquare(): Boolean = !isLightSquare()
    fun toCoordinate(isFlipped: Boolean): Coordinate =
        if (isFlipped) Coordinate(
            x = Coordinate.max.x - file + 1,
            y = rank.toFloat(),
        )
        else Coordinate(
            x = file.toFloat(),
            y = Coordinate.max.y - rank + 1,
        )

    companion object {
        fun from(file: File, rank: Rank): Position =
            values()[file.ordinal + rank.ordinal * 8]

        fun from(file: Int, rank: Int): Position {
            require(file in 1..8)
            require(rank in 1..8)
            return values()[(file - 1) + (rank - 1) * 8]
        }
    }
}