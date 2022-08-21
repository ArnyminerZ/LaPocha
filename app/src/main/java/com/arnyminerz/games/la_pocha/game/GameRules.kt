package com.arnyminerz.games.la_pocha.game

import com.arnyminerz.games.la_pocha.utils.JsonSerializable
import com.arnyminerz.games.la_pocha.utils.JsonSerializer
import org.json.JSONObject

data class GameRules(
    val upAndDown: Boolean,
    val deck: Deck,
) : JsonSerializable {
    companion object : JsonSerializer<GameRules> {
        override fun fromJson(json: JSONObject): GameRules =
            GameRules(
                json.getBoolean("up_and_down"),
                Deck.valueOf(json.getString("deck")),
            )

        val PRESETS = listOf(
            Preset("La Pocha", GameRules(true, Deck.SPANISH)),
            Preset("Skull King", GameRules(false, Deck.SKULL_KING)),
        )
    }

    data class Preset(
        val name: String,
        val rules: GameRules,
    )

    override fun toJson(): JSONObject = JSONObject().apply {
        put("up_and_down", upAndDown)
        put("deck", deck.name)
    }
}
