package com.arnyminerz.games.la_pocha.viewmodel

import android.app.Application
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.arnyminerz.games.la_pocha.App
import com.arnyminerz.games.la_pocha.game.GameInfo
import com.arnyminerz.games.la_pocha.game.GameProgress
import com.arnyminerz.games.la_pocha.game.Player
import com.arnyminerz.games.la_pocha.preferences.GAME_INFO
import com.arnyminerz.games.la_pocha.preferences.GAME_PROGRESS
import com.arnyminerz.games.la_pocha.preferences.SHOWN_INTRO
import com.arnyminerz.games.la_pocha.preferences.dataStore
import com.arnyminerz.games.la_pocha.utils.io
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.json.JSONObject
import timber.log.Timber

class MainViewModel(application: Application) : AndroidViewModel(application) {
    var shownIntro = getApplication<App>()
        .dataStore
        .data
        .map { it[SHOWN_INTRO] ?: false }

    var gameInfo = getApplication<App>()
        .dataStore
        .data
        .map { it[GAME_INFO] }
        .map { jsonStr -> jsonStr?.let { JSONObject(it) } }
        .map { json -> json?.let { GameInfo.fromJson(it) } }

    val gameProgress = getApplication<App>()
        .dataStore
        .data
        .map { it[GAME_PROGRESS] }
        .map { jsonStr -> jsonStr?.let { JSONObject(it) } }
        .map { json -> json?.let { GameProgress.fromJson(it) } ?: GameProgress.Default }

    fun markShownIntro() =
        viewModelScope.launch {
            Timber.d("Marking intro as shown...")
            io { getApplication<App>().dataStore.edit { it[SHOWN_INTRO] = true } }
            Timber.d("Marked intro as shown.")
        }

    fun startGame(playersList: List<Player>, upAndDown: Boolean) =
        viewModelScope.launch {
            Timber.d("Storing game preferences...")
            io {
                getApplication<App>()
                    .dataStore
                    .edit { pref ->
                        pref[GAME_INFO] = GameInfo(playersList, upAndDown).toJson().toString()
                        pref[GAME_PROGRESS] = GameProgress.Default.toJson().toString()
                    }
            }
            Timber.d("Game started!")
        }

    fun endGame() =
        viewModelScope.launch {
            Timber.d("Forcing game ending...")
            io {
                getApplication<App>()
                    .dataStore
                    .edit { it.remove(GAME_INFO) }
            }
            Timber.d("Game finished!")
        }

    fun nextRound() =
        viewModelScope.launch {
            Timber.d("Going to next round...")
            io {
                getApplication<App>()
                    .dataStore
                    .edit {
                        // TODO: Detect game end
                        it[GAME_PROGRESS] = gameProgress
                            // Get GameProgress
                            .first()
                            // Increase round by 1
                            .changeRound(1)
                            // Convert instance to JSON
                            .toJson()
                            // Convert JSON to String
                            .toString()
                    }
            }
        }
}