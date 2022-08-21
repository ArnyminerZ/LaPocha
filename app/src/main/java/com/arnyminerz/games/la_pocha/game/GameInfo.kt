package com.arnyminerz.games.la_pocha.game

import com.arnyminerz.games.la_pocha.utils.JSON_OBJECT_GETTER
import com.arnyminerz.games.la_pocha.utils.JsonSerializable
import com.arnyminerz.games.la_pocha.utils.JsonSerializer
import com.arnyminerz.games.la_pocha.utils.map
import com.arnyminerz.games.la_pocha.utils.toJson
import org.json.JSONObject

data class GameInfo(
    val players: List<Player>,
    val upAndDown: Boolean,
) : JsonSerializable {
    companion object : JsonSerializer<GameInfo> {
        override fun fromJson(json: JSONObject): GameInfo =
            GameInfo(
                json.getJSONArray("players")
                    .map(JSON_OBJECT_GETTER) { Player.fromJson(it) },
                json.getBoolean("up_and_down"),
            )

        /**
         * Obtains the amount of cards are required for a set amount of players.
         * @author Arnau Mora
         * @since 20220821
         */
        fun cardsNumber(players: Int): Int = when (players) {
            3 -> 36
            4 -> 40
            5 -> 40
            6 -> 48
            8 -> 40
            else -> -1
        }
    }

    override fun toJson() = JSONObject().apply {
        put("players", players.toJson())
        put("up_and_down", upAndDown)
    }

    val numberOfPlayers: Int = players.size

    val numberOfCards: Int = cardsNumber(numberOfPlayers)

    val numberOfRounds: Int = (numberOfCards / numberOfPlayers) * (if (upAndDown) 2 else 1)
}
