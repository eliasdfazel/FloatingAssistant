/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 3/6/23, 8:21 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.floating.smart.panel.Floating.Extensions

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import co.geeksempire.floating.smart.panel.Floating.FloatingPanelServices
import co.geeksempire.floating.smart.panel.Preferences.UI.ColorsIO
import co.geeksempire.floating.smart.panel.Utils.Colors.setColorAlpha
import co.geeksempire.floating.smart.panel.databinding.FloatingLayoutBinding

fun FloatingPanelServices.registerFloatingBroadcasts(floatingLayoutBinding: FloatingLayoutBinding) {

    val intentFilter = IntentFilter().apply {
        addAction(ColorsIO.Type.colorsChanged)
    }
    val broadcastReceiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context?, intent: Intent?) {

            intent?.let {

                if (intent.action == ColorsIO.Type.colorsChanged) {
                    Log.d(this@registerFloatingBroadcasts.javaClass.simpleName, "New Background Color Received")

                    val backgroundColor = setColorAlpha(intent.getIntExtra(ColorsIO.Type.dominantColor, 0), 173f)

                    floatingLayoutBinding.rootView.setBackgroundColor(backgroundColor)

                }

            }

        }

    }
    registerReceiver(broadcastReceiver, intentFilter)

}