/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 3/6/23, 8:12 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.floating.smart.panel.Dashboard.UI

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import co.geeksempire.floating.smart.panel.BuildConfig
import co.geeksempire.floating.smart.panel.Dashboard.Extensions.setupUserInterface
import co.geeksempire.floating.smart.panel.Preferences.UI.ColorsIO
import co.geeksempire.floating.smart.panel.R
import co.geeksempire.floating.smart.panel.Tests.PrototypeData
import co.geeksempire.floating.smart.panel.Utils.Colors.Palettes
import co.geeksempire.floating.smart.panel.Utils.Notifications.NotificationsCreator
import co.geeksempire.floating.smart.panel.Utils.Settings.SystemSettings
import co.geeksempire.floating.smart.panel.databinding.DashboardLayoutBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Dashboard : AppCompatActivity() {

    val fireaseUser = Firebase.auth.currentUser

    val systemSettings: SystemSettings by lazy {
        SystemSettings(applicationContext)
    }

    val colorsIO: ColorsIO by lazy {
        ColorsIO(applicationContext)
    }

    val notificationsCreator = NotificationsCreator()

    val palettes: Palettes by lazy {
        Palettes(applicationContext)
    }

    private object RequestId {
        const val Permissions = 0
    }

    lateinit var dashboardLayoutBinding: DashboardLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dashboardLayoutBinding = DashboardLayoutBinding.inflate(layoutInflater)
        setContentView(dashboardLayoutBinding.root)

        window.decorView.setBackgroundColor(getColor(R.color.black))

        val allPermissions: ArrayList<String> = ArrayList<String>()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            allPermissions.add(Manifest.permission.POST_NOTIFICATIONS)
        }

        requestPermissions(allPermissions.toTypedArray(), Dashboard.RequestId.Permissions)

        colorsIO.processWallpaperColors()

    }

    override fun onStart() {
        super.onStart()

        if (BuildConfig.DEBUG) {

            dashboardLayoutBinding.prototypeGenerator.visibility = View.VISIBLE

            dashboardLayoutBinding.prototypeGenerator.setOnClickListener {

                PrototypeData(applicationContext)
                    .generate()

            }

        }

    }

    override fun onResume() {
        super.onResume()

        setupUserInterface()

    }

}