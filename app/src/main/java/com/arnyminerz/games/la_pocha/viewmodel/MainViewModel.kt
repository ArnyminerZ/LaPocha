package com.arnyminerz.games.la_pocha.viewmodel

import android.app.Application
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.arnyminerz.games.la_pocha.App
import com.arnyminerz.games.la_pocha.game.GameInfo
import com.arnyminerz.games.la_pocha.game.Player
import com.arnyminerz.games.la_pocha.preferences.GAME_INFO_PLAYERS
import com.arnyminerz.games.la_pocha.preferences.SHOWN_INTRO
import com.arnyminerz.games.la_pocha.preferences.dataStore
import com.arnyminerz.games.la_pocha.utils.io
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import timber.log.Timber

class MainViewModel(application: Application) : AndroidViewModel(application) {
    var shownIntro = getApplication<App>()
        .dataStore
        .data
        .map { it[SHOWN_INTRO] ?: false }

    var gameInfo = getApplication<App>()
        .dataStore
        .data
        .map { it[GAME_INFO_PLAYERS] }
        .map { set -> (set?.map { Player(it) })?.let { GameInfo(it) } }

    fun markShownIntro() =
        viewModelScope.launch {
            Timber.d("Marking intro as shown...")
            io { getApplication<App>().dataStore.edit { it[SHOWN_INTRO] = true } }
            Timber.d("Marked intro as shown.")
        }

    fun startGame(playersList: List<Player>) =
        viewModelScope.launch {
            Timber.d("Storing game preferences...")
            io {
                getApplication<App>()
                    .dataStore
                    .edit { pref -> pref[GAME_INFO_PLAYERS] = playersList.map { it.name }.toSet() }
            }
            Timber.d("Game started!")
        }
}