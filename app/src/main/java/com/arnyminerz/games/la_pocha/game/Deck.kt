package com.arnyminerz.games.la_pocha.game

data class Deck private constructor(
    val name: String,
    val cards: Int,
    val suits: Int,
    val specialCards: Int,
    val modifiers: Modifier? = null,
) {
    companion object {
        val SPANISH = Deck("SPANISH", 48, 4, 0, object : Modifier {
            override fun modify(gameInfo: GameInfo, deck: Deck): Deck =
                if (gameInfo.numberOfPlayers == 6)
                    deck.copy(cards = deck.cards + 8)
                else
                    deck
        })
        val SKULL_KING = Deck("SKULL_KING", 66, 4, 14)

        fun valueOf(name: String) = when (name) {
            "SPANISH" -> SPANISH
            "SKULL_KING" -> SKULL_KING
            else -> throw IllegalStateException("Could not find Deck with name $name")
        }
    }

    interface Modifier {
        fun modify(gameInfo: GameInfo, deck: Deck): Deck
    }
}
