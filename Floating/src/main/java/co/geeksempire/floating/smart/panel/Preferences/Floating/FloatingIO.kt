/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 3/6/23, 7:55 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.floating.smart.panel.Preferences.Floating

import android.content.Context
import co.geeksempire.floating.smart.panel.Preferences.PreferencesIO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async

class FloatingIO (private val context: Context) {

    object FloatingSide {
        const val FloatingSideX = "FloatingSideX"
        const val FloatingSideY = "FloatingSideY"

        const val LeftSide = 0
        const val RightSide = 1
    }

    private val preferencesIO = PreferencesIO(context)

    fun storePositionX(inputValue: Int) = CoroutineScope(SupervisorJob() + Dispatchers.IO).async {

        preferencesIO.savePreference((FloatingIO.FloatingSide.FloatingSideX), inputValue)

    }

    fun storePositionY(inputValue: Int) = CoroutineScope(SupervisorJob() + Dispatchers.IO).async {

        preferencesIO.savePreference((FloatingIO.FloatingSide.FloatingSideY), inputValue)

    }

    fun positionX() : Int {

        return preferencesIO.readPreference((FloatingIO.FloatingSide.FloatingSideX), 37)
    }

    fun positionY() : Int {

        return preferencesIO.readPreference((FloatingIO.FloatingSide.FloatingSideY), 37)
    }

}