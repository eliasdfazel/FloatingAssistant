/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 2/27/23, 10:56 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.floating.smart.panel.Interactions

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import co.geeksempire.floating.smart.panel.Utils.Settings.SystemSettings

class InteractionsObserver : AccessibilityService() {

    val systemSettings: SystemSettings by lazy {
        SystemSettings(applicationContext)
    }

    override fun onServiceConnected() {

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        return START_STICKY
    }

    override fun onAccessibilityEvent(accessibilityEvent: AccessibilityEvent?) {

        accessibilityEvent?.let {

            when (accessibilityEvent.eventType) {
                AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED -> {

                    val packageName = accessibilityEvent.packageName.toString()
                    val className = accessibilityEvent.className.toString()

                    Log.d(this@InteractionsObserver.javaClass.simpleName, "Application: $packageName | $className")




                    // perform database process




                }
                else -> {}
            }

        }

    }

    override fun onInterrupt() {

    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}