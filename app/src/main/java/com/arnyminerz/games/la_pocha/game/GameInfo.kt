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
    }

    override fun toJson() = JSONObject().apply {
        put("players", players.toJson())
        put("up_and_down", upAndDown)
    }
}
