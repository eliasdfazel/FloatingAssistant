/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 3/6/23, 7:13 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.floating.smart.panel.Floating.Extensions

import co.geeksempire.floating.smart.panel.Floating.FloatingPanelServices
import co.geeksempire.floating.smart.panel.Utils.Colors.setColorAlpha
import co.geeksempire.floating.smart.panel.databinding.FloatingLayoutBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun FloatingPanelServices.setupUserInterface(floatingLayoutBinding: FloatingLayoutBinding) = CoroutineScope(Dispatchers.Main).launch {

    colorsIO.dominantColor().collect { dominantColor ->

        println(">>> >> > ${dominantColor}")

        val backgroundColor = setColorAlpha(dominantColor, 173f)

        floatingLayoutBinding.rootView.setBackgroundColor(backgroundColor)



    }

}

