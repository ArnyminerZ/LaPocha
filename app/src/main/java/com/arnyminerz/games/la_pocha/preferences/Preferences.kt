package com.arnyminerz.games.la_pocha.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.arnyminerz.games.la_pocha.BuildConfig

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = BuildConfig.APPLICATION_ID)

val SHOWN_INTRO = booleanPreferencesKey(PREFERENCES_KEY_SHOWN_INTRO)
val GAME_INFO_PLAYERS = stringSetPreferencesKey(PREFERENCES_KEY_GAME_PLAYERS)
