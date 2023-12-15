package com.alberto.chess.domain.model.board

import com.alberto.chess.domain.model.moves.BoardEffect
import com.alberto.chess.domain.model.pieces.Bishop
import com.alberto.chess.domain.model.pieces.King
import com.alberto.chess.domain.model.pieces.Knight
import com.alberto.chess.domain.model.pieces.Pawn
import com.alberto.chess.domain.model.pieces.Piece
import com.alberto.chess.domain.model.pieces.Queen
import com.alberto.chess.domain.model.pieces.Rook
import com.alberto.chess.domain.model.pieces.Side

/**
 * Map de las posiciones del tablero ocupadas por piezas
 * Y un map de todas las posiciones del tablero con su casilla correspondiente que puede tener o no una pieza
 */
data class Board(
    val pieces: Map<Position, Piece> = initialPieces,
) {
    val squares = Position.values().associateWith { position -> Square(position, pieces[position]) }
    operator fun get(position: Position): Square = squares[position]!!
    operator fun get(file: Int, rank: Int): Square? {
        return try {
            squares[Position.from(file, rank)]
        } catch (e: IllegalArgumentException) {
            null
        }
    }
    operator fun get(file: File, rank: Rank): Square = get(Position.from(file, rank))
    fun find(piece: Piece): Square? = squares.values.firstOrNull { it.piece == piece }
    inline fun <reified T : Piece> find(side: Side): List<Square> =
        squares.values.filter { it.piece != null && it.piece::class == T::class && it.piece.side == side }

    fun pieces(side: Side): Map<Position, Piece> =
        pieces.filter { (_, piece) -> piece.side == side }

    fun apply(effect: BoardEffect?): Board = effect?.applyOn(this) ?: this
}

private val initialPieces = mapOf(
    Position.A8 to Rook(Side.BLACK),
    Position.B8 to Knight(Side.BLACK),
    Position.C8 to Bishop(Side.BLACK),
    Position.D8 to Queen(Side.BLACK),
    Position.E8 to King(Side.BLACK),
    Position.F8 to Bishop(Side.BLACK),
    Position.G8 to Knight(Side.BLACK),
    Position.H8 to Rook(Side.BLACK),

    Position.A7 to Pawn(Side.BLACK),
    Position.B7 to Pawn(Side.BLACK),
    Position.C7 to Pawn(Side.BLACK),
    Position.D7 to Pawn(Side.BLACK),
    Position.E7 to Pawn(Side.BLACK),
    Position.F7 to Pawn(Side.BLACK),
    Position.G7 to Pawn(Side.BLACK),
    Position.H7 to Pawn(Side.BLACK),

    Position.A2 to Pawn(Side.WHITE),
    Position.B2 to Pawn(Side.WHITE),
    Position.C2 to Pawn(Side.WHITE),
    Position.D2 to Pawn(Side.WHITE),
    Position.E2 to Pawn(Side.WHITE),
    Position.F2 to Pawn(Side.WHITE),
    Position.G2 to Pawn(Side.WHITE),
    Position.H2 to Pawn(Side.WHITE),

    Position.A1 to Rook(Side.WHITE),
    Position.B1 to Knight(Side.WHITE),
    Position.C1 to Bishop(Side.WHITE),
    Position.D1 to Queen(Side.WHITE),
    Position.E1 to King(Side.WHITE),
    Position.F1 to Bishop(Side.WHITE),
    Position.G1 to Knight(Side.WHITE),
    Position.H1 to Rook(Side.WHITE),
)