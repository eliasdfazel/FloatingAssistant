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

import android.app.AppOpsManager
import android.content.ComponentName
import android.content.Context
import android.os.Build
import android.os.Process
import android.provider.Settings
import android.text.TextUtils.SimpleStringSplitter
import co.geeksempire.floating.smart.panel.Interactions.InteractionsObserver


class SystemSettings (private val context: Context) {

    fun accessibilityServiceEnabled() : Boolean {

        val expectedComponentName = ComponentName(context, InteractionsObserver::class.java)

        val enabledServicesSetting = Settings.Secure.getString(context.contentResolver, Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES) ?: return false

        val colonSplitter = SimpleStringSplitter(':')
        colonSplitter.setString(enabledServicesSetting)

        while (colonSplitter.hasNext()) {

            val componentNameString = colonSplitter.next()

            val enabledService = ComponentName.unflattenFromString(componentNameString)

            if (enabledService != null && enabledService == expectedComponentName) {

                return true
            }

        }

        return false
    }

    fun floatingPermissionEnabled() : Boolean {

        return Settings.canDrawOverlays(context)
    }

    fun usageAccessEnabled() : Boolean {

        val appOps = context.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager

        val mode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            appOps.unsafeCheckOp("android:get_usage_stats", Process.myUid(), context.packageName)
        } else {
            appOps.checkOp("android:get_usage_stats", Process.myUid(), context.packageName)
        }

        return mode == AppOpsManager.MODE_ALLOWED
    }

}

fun returnApi() : Int {

    return Build.VERSION.SDK_INT
}