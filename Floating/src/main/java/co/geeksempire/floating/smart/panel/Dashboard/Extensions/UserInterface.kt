/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 3/14/23, 8:28 AM
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
import androidx.constraintlayout.widget.ConstraintLayout
import co.geeksempire.floating.smart.panel.BuildConfig
import co.geeksempire.floating.smart.panel.Dashboard.UI.Dashboard
import co.geeksempire.floating.smart.panel.Floating.FloatingPanelServices
import co.geeksempire.floating.smart.panel.R
import co.geeksempire.floating.smart.panel.Utils.Animations.alphaAnimation
import co.geeksempire.floating.smart.panel.Utils.Display.dpToInteger
import co.geeksempire.floating.smart.panel.Utils.Display.statusBarHeight
import co.geeksempire.floating.smart.panel.Utils.Views.Dialogue.ConfirmDialogue
import co.geeksempire.floating.smart.panel.Utils.Views.Dialogue.ConfirmDialogueInterface
import co.geeksempire.floating.smart.panel.Utils.Views.Switch.SwitchController
import co.geeksempire.floating.smart.panel.Utils.Views.Switch.SwitchInterface
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target


fun Dashboard.setupUserInterface() {

    colorsIO.processWallpaperColors()

    setupProfile()

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
    if ((systemSettings.accessibilityServiceEnabled()
        && systemSettings.floatingPermissionEnabled()
        && systemSettings.usageAccessEnabled()) || BuildConfig.DEBUG) {

        dashboardLayoutBinding.launchButtonBackground.visibility = View.VISIBLE
        dashboardLayoutBinding.launchButtonBackground.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.fade_in))

        dashboardLayoutBinding.launchButtonBlurry.visibility = View.VISIBLE
        dashboardLayoutBinding.launchButtonBlurry.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.fade_in))

        alphaAnimation(view =  dashboardLayoutBinding.launchButtonBlurry, animationStatus = this@setupUserInterface)

        dashboardLayoutBinding.launchButton.visibility = View.VISIBLE
        dashboardLayoutBinding.launchButton.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.fade_in))

        dashboardLayoutBinding.launchButton.setOnClickListener {

            notificationsCreator.playNotificationSound(this@setupUserInterface, R.raw.titan)
            notificationsCreator.doVibrate(applicationContext, 73)

            if (!FloatingPanelServices.Floating) {

                val confirmDialogue = ConfirmDialogue(context = this@setupUserInterface, viewGroup = dashboardLayoutBinding.root)
                confirmDialogue.initialize(dialogueTitle = getString(R.string.floatingPanelTitle), dialogueDescription = getString(R.string.floatingPanelDescription))
                    .show(object : ConfirmDialogueInterface {

                        override fun confirmed() {}

                        override fun dismissed() {}

                    })

                startForegroundService(Intent(applicationContext, FloatingPanelServices::class.java))

            } else {

                val confirmDialogue = ConfirmDialogue(context = this@setupUserInterface, viewGroup = dashboardLayoutBinding.root)
                confirmDialogue.initialize(dialogueTitle = getString(R.string.floatingPanelTitle), dialogueDescription = getString(R.string.removeFloatingPanelDescription))
                    .show(object : ConfirmDialogueInterface {

                        override fun confirmed() {

                            stopService(Intent(applicationContext, FloatingPanelServices::class.java))

                        }

                        override fun dismissed() {}

                    })

            }

        }

    }
    /* End - Launch Button */

}

fun Dashboard.setupProfile() {

    firebaseUser?.let {

        dashboardLayoutBinding.indicatorText.text = getString(R.string.profile)

        Glide.with(applicationContext)
            .asDrawable()
            .load(it.photoUrl)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .listener(object : RequestListener<Drawable> {

                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean) : Boolean {

                    return false
                }

                override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean) : Boolean {

                    runOnUiThread {

                        val profileColor = palettes.extractDominantColor(drawable = resource)

                        dashboardLayoutBinding.apply {

                            profileImageGlow.imageTintList = ColorStateList.valueOf(profileColor)
                            profileImageBackground.setImageDrawable(ColorDrawable(profileColor))

                            profileImage.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.fade_in))

                            profileImage.setImageDrawable(resource)

                            dashboardLayoutBinding.waitingAnimation.visibility = View.GONE

                        }

                    }

                    return false
                }

            })
            .submit()

    }

    /* Start - Profile */
    val profileImageLayoutParameters = dashboardLayoutBinding.profileImageBackground.layoutParams as ConstraintLayout.LayoutParams
    profileImageLayoutParameters.topMargin = dpToInteger(applicationContext, 37) + statusBarHeight(applicationContext)
    dashboardLayoutBinding.profileImageBackground.layoutParams = profileImageLayoutParameters

    val profileImageGlowLayoutParameters = dashboardLayoutBinding.profileImageGlow.layoutParams as ConstraintLayout.LayoutParams
    profileImageGlowLayoutParameters.topMargin = -dpToInteger(applicationContext, 27) + statusBarHeight(applicationContext)
    dashboardLayoutBinding.profileImageGlow.layoutParams = profileImageGlowLayoutParameters

    dashboardLayoutBinding.contentWrapper.setPadding(0, dpToInteger(applicationContext, 173) + statusBarHeight(applicationContext), 0, dashboardLayoutBinding.contentWrapper.paddingBottom)
    /* end - Profile */

}