package com.alberto.chess.domain.model.board

import com.alberto.chess.domain.model.pieces.Piece
import com.alberto.chess.domain.model.pieces.Side

data class Square (
    val position: Position,
    val piece: Piece? = null,
) {
    val file: Int = position.file
    val rank: Int = position.rank
    fun file(): File = position.file()
    fun rank(): Rank = position.rank()
    val isLight: Boolean = position.isLightSquare()
    val isDark: Boolean = position.isDarkSquare()
    val isEmpty: Boolean = piece == null
    val isNotEmpty: Boolean = !isEmpty
    fun hasPiece(side: Side): Boolean = piece?.side == side
    val hasWhitePiece: Boolean = piece?.side == Side.WHITE
    val hasBlackPiece: Boolean = piece?.side == Side.BLACK
    override fun toString(): String = file().notation + rank().notation
}