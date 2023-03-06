/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 3/6/23, 7:49 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.floating.smart.panel.Preferences

import android.content.Context

class PreferencesIO (private val context: Context) {

    fun savePreference(key: String?, value: Int) {

        context.getSharedPreferences("Preferences", Context.MODE_PRIVATE).apply {

            edit().apply {

                putInt(key, value)
                apply()

            }

        }

    }

    fun readPreference(key: String?, defaultValue: Int) : Int {

        return context.getSharedPreferences("Preferences", Context.MODE_PRIVATE).getInt(key, defaultValue)
    }

}