/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 2/27/23, 10:51 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.floating.smart.panel.Preferences.UI

import android.app.WallpaperManager
import android.content.Context
import android.content.Intent
import androidx.datastore.preferences.core.intPreferencesKey
import co.geeksempire.floating.smart.panel.Preferences.PreferencesIO
import co.geeksempire.floating.smart.panel.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow

class ColorsIO(private val context: Context) {

    object Type {
        const val colorsChanged = "colorsChanged"

        const val dominantColor = "dominantColor"
        const val vibrantColor = "vibrantColor"
        const val mutedColor = "mutedColor"
    }

    private val preferencesIO = PreferencesIO(context)


    fun processWallpaperColors() = CoroutineScope(Dispatchers.Main).async {

        val wallpaperColors = WallpaperManager.getInstance(context).getWallpaperColors(WallpaperManager.FLAG_SYSTEM)

        wallpaperColors?.let {

            dominantColor().collect { currentDominantWallpaper ->

                if (currentDominantWallpaper != wallpaperColors.primaryColor.toArgb()) {

                    context.sendBroadcast(Intent(ColorsIO.Type.colorsChanged).putExtra(ColorsIO.Type.dominantColor, wallpaperColors.primaryColor.toArgb()))

                }

            }



            storeDominantColor(wallpaperColors.primaryColor.toArgb())

            storeVibrantColor(wallpaperColors.secondaryColor?.toArgb() ?: context.getColor(R.color.primaryColorPurpleLight))

            storeMutedColor(wallpaperColors.tertiaryColor?.toArgb() ?: context.getColor(R.color.premiumDark))

        }

    }

    fun storeDominantColor(inputValue: Int) = CoroutineScope(SupervisorJob() + Dispatchers.IO).async {

        preferencesIO.savePreferences(intPreferencesKey(ColorsIO.Type.dominantColor), inputValue)

    }

    fun storeVibrantColor(inputValue: Int) = CoroutineScope(SupervisorJob() + Dispatchers.IO).async {

        preferencesIO.savePreferences(intPreferencesKey(ColorsIO.Type.vibrantColor), inputValue)

    }

    fun storeMutedColor(inputValue: Int) = CoroutineScope(SupervisorJob() + Dispatchers.IO).async {

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