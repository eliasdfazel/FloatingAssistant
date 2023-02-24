/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 2/24/23, 8:13 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.floating.smart.panel.Dashboard.Extensions

import android.content.res.ColorStateList
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import co.geeksempire.floating.smart.panel.Dashboard.UI.Dashboard
import co.geeksempire.floating.smart.panel.R
import co.geeksempire.floating.smart.panel.Utils.Views.Switch.SwitchController
import co.geeksempire.floating.smart.panel.Utils.Views.Switch.SwitchInterface
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

fun Dashboard.setupUserInterface() {

    fireaseUser?.let {

        dashboardLayoutBinding.indicatorText.text = getString(R.string.profile)

        Glide.with(applicationContext)
            .asDrawable()
            .load(it.photoUrl)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .listener(object : RequestListener<Drawable> {

                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {

                    return false
                }

                override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {

                    runOnUiThread {

                        val profileColor = palettes.extractDominantColor(drawable = resource)

                        dashboardLayoutBinding.profileImageGlow.imageTintList = ColorStateList.valueOf(profileColor)
                        dashboardLayoutBinding.profileImageBackground.setImageDrawable(ColorDrawable(profileColor))

                    }

                    return false
                }

            })
            .submit()

    }

    /* Start - Interactions  */
    dashboardLayoutBinding.interactions.preferencesTitle.text = getString(R.string.interactionsTitle)
    dashboardLayoutBinding.interactions.preferencesDescription.text = getString(R.string.interactionsDescription)

    dashboardLayoutBinding.interactions.preferencesTitle.setOnClickListener {  }

    val switchControllerInteraction = SwitchController(applicationContext,
        dashboardLayoutBinding.interactions.switchBackground, dashboardLayoutBinding.interactions.switchHandheld)
    switchControllerInteraction.switchStatus = systemSettings.accessibilityServiceEnabled()
    switchControllerInteraction.switchIt(object : SwitchInterface {

        override fun switchedOn() {



        }

        override fun switchedOff() {



        }

        override fun switchedIt() {



        }

    })
    /* End - Interactions  */

    /* Start - Floating  */
    dashboardLayoutBinding.floatingPermission.preferencesTitle.text = getString(R.string.floatingTitle)
    dashboardLayoutBinding.floatingPermission.preferencesDescription.text = getString(R.string.floatingDescription)

    dashboardLayoutBinding.floatingPermission.preferencesTitle.setOnClickListener {  }

    val switchControllerFloating = SwitchController(applicationContext,
        dashboardLayoutBinding.floatingPermission.switchBackground, dashboardLayoutBinding.floatingPermission.switchHandheld)
    switchControllerFloating.switchStatus = systemSettings.floatingPermissionEnabled()
    switchControllerFloating.switchIt(object : SwitchInterface {

        override fun switchedOn() {



        }

        override fun switchedOff() {



        }

        override fun switchedIt() {



        }

    })
    /* End - Floating  */

    /* Start - Usage Access  */
    dashboardLayoutBinding.usageAccess.preferencesTitle.text = getString(R.string.usageAccessTitle)
    dashboardLayoutBinding.usageAccess.preferencesDescription.text = getString(R.string.usageAccessDescription)

    dashboardLayoutBinding.usageAccess.preferencesTitle.setOnClickListener {  }

    val switchControllerUsage = SwitchController(applicationContext,
        dashboardLayoutBinding.usageAccess.switchBackground, dashboardLayoutBinding.usageAccess.switchHandheld)
    switchControllerUsage.switchStatus = systemSettings.usageAccessEnabled()
    switchControllerUsage.switchIt(object : SwitchInterface {

        override fun switchedOn() {



        }

        override fun switchedOff() {



        }

        override fun switchedIt() {



        }

    })
    /* End - Usage Access  */

}