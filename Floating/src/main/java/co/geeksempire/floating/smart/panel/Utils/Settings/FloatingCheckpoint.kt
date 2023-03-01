/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 3/1/23, 7:19 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.floating.smart.panel.Utils.Settings

import android.annotation.TargetApi
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import org.lsposed.hiddenapibypass.HiddenApiBypass

class FloatingCheckpoint {

    object WindowMode {
        const val WINDOWING_MODE_FREEFORM = 5
        const val FREEFORM_WORKSPACE_STACK_ID = 2
    }

    fun freeFormSupport(context: Context): Boolean {
        return context.packageManager.hasSystemFeature(PackageManager.FEATURE_FREEFORM_WINDOW_MANAGEMENT) ||
                Settings.Global.getInt(
                    context.contentResolver,
                    "enable_freeform_support",
                    0
                ) != 0 && Settings.Global.getInt(
            context.contentResolver,
            "force_resizable_activities",
            0
        ) != 0
    }

    fun getWindowingModeMethodName(): String? {
        return if (returnApi() >= 28) "setLaunchWindowingMode" else "setLaunchStackId"
    }

    private fun getFreeformWindowModeId(): Int {
        return if (returnApi() >= 28) {
            WindowMode.WINDOWING_MODE_FREEFORM
        } else {
            WindowMode.FREEFORM_WORKSPACE_STACK_ID
        }
    }

    @TargetApi(Build.VERSION_CODES.P)
    fun allowReflection() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            HiddenApiBypass.addHiddenApiExemptions("")
        }
    }

}