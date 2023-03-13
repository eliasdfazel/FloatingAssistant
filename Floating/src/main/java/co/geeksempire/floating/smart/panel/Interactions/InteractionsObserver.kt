/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 3/13/23, 10:53 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.floating.smart.panel.Interactions

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import androidx.room.Room
import co.geeksempire.floating.smart.panel.Database.ArwenDataAccessObject
import co.geeksempire.floating.smart.panel.Database.ArwenDataInterface
import co.geeksempire.floating.smart.panel.Database.ArwenDatabase
import co.geeksempire.floating.smart.panel.Database.Process.Filters
import co.geeksempire.floating.smart.panel.Floating.Data.FloatingDataStructure
import co.geeksempire.floating.smart.panel.Utils.Operations.ApplicationsData

class InteractionsObserver : AccessibilityService() {

    private val filters: Filters by lazy {
        Filters(applicationContext)
    }


    private val applicationsData: ApplicationsData by lazy {
        ApplicationsData(applicationContext)
    }


    private val arwenDatabaseAccess: ArwenDataAccessObject by lazy {
        Room.databaseBuilder(applicationContext, ArwenDataInterface::class.java, ArwenDatabase.DatabaseName).build().initializeDataAccessObject()
    }

    override fun onServiceConnected() {

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int) : Int {

        return START_STICKY
    }

    override fun onAccessibilityEvent(accessibilityEvent: AccessibilityEvent?) {

        accessibilityEvent?.let {

            when (accessibilityEvent.eventType) {
                AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED -> {

                    if (!ArwenDatabase.DatabaseHandled) {

                        val packageName = accessibilityEvent.packageName.toString()
                        val className = accessibilityEvent.className.toString()

                        val floatingDataStructure = FloatingDataStructure(
                            applicationPackageName = packageName,
                            applicationClassName = className,
                            applicationName = applicationsData.activityName(packageName, className),
                            applicationIcon = applicationsData.activityIcon(packageName, className)
                        )

                        if (filters.validateEntry(ArwenDatabase.clickedApplicationData, floatingDataStructure, applicationsData)) {
                            Log.d(this@InteractionsObserver.javaClass.simpleName, "Application: $packageName | $className")

                            ArwenDatabase.clickedApplicationData.add(floatingDataStructure)

                            sendBroadcast(Intent(ArwenDatabase.DatabaseName).putExtra(Intent.EXTRA_TEXT, packageName))

                            if (ArwenDatabase.clickedApplicationData.size == 2) {
                                Log.d(this@InteractionsObserver.javaClass.simpleName, "Start Database Queries")

                                filters.insertProcess(arwenDatabaseAccess, ArwenDatabase.clickedApplicationData[0], ArwenDatabase.clickedApplicationData[1])

                                ArwenDatabase.clickedApplicationData.removeFirst()

                            }

                        }

                    } else {

                        ArwenDatabase.DatabaseHandled = false

                    }

                }
                else -> {}
            }

        }

    }

    override fun onInterrupt() {

    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}