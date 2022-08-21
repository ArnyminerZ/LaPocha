package com.arnyminerz.games.la_pocha.game

import com.arnyminerz.games.la_pocha.utils.JsonSerializable
import com.arnyminerz.games.la_pocha.utils.JsonSerializer
import org.json.JSONObject

data class Player(
    val name: String,
) : JsonSerializable {
    companion object : JsonSerializer<Player> {
        override fun fromJson(json: JSONObject): Player = Player(
            json.getString("name")
        )
    }

    val tag: String = name
        .takeIf { it.length >= 3 }
        ?.substring(0, 3)
        ?.uppercase()
        ?: "AAA"

    override fun toJson(): JSONObject = JSONObject().apply {
        put("name", name)
    }
}