/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 3/13/23, 7:31 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.floating.smart.panel.Floating.Extensions

import android.content.res.ColorStateList
import co.geeksempire.floating.smart.panel.Floating.FloatingPanelServices
import co.geeksempire.floating.smart.panel.Utils.Colors.setColorAlpha
import co.geeksempire.floating.smart.panel.databinding.FloatingLayoutBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun FloatingPanelServices.setupUserInterface(floatingLayoutBinding: FloatingLayoutBinding) = CoroutineScope(Dispatchers.Main).launch {

    val dominantColor = colorsIO.dominantColor()

    floatingLayoutBinding.floatingHandheldGlow.imageTintList = ColorStateList.valueOf(dominantColor)
    floatingLayoutBinding.floatingHandheld.imageTintList = ColorStateList.valueOf(dominantColor)

    val backgroundColor = setColorAlpha(dominantColor, floatingIO.transparency())

    floatingLayoutBinding.rootView.backgroundTintList = ColorStateList.valueOf(backgroundColor)

}

