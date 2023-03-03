/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 3/3/23, 8:06 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.floating.smart.panel.Preferences.Floating

import android.content.Context
import androidx.datastore.preferences.core.intPreferencesKey
import co.geeksempire.floating.smart.panel.Preferences.PreferencesIO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow

class FloatingIO (private val context: Context) {

    object FloatingSide {
        const val FloatingSideX = "FloatingSideX"
        const val FloatingSideY = "FloatingSideY"

        const val LeftSide = 0
        const val RightSide = 1
    }

    private val preferencesIO = PreferencesIO(context)

    fun storePositionX(inputValue: Int) = CoroutineScope(SupervisorJob() + Dispatchers.IO).async {

        preferencesIO.savePreferences(intPreferencesKey(FloatingIO.FloatingSide.FloatingSideX), inputValue)

    }

    fun storePositionY(inputValue: Int) = CoroutineScope(SupervisorJob() + Dispatchers.IO).async {

        preferencesIO.savePreferences(intPreferencesKey(FloatingIO.FloatingSide.FloatingSideY), inputValue)

    }

    fun positionX() : Flow<Int> {

        return preferencesIO.readPreferencesInt(intPreferencesKey(FloatingIO.FloatingSide.FloatingSideX), 37)
    }

    fun positionY() : Flow<Int> {

        return preferencesIO.readPreferencesInt(intPreferencesKey(FloatingIO.FloatingSide.FloatingSideY), 37)
    }

}