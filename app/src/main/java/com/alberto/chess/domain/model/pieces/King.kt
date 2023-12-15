package com.alberto.chess.domain.model.pieces

import com.alberto.chess.domain.model.board.File
import com.alberto.chess.domain.model.board.Rank
import com.alberto.chess.domain.model.moves.BoardMove
import com.alberto.chess.domain.model.moves.KingSideCastle
import com.alberto.chess.domain.model.moves.Move
import com.alberto.chess.domain.model.moves.QueenSideCastle
import com.alberto.chess.domain.model.moves.singleMove
import com.alberto.chess.ui.game.states.GameSnapshotState

class King(
    override val side: Side,
) : Piece {
    override val value: Int = Int.MAX_VALUE
    override val asset: Int =
        when (side) {
            Side.WHITE -> com.alberto.chess.R.drawable.ic_king_white
            Side.BLACK -> com.alberto.chess.R.drawable.ic_king_black
        }
    override val textSymbol: String =
        when (side) {
            Side.WHITE -> Symbols.WHITE_KING.fenSymbol
            Side.BLACK -> Symbols.BLACK_KING.fenSymbol
        }
    override val symbol: String =
        when (side) {
            Side.WHITE -> Symbols.WHITE_KING.fanSymbol
            Side.BLACK -> Symbols.BLACK_KING.fanSymbol
        }

    companion object {
        val targetLocations = Bishop.offsets + Rook.offsets
    }

    override fun pseudoLegalMoves(
        gameSnapshotState: GameSnapshotState,
        check: Boolean
    ): List<BoardMove> {
        val moves = targetLocations.mapNotNull {
            singleMove(
                gameSnapshotState,
                it.fileOffset,
                it.rankOffset
            )
        }.toMutableList()

        if (!check) {
            castleKingSide(gameSnapshotState)?.let { moves += it }
            castleQueenSide(gameSnapshotState)?.let { moves += it }
        }

        return moves
    }

    private fun castleKingSide(
        gameSnapshotState: GameSnapshotState
    ): BoardMove? {
        if (gameSnapshotState.hasCheck()) return null
        if (!gameSnapshotState.castlingState[side].canCastleKingSide()) return null

        val rank = if (side == Side.WHITE) Rank.ONE else Rank.EIGHT
        val eSquare = gameSnapshotState.board[File.E, rank]
        val fSquare = gameSnapshotState.board[File.F, rank]
        val gSquare = gameSnapshotState.board[File.G, rank]
        val hSquare = gameSnapshotState.board[File.H, rank]
        if (fSquare.isNotEmpty || gSquare.isNotEmpty) return null
        if (gameSnapshotState.hasCheckFor(fSquare.position) || gameSnapshotState.hasCheckFor(gSquare.position)) return null
        if (hSquare.piece !is Rook) return null

        return BoardMove(
            move = KingSideCastle(this, eSquare.position, gSquare.position),
            consequence = Move(hSquare.piece, hSquare.position, fSquare.position)
        )
    }

    private fun castleQueenSide(
        gameSnapshotState: GameSnapshotState
    ): BoardMove? {
        if (gameSnapshotState.hasCheck()) return null
        if (!gameSnapshotState.castlingState[side].canCastleQueenSide()) return null

        val rank = if (side == Side.WHITE) Rank.ONE else Rank.EIGHT
        val eSquare = gameSnapshotState.board[File.E, rank]
        val dSquare = gameSnapshotState.board[File.D, rank]
        val cSquare = gameSnapshotState.board[File.C, rank]
        val bSquare = gameSnapshotState.board[File.B, rank]
        val aSquare = gameSnapshotState.board[File.A, rank]
        if (dSquare.isNotEmpty || cSquare.isNotEmpty || bSquare.isNotEmpty) return null
        if (gameSnapshotState.hasCheckFor(dSquare.position) || gameSnapshotState.hasCheckFor(cSquare.position)) return null
        if (aSquare.piece !is Rook) return null

        return BoardMove(
            move = QueenSideCastle(this, eSquare.position, cSquare.position),
            consequence = Move(aSquare.piece, aSquare.position, dSquare.position)
        )
    }
}
