/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 3/6/23, 11:37 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.floating.smart.panel.Boot

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import co.geeksempire.floating.smart.panel.Floating.FloatingPanelServices
import co.geeksempire.floating.smart.panel.R
import co.geeksempire.floating.smart.panel.Utils.Notifications.NotificationsCreator
import co.geeksempire.floating.smart.panel.Utils.Settings.SystemSettings

class OpenOnBoot : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val systemSettings = SystemSettings(applicationContext)

        if (systemSettings.accessibilityServiceEnabled()
            && systemSettings.floatingPermissionEnabled()
            && systemSettings.usageAccessEnabled()) {

            val notificationsCreator = NotificationsCreator()

            notificationsCreator.playNotificationSound(this@OpenOnBoot, R.raw.titan)
            notificationsCreator.doVibrate(applicationContext, 73)

            if (!FloatingPanelServices.Floating) {

                startForegroundService(Intent(applicationContext, FloatingPanelServices::class.java))

            }

        }

        this@OpenOnBoot.finish()

    }

}