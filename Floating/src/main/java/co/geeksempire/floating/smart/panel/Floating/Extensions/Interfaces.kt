/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 3/11/23, 7:38 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.floating.smart.panel.Floating.Extensions

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.res.ColorStateList
import android.util.Log
import co.geeksempire.floating.smart.panel.Floating.FloatingPanelServices
import co.geeksempire.floating.smart.panel.Preferences.UI.ColorsIO
import co.geeksempire.floating.smart.panel.Utils.Colors.setColorAlpha
import co.geeksempire.floating.smart.panel.databinding.FloatingLayoutBinding

fun FloatingPanelServices.registerFloatingBroadcasts(floatingLayoutBinding: FloatingLayoutBinding) {

    val intentFilter = IntentFilter().apply {
        addAction(ColorsIO.Type.colorsChanged)
    }
    broadcastReceiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context?, intent: Intent?) {

            intent?.let {

                if (intent.action == ColorsIO.Type.colorsChanged) {
                    Log.d(this@registerFloatingBroadcasts.javaClass.simpleName, "New Background Color Received")

                    val backgroundColor = setColorAlpha(intent.getIntExtra(ColorsIO.Type.dominantColor, 0), floatingIO.transparency())

                    floatingLayoutBinding.rootView.backgroundTintList = ColorStateList.valueOf(backgroundColor)

                    floatingLayoutBinding.floatingHandheldGlow.imageTintList = ColorStateList.valueOf(backgroundColor)
                    floatingLayoutBinding.floatingHandheld.imageTintList = ColorStateList.valueOf(backgroundColor)

                }

            }

        }

    }
    registerReceiver(broadcastReceiver, intentFilter)

}