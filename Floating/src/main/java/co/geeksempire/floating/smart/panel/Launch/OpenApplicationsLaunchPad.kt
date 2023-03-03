/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 3/3/23, 10:45 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */
package co.geeksempire.floating.smart.panel.Launch

import android.app.Activity
import android.os.Bundle

class OpenApplicationsLaunchPad : Activity() {

    override fun onCreate(Saved: Bundle?) {
        super.onCreate(Saved)

        val appPackageName: String? = intent.getStringExtra("packageName")

        appPackageName?.let {

            try {

                if (intent.hasExtra("className")) {

                    val appClassName = intent.getStringExtra("className")!!

                    openApplicationFromActivity(this@OpenApplicationsLaunchPad, appPackageName, appClassName)

                } else {

                    openApplicationFromActivity(this@OpenApplicationsLaunchPad, appPackageName)

                }

            } catch (e: Exception) {
                e.printStackTrace()

                openApplicationFromActivity(this@OpenApplicationsLaunchPad, appPackageName)

            }

        }

        this@OpenApplicationsLaunchPad.finish()
    }

}