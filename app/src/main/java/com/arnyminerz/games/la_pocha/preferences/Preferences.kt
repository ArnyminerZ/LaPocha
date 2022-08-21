package com.arnyminerz.games.la_pocha.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.arnyminerz.games.la_pocha.BuildConfig

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = BuildConfig.APPLICATION_ID)

val SHOWN_INTRO = booleanPreferencesKey(PREFERENCES_KEY_SHOWN_INTRO)

val GAME_INFO = stringPreferencesKey(PREFERENCES_KEY_GAME_INFO)

val GAME_PROGRESS = stringPreferencesKey(PREFERENCES_KEY_GAME_PROGRESS)
