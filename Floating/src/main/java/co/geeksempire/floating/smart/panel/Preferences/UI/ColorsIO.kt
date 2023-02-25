/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 2/25/23, 10:16 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.floating.smart.panel.Preferences.UI

import android.app.WallpaperManager
import android.content.Context
import androidx.datastore.preferences.core.intPreferencesKey
import co.geeksempire.floating.smart.panel.FloatingApplication
import co.geeksempire.floating.smart.panel.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow

class ColorsIO(private val context: Context) {

    object Type {
        const val dominantColor = "dominantColor"
        const val vibrantColor = "vibrantColor"
        const val mutedColor = "mutedColor"
    }

    private val preferencesIO = (context.applicationContext as FloatingApplication).preferencesIO

    fun processWallpaperColors() {

        val wallpaperColors = WallpaperManager.getInstance(context).getWallpaperColors(WallpaperManager.FLAG_SYSTEM)

        wallpaperColors?.let {

            storeDominantColor(wallpaperColors.primaryColor.toArgb())

            storeVibrantColor(wallpaperColors.secondaryColor?.toArgb() ?: context.getColor(R.color.primaryColorPurpleLight))

            storeMutedColor(wallpaperColors.tertiaryColor?.toArgb() ?: context.getColor(R.color.premiumDark))

        }

    }

    fun storeDominantColor(inputValue: Int) = CoroutineScope(Dispatchers.IO + SupervisorJob()).async {

        preferencesIO.savePreferences(intPreferencesKey(ColorsIO.Type.dominantColor), inputValue)

    }

    fun storeVibrantColor(inputValue: Int) = CoroutineScope(Dispatchers.IO + SupervisorJob()).async {

        preferencesIO.savePreferences(intPreferencesKey(ColorsIO.Type.vibrantColor), inputValue)

    }

    fun storeMutedColor(inputValue: Int) = CoroutineScope(Dispatchers.IO + SupervisorJob()).async {

        preferencesIO.savePreferences(intPreferencesKey(ColorsIO.Type.mutedColor), inputValue)

    }

    fun dominantColor(): Flow<Int> {

        return preferencesIO.readPreferencesInt(intPreferencesKey(ColorsIO.Type.dominantColor), context.getColor(R.color.primaryColorPurple))
    }

    fun vibrantColor(): Flow<Int> {

        return preferencesIO.readPreferencesInt(intPreferencesKey(ColorsIO.Type.vibrantColor), context.getColor(R.color.primaryColorPurpleLight))
    }

    fun mutedColor(): Flow<Int> {

        return preferencesIO.readPreferencesInt(intPreferencesKey(ColorsIO.Type.vibrantColor), context.getColor(R.color.premiumDark))
    }

}