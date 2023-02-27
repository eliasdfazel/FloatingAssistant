/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 2/27/23, 11:08 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.floating.smart.panel.Floating

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.res.ColorStateList
import android.os.IBinder
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.geeksempire.floating.smart.panel.Floating.Adapter.FloatingAdapter
import co.geeksempire.floating.smart.panel.Preferences.UI.ColorsIO
import co.geeksempire.floating.smart.panel.Utils.Notifications.NotificationsCreator
import co.geeksempire.floating.smart.panel.databinding.FloatingLayoutBinding

class FloatingPanelServices : Service() {

    companion object {
        var Floating = false
    }

    private val notificationsCreator = NotificationsCreator()

    private val layoutInflater: LayoutInflater by lazy {
        getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    private val floatingAdapter: FloatingAdapter by lazy {
        FloatingAdapter(applicationContext, layoutInflater)
    }

    override fun onBind(intent: Intent?): IBinder? { return null }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)

        val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager

        val floatingLayoutBinding = FloatingLayoutBinding.inflate(layoutInflater)

        windowManager.addView(floatingLayoutBinding.root, notificationsCreator.generateLayoutParameters(applicationContext, 73, 301, 333, 333))

        registerFloatingBroadcasts(floatingLayoutBinding)

        val linearLayoutManager = LinearLayoutManager(applicationContext, RecyclerView.HORIZONTAL, false)
        floatingLayoutBinding.floatingRecyclerView.layoutManager = linearLayoutManager

        floatingLayoutBinding.floatingRecyclerView.adapter = floatingAdapter


        // if final list empty then show random apps from most used apps


        return START_STICKY
    }

    override fun onCreate() {
        super.onCreate()

        startForeground(333, notificationsCreator.bindNotification(applicationContext))

        FloatingPanelServices.Floating = true

    }

    override fun onDestroy() {
        super.onDestroy()

        FloatingPanelServices.Floating = false

    }

    private fun registerFloatingBroadcasts(floatingLayoutBinding: FloatingLayoutBinding) {

        val intentFilter = IntentFilter()
        intentFilter.addAction(ColorsIO.Type.colorsChanged)
        val broadcastReceiver = object : BroadcastReceiver() {

            override fun onReceive(context: Context?, intent: Intent?) {

                intent?.let {

                    if (intent.action == ColorsIO.Type.colorsChanged) {

                        floatingLayoutBinding.floatingBackground.imageTintList = ColorStateList.valueOf(intent.getIntExtra(ColorsIO.Type.dominantColor, 0))

                    }

                }

            }

        }
        registerReceiver(broadcastReceiver, intentFilter)

    }

}