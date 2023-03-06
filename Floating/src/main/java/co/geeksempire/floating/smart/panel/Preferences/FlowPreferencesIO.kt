/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 3/6/23, 7:44 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.floating.smart.panel.Preferences

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import co.geeksempire.floating.smart.panel.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FlowPreferencesIO (private val context: Context) {

    suspend fun savePreferences(preferenceKey: Preferences.Key<String>, inputValue: String) {

        context.dataStore.edit { settings ->

            settings[preferenceKey] = inputValue

        }
    }

    suspend fun savePreferences(preferenceKey: Preferences.Key<Boolean>, inputValue: Boolean) {

        context.dataStore.edit { settings ->

            settings[preferenceKey] = inputValue

        }
    }

    suspend fun savePreferences(preferenceKey: Preferences.Key<Int>, inputValue: Int) {

        context.dataStore.edit { settings ->

            settings[preferenceKey] = inputValue

        }
    }

    fun readPreferencesString(preferenceKey: Preferences.Key<String>, defaultValue: String) : Flow<String> {

        return context.dataStore.data.map { preferences -> preferences[preferenceKey]?:defaultValue }
    }

    fun readPreferencesBoolean(preferenceKey: Preferences.Key<Boolean>, defaultValue: Boolean) : Flow<Boolean> {

        return context.dataStore.data.map { preferences -> preferences[preferenceKey]?:defaultValue }
    }

    fun readPreferencesInt(preferenceKey: Preferences.Key<Int>, defaultValue: Int) : Flow<Int> {

        return context.dataStore.data.map { preferences -> preferences[preferenceKey]?:defaultValue }
    }

}