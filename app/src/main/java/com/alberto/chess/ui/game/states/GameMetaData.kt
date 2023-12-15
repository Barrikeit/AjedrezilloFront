package com.alberto.chess.ui.game.states

import com.alberto.chess.domain.model.pieces.Side
import com.alberto.chess.ui.game.controller.GameStatus
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * GameInfo es una serie de datos, que a partir de esto y la lista de movimientos se obtiene un archivo PGN
 * El evento
 * El sitio
 * La fecha
 * La Ronda
 * El jugador blanco
 * El jugador negro
 * Resultado, 1-0, 0-1, 1/2-1/2 (tablas)
 * Como ha terminado, enProgreso, check mate, stale mate, draw by repetition
 * Lista de movimientos
 */
data class GameMetaData(
    val tags: Map<String, String>,
) {
    operator fun get(key: String): String? = tags[key]
    val event: String? = get(KEY_EVENT)
    val site: String? = get(KEY_SITE)
    val date: String? = get(KEY_DATE)
    val white: String? = get(KEY_WHITE)
    val black: String? = get(KEY_BLACK)
    val result: String? = get(KEY_RESULT)
    val termination: String? = get(KEY_TERMINATION)

    companion object {
        const val KEY_EVENT = "Event"
        const val KEY_SITE = "Site"
        const val KEY_DATE = "Date"
        const val KEY_WHITE = "White"
        const val KEY_BLACK = "Black"
        const val KEY_RESULT = "Result"
        const val KEY_TERMINATION = "Termination"

        fun createWithDefaults(): GameMetaData =
            GameMetaData(
                tags = mapOf(
                    KEY_EVENT to "Ajedrezillo game",
                    KEY_SITE to "Ajedrezillo app",
                    KEY_DATE to LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                        .toString(),
                    KEY_WHITE to "Player 1",
                    KEY_BLACK to "Player 2",
                )
            )

        fun withResult(gameMetaData: GameMetaData): GameMetaData =
            GameMetaData(
                tags = mapOf(
                    KEY_EVENT to "Ajedrezillo game",
                    KEY_SITE to "Ajedrezillo app",
                    KEY_DATE to LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                        .toString(),
                    KEY_WHITE to "Player 1",
                    KEY_BLACK to "Player 2",
                    KEY_RESULT to "1-0",
                    KEY_TERMINATION to "Normal",

                )
            )
    }
}

fun GameMetaData.withResolution(gameStatus: GameStatus, lastMoveBy: Side): GameMetaData =
    when (gameStatus) {
        GameStatus.IN_PROGRESS -> this
        GameStatus.CHECKMATE -> {
            val result = if (lastMoveBy == Side.WHITE) "1-0" else "0-1"
            val winner = if (lastMoveBy == Side.WHITE) white else black
            copy(
                tags = tags
                    .plus(GameMetaData.KEY_RESULT to result)
                    .plus(GameMetaData.KEY_TERMINATION to "$winner won by checkmate")
            )
        }
        GameStatus.STALEMATE -> {
            copy(
                tags = tags
                    .plus(GameMetaData.KEY_RESULT to "½ - ½")
                    .plus(GameMetaData.KEY_TERMINATION to "Stalemate")
            )
        }
        GameStatus.DRAW -> {
            copy(
                tags = tags
                    .plus(GameMetaData.KEY_RESULT to "½ - ½")
                    .plus(GameMetaData.KEY_TERMINATION to "Draw")
            )
        }
        GameStatus.RESIGNATION -> {
            val result = if (lastMoveBy == Side.WHITE) "0-1" else "1-0"
            val winner = if (lastMoveBy == Side.WHITE) black else white
            copy(
                tags = tags
                    .plus(GameMetaData.KEY_RESULT to result)
                    .plus(GameMetaData.KEY_TERMINATION to "$winner won by resignation")
            )
        }
        GameStatus.DRAW_BY_REPETITION -> {
            copy(
                tags = tags
                    .plus(GameMetaData.KEY_RESULT to "½ - ½")
                    .plus(GameMetaData.KEY_TERMINATION to "Draw by repetition")
            )
        }
        GameStatus.INSUFFICIENT_MATERIAL -> {
            copy(
                tags = tags
                    .plus(GameMetaData.KEY_RESULT to "½ - ½")
                    .plus(GameMetaData.KEY_TERMINATION to "Draw by insufficient material")
            )
        }
    }