package com.arnyminerz.games.la_pocha.game

import com.arnyminerz.games.la_pocha.utils.JsonSerializable
import com.arnyminerz.games.la_pocha.utils.JsonSerializer
import org.json.JSONObject

data class GameRules(
    val upAndDown: Boolean
) : JsonSerializable {
    companion object : JsonSerializer<GameRules> {
        override fun fromJson(json: JSONObject): GameRules =
            GameRules(json.getBoolean("up_and_down"))
    }

    override fun toJson(): JSONObject = JSONObject().apply {
        put("up_and_down", upAndDown)
    }
}
