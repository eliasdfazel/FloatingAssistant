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
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Handler
import android.os.Looper
import co.geeksempire.floating.smart.panel.Utils.Display.displaySection
import co.geeksempire.floating.smart.panel.Utils.Display.displayX
import co.geeksempire.floating.smart.panel.Utils.Display.displayY
import co.geeksempire.floating.smart.panel.Utils.Settings.FloatingCheckpoint
import co.geeksempire.floating.smart.panel.Utils.Settings.returnApi

fun openApplicationFromActivity(instanceOfActivity: Activity, packageName: String, className: String) {

    val openAlias = Intent()
    openAlias.setClassName(packageName, className)
    openAlias.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    instanceOfActivity.startActivity(openAlias)

}

fun openApplicationFromActivity(instanceOfActivity: Activity, packageName: String) {

    val launchIntentForPackage: Intent? = instanceOfActivity.packageManager.getLaunchIntentForPackage(packageName)

    launchIntentForPackage?.let {
        launchIntentForPackage.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        instanceOfActivity.startActivity(launchIntentForPackage)
    }

}

fun openApplicationFreeForm(
    context: Context,
    PackageName: String,
    leftPositionX: Int /*X*/,
    rightPositionX: Int /*displayX/2*/,
    topPositionY: Int /*Y*/,
    bottomPositionY: Int /*displayY/2*/
) {

    val floatingCheckpoint = FloatingCheckpoint()

    //Enable Developer Option & Turn ON 'Force Activities to be Resizable'
    //adb shell settings put global enable_freeform_support 1
    //adb shell settings put global force_resizable_activities 1

    if (returnApi() < 28) {
        val homeScreen = Intent(Intent.ACTION_MAIN)
        homeScreen.addCategory(Intent.CATEGORY_HOME)
        homeScreen.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(homeScreen)
    }

    Handler(Looper.getMainLooper()).postDelayed({

        val activityOptions = ActivityOptions.makeBasic()

        try {
            floatingCheckpoint.allowReflection()
            val method = ActivityOptions::class.java.getMethod(floatingCheckpoint.getWindowingModeMethodName(), Int::class.javaPrimitiveType)
            method.invoke(activityOptions, floatingCheckpoint.getFreeformWindowModeId())
        } catch (e: Exception) {
            e.printStackTrace()
        }

        when (displaySection(context, leftPositionX, topPositionY)) {
            FloatingCheckpoint.DisplaySection.TopLeft -> {
                activityOptions.launchBounds = Rect(
                    leftPositionX,
                    topPositionY,
                    rightPositionX,
                    bottomPositionY
                )
            }
            FloatingCheckpoint.DisplaySection.TopRight -> {
                activityOptions.launchBounds = Rect(
                    leftPositionX - displayX(context) / 2,
                    topPositionY,
                    leftPositionX,
                    bottomPositionY
                )
            }
            FloatingCheckpoint.DisplaySection.BottomRight -> {
                activityOptions.launchBounds = Rect(
                    leftPositionX - displayX(context) / 2,
                    topPositionY - displayY(context) / 2,
                    leftPositionX,
                    topPositionY
                )
            }
            FloatingCheckpoint.DisplaySection.BottomLeft -> {
                activityOptions.launchBounds = Rect(
                    leftPositionX,
                    topPositionY - displayY(context) / 2,
                    leftPositionX + displayX(context) / 2,
                    topPositionY
                )
            }
            else -> {
                activityOptions.launchBounds = Rect(
                    displayX(context) / 4,
                    displayX(context) / 2,
                    displayY(context) / 4,
                    displayY(context) / 2
                )
            }
        }

        val openAlias: Intent? = context.packageManager.getLaunchIntentForPackage(PackageName)
        if (openAlias != null) {
            openAlias.flags = Intent.FLAG_ACTIVITY_LAUNCH_ADJACENT or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_NO_ANIMATION
        }
        context.startActivity(openAlias, activityOptions.toBundle())

    }, 3000)

}

fun openApplicationFreeForm(
    context: Context,
    PackageName: String,
    ClassName: String,
    leftPositionX: Int /*X*/,
    rightPositionX: Int,
    topPositionY: Int /*Y*/,
    bottomPositionY: Int
) {

    val floatingCheckpoint = FloatingCheckpoint()

    //Enable Developer Option & Turn ON 'Force Activities to be Resizable'
    //adb shell settings put global enable_freeform_support 1
    //adb shell settings put global force_resizable_activities 1

    if (returnApi() < 28) {
        val homeScreen = Intent(Intent.ACTION_MAIN)
        homeScreen.addCategory(Intent.CATEGORY_HOME)
        homeScreen.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(homeScreen)
    }

    Handler(Looper.getMainLooper()).postDelayed({

        val activityOptions = ActivityOptions.makeBasic()

        try {
            floatingCheckpoint.allowReflection()
            val method = ActivityOptions::class.java.getMethod(floatingCheckpoint.getWindowingModeMethodName(), Int::class.javaPrimitiveType)
            method.invoke(activityOptions, floatingCheckpoint.getFreeformWindowModeId())
        } catch (e: Exception) {
            e.printStackTrace()
        }

        when (displaySection(context, leftPositionX, topPositionY)) {
            FloatingCheckpoint.DisplaySection.TopLeft -> {
                activityOptions.launchBounds = Rect(
                    leftPositionX,
                    topPositionY,
                    rightPositionX,
                    bottomPositionY
                )
            }
            FloatingCheckpoint.DisplaySection.TopRight -> {
                activityOptions.launchBounds = Rect(
                    leftPositionX - displayX(context) / 2,
                    topPositionY,
                    leftPositionX,
                    bottomPositionY
                )
            }
            FloatingCheckpoint.DisplaySection.BottomRight -> {
                activityOptions.launchBounds = Rect(
                    leftPositionX - displayX(context) / 2,
                    topPositionY - displayY(context) / 2,
                    leftPositionX,
                    topPositionY
                )
            }
            FloatingCheckpoint.DisplaySection.BottomLeft -> {
                activityOptions.launchBounds = Rect(
                    leftPositionX,
                    topPositionY - displayY(context) / 2,
                    leftPositionX + displayX(context) / 2,
                    topPositionY
                )
            }
            else -> {
                activityOptions.launchBounds = Rect(
                    displayX(context) / 4,
                    displayX(context) / 2,
                    displayY(context) / 4,
                    displayY(context) / 2
                )
            }
        }

        val openAlias = Intent()
        openAlias.setClassName(PackageName!!, ClassName!!)
        openAlias.addCategory(Intent.CATEGORY_LAUNCHER)
        openAlias.flags = Intent.FLAG_ACTIVITY_LAUNCH_ADJACENT or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_NO_ANIMATION
        context.startActivity(openAlias, activityOptions.toBundle())

    }, 3000)


}