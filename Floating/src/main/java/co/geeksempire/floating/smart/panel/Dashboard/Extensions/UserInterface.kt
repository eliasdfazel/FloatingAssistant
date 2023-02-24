/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 2/24/23, 9:23 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.floating.smart.panel.Dashboard.Extensions

import android.app.ActivityOptions
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.view.View
import android.view.animation.AnimationUtils
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

            Handler(Looper.getMainLooper()).postDelayed({

                startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK),
                    ActivityOptions.makeCustomAnimation(applicationContext, R.anim.fade_in, 0).toBundle())

            }, 999)

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

            Handler(Looper.getMainLooper()).postDelayed({

                startActivity(Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName")).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK),
                    ActivityOptions.makeCustomAnimation(applicationContext, R.anim.fade_in, 0).toBundle())

            }, 999)
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

            Handler(Looper.getMainLooper()).postDelayed({

                startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK),
                    ActivityOptions.makeCustomAnimation(applicationContext, R.anim.fade_in, 0).toBundle())

            }, 999)

        }

    })
    /* End - Usage Access  */

    /* Start - Launch Button */
    if (systemSettings.accessibilityServiceEnabled()
        && systemSettings.floatingPermissionEnabled()) {

        dashboardLayoutBinding.launchButton.visibility = View.VISIBLE
        dashboardLayoutBinding.launchButton.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.fade_in))

        dashboardLayoutBinding.launchButton.setOnClickListener {



        }

    }
    /* End - Launch Button */

}