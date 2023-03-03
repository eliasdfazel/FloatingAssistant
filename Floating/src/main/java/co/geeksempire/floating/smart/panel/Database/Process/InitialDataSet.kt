/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 3/3/23, 9:10 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.floating.smart.panel.Database.Process

import android.app.usage.UsageStatsManager
import android.content.Context
import android.util.Log
import co.geeksempire.floating.smart.panel.Floating.Data.FloatingDataStructure
import co.geeksempire.floating.smart.panel.Utils.Operations.ApplicationsData

class InitialDataSet (private val context: Context) {

    private val applicationsData = ApplicationsData(context)

    fun generate(daysAgo: Long = 7) : ArrayList<FloatingDataStructure> {

        val usageStatsManager = context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager

        val queryUsageStats = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, (86400000 * daysAgo).toLong(), System.currentTimeMillis())

        queryUsageStats.sortWith { usageStatsLeft, usageStatsRight ->

            usageStatsRight.lastTimeUsed.compareTo(usageStatsLeft.lastTimeUsed)
        }

        val randomApplications = ArrayList<FloatingDataStructure>()

        queryUsageStats.slice(IntRange(0, 19)).forEach { usageStat ->
            Log.d(this@InitialDataSet.javaClass.simpleName, "Package Name: ${usageStat.packageName}")

            if (usageStat.packageName != context.packageName
                && !applicationsData.isDefaultLauncher(usageStat.packageName)
                && !applicationsData.isSystemApplication(usageStat.packageName)
                && applicationsData.canLaunch(usageStat.packageName)) {

                randomApplications.add(FloatingDataStructure(
                    applicationPackageName = usageStat.packageName,
                    applicationName = applicationsData.activityLabel(applicationsData.createActivityInformation(usageStat.packageName)),
                    applicationIcon = applicationsData.applicationIcon(applicationsData.createActivityInformation(usageStat.packageName))
                ))

            }

        }

        return randomApplications
    }

}