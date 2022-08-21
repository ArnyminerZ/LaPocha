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

        val PRESETS = listOf(
            Preset("La Pocha", GameRules(true)),
            Preset("Skull King", GameRules(false)),
        )
    }

    data class Preset(
        val name: String,
        val rules: GameRules,
    )

    override fun toJson(): JSONObject = JSONObject().apply {
        put("up_and_down", upAndDown)
    }
}
