/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 3/13/23, 10:39 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.floating.smart.panel.Utils.Operations

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import co.geeksempire.floating.smart.panel.R

class ApplicationsData (private val context: Context) {

    fun applicationName(packageName: String) : String {

        return  try {

            val applicationInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

                context.packageManager.getApplicationInfo(packageName, PackageManager.ApplicationInfoFlags.of(0))

            } else {

                context.packageManager.getApplicationInfo(packageName, 0)

            }

            context.packageManager.getApplicationLabel(applicationInfo).toString()

        } catch (e: Exception) {

            ""

        }
    }

    fun applicationIcon(packageName: String) : Drawable {

        return try {

            context.packageManager.getApplicationIcon(packageName)

        } catch (e: Exception) {

            ColorDrawable(context.getColor(R.color.primaryColorPurple))

        }
    }

    fun activityName(packageName: String, className: String?) : String {

        return if (className != null) {

            try {

                val activityInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    context.packageManager.getActivityInfo(ComponentName(packageName, className), PackageManager.ComponentInfoFlags.of(0))
                } else {
                    context.packageManager.getActivityInfo(ComponentName(packageName, className), 0)
                }

                activityInfo.loadLabel(context.packageManager).toString()

            } catch (e: Exception) {

                applicationName(packageName)

            }

        } else {

            applicationName(packageName)

        }
    }

    fun activityIcon(packageName: String, className: String?) : Drawable {

        return if (className != null) {

            try {

                val activityInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    context.packageManager.getActivityInfo(ComponentName(packageName, className), PackageManager.ComponentInfoFlags.of(0))
                } else {
                    context.packageManager.getActivityInfo(ComponentName(packageName, className), 0)
                }

                activityInfo.loadIcon(context.packageManager)

            } catch (e: Exception) {

                applicationIcon(packageName)

            }

        } else {

            applicationIcon(packageName)

        }
    }

    fun createActivityInformation(packageName: String) : ActivityInfo {

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

            try {

                context.packageManager.getActivityInfo(ComponentName(context, packageName), PackageManager.ComponentInfoFlags.of(0))

            } catch (e: Exception) {

                context.packageManager.getActivityInfo(ComponentName(context, packageName), 0)


            }

        } else {

            context.packageManager.getActivityInfo(ComponentName(context, packageName), 0)

        }
    }

    fun createActivityInformation(packageName: String, className: String) : ActivityInfo {

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

            try {

                context.packageManager.getActivityInfo(ComponentName(packageName, className), PackageManager.ComponentInfoFlags.of(0))

            } catch (e: Exception) {

                context.packageManager.getActivityInfo(ComponentName(packageName, className), 0)

            }

        } else {

            context.packageManager.getActivityInfo(ComponentName(packageName, className), 0)

        }
    }

    fun isSystemApplication(packageName: String) : Boolean {

        val packageManager = context.packageManager

        return try {

            val targetPackageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)

            val systemPackageInfo = packageManager.getPackageInfo("android", PackageManager.GET_SIGNATURES)

            targetPackageInfo?.signatures != null && (systemPackageInfo.signatures[0] == targetPackageInfo.signatures[0])

        } catch (e: PackageManager.NameNotFoundException) {

            false

        } catch (e: Exception) {

            false

        }
    }

    fun isDefaultLauncher(packageName: String) : Boolean {

        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)

        val defaultLauncher = context.packageManager.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY)

        val defaultLauncherPackageName = defaultLauncher?.activityInfo?.packageName

        return (defaultLauncherPackageName == packageName)
    }

    fun canLaunch(packageName: String) : Boolean {

        return context.packageManager.getLaunchIntentForPackage(packageName) != null
    }

}