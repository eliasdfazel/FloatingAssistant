/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 3/13/23, 7:07 AM
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
import android.graphics.Color
import android.util.Log
import co.geeksempire.floating.smart.panel.Database.ArwenDatabase
import co.geeksempire.floating.smart.panel.Floating.Data.FloatingDataStructure
import co.geeksempire.floating.smart.panel.Floating.FloatingPanelServices
import co.geeksempire.floating.smart.panel.Preferences.UI.ColorsIO
import co.geeksempire.floating.smart.panel.Utils.Colors.setColorAlpha
import co.geeksempire.floating.smart.panel.Utils.Operations.ApplicationsData
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

                    val dominantColor = intent.getIntExtra(ColorsIO.Type.dominantColor, Color.BLACK)

                    val backgroundColor = setColorAlpha(dominantColor, floatingIO.transparency())

                    floatingLayoutBinding.rootView.backgroundTintList = ColorStateList.valueOf(backgroundColor)

                    floatingLayoutBinding.floatingHandheldGlow.imageTintList = ColorStateList.valueOf(dominantColor)
                    floatingLayoutBinding.floatingHandheld.imageTintList = ColorStateList.valueOf(dominantColor)

                } else if (intent.action == ArwenDatabase.DatabaseName) {

                    val applicationsData = ApplicationsData(applicationContext)

                    val packageName = intent.getStringExtra(Intent.EXTRA_TEXT)!!

                    val floatingDataStructure = FloatingDataStructure(
                        applicationPackageName = packageName,
                        applicationClassName = null,
                        applicationName = applicationsData.applicationName(packageName),
                        applicationIcon = applicationsData.applicationIcon(packageName)
                    )

                    this@registerFloatingBroadcasts.notifyDataSetUpdate(floatingDataStructure)

                }

            }

        }

    }
    registerReceiver(broadcastReceiver, intentFilter)

}