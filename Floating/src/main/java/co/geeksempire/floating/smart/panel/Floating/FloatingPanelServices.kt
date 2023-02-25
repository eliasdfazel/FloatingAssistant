/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 2/25/23, 9:33 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.floating.smart.panel.Floating

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.geeksempire.floating.smart.panel.Floating.Adapter.FloatingAdapter
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

        val linearLayoutManager = LinearLayoutManager(applicationContext, RecyclerView.HORIZONTAL, false)
        floatingLayoutBinding.floatingRecyclerView.layoutManager = linearLayoutManager

        floatingLayoutBinding.floatingRecyclerView.adapter = floatingAdapter



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

}