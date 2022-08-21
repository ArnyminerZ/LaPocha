package com.arnyminerz.games.la_pocha.game

fun cardsNumber(players: Int): Int = when (players) {
    3 -> 36
    4 -> 40
    5 -> 40
    6 -> 48
    8 -> 40
    else -> -1
}
