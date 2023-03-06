/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 3/6/23, 8:34 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.floating.smart.panel.Preferences.UI

import android.app.WallpaperManager
import android.content.Context
import android.content.Intent
import co.geeksempire.floating.smart.panel.Preferences.PreferencesIO
import co.geeksempire.floating.smart.panel.R
import kotlinx.coroutines.*

class ColorsIO(private val context: Context) {

    object Type {
        const val colorsChanged = "colorsChanged"

        const val dominantColor = "dominantColor"
        const val vibrantColor = "vibrantColor"
        const val mutedColor = "mutedColor"
    }

    private val preferencesIO = PreferencesIO(context)


    fun processWallpaperColors() = CoroutineScope(SupervisorJob() + Dispatchers.Main).async {

        val wallpaperColors = WallpaperManager.getInstance(context).getWallpaperColors(WallpaperManager.FLAG_SYSTEM)

        wallpaperColors?.let {

            val currentDominantWallpaper = dominantColor()

            if (currentDominantWallpaper != wallpaperColors.primaryColor.toArgb()) {

                context.sendBroadcast(Intent(Type.colorsChanged).putExtra(ColorsIO.Type.dominantColor, wallpaperColors.primaryColor.toArgb()))

            }

            storeDominantColor(wallpaperColors.primaryColor.toArgb())

            storeVibrantColor(wallpaperColors.secondaryColor?.toArgb() ?: context.getColor(R.color.primaryColorPurpleLight))

            storeMutedColor(wallpaperColors.tertiaryColor?.toArgb() ?: context.getColor(R.color.premiumDark))

        }

    }

    fun storeDominantColor(inputValue: Int) = CoroutineScope(SupervisorJob() + Dispatchers.IO).launch {

        preferencesIO.savePreference((ColorsIO.Type.dominantColor), inputValue)

    }

    fun storeVibrantColor(inputValue: Int) = CoroutineScope(SupervisorJob() + Dispatchers.IO).launch {

        preferencesIO.savePreference((ColorsIO.Type.vibrantColor), inputValue)

    }

    fun storeMutedColor(inputValue: Int) = CoroutineScope(SupervisorJob() + Dispatchers.IO).launch {

        preferencesIO.savePreference((ColorsIO.Type.mutedColor), inputValue)

    }

    fun dominantColor() : Int {

        return preferencesIO.readPreference((ColorsIO.Type.dominantColor), context.getColor(R.color.primaryColorPurple))
    }

    fun vibrantColor() : Int {

        return preferencesIO.readPreference((ColorsIO.Type.vibrantColor), context.getColor(R.color.primaryColorPurple))
    }

    fun mutedColor() : Int {

        return preferencesIO.readPreference((ColorsIO.Type.mutedColor), context.getColor(R.color.primaryColorPurple))

    }

}