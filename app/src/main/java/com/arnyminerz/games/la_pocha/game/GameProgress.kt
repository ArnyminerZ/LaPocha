package com.arnyminerz.games.la_pocha.game

import com.arnyminerz.games.la_pocha.utils.JsonSerializable
import com.arnyminerz.games.la_pocha.utils.JsonSerializer
import org.json.JSONObject

data class GameProgress(
    val round: Int,
) : JsonSerializable {
    companion object : JsonSerializer<GameProgress> {
        override fun fromJson(json: JSONObject): GameProgress = GameProgress(
            json.getInt("round")
        )

        val Default: GameProgress
            get() = GameProgress(0)
    }

    fun changeRound(variance: Int) = copy(round = round + variance)

    override fun toJson(): JSONObject = JSONObject().apply {
        put("round", round)
    }

    fun numberOfCards(gameInfo: GameInfo) =
        if (round < gameInfo.numberOfCards / gameInfo.numberOfPlayers)
            round + 1
        else gameInfo.numberOfRounds - (round % gameInfo.numberOfRounds)
}
