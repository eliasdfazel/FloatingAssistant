/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 3/13/23, 8:59 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.floating.smart.panel.Database.Process

import android.content.Context
import android.util.Log
import co.geeksempire.floating.smart.panel.Database.ArwenDataAccessObject
import co.geeksempire.floating.smart.panel.Database.ArwenDataStructure
import co.geeksempire.floating.smart.panel.Floating.Data.FloatingDataStructure
import co.geeksempire.floating.smart.panel.Utils.Operations.ApplicationsData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import java.util.*
import kotlin.math.abs

class Filters (private val context: Context) {

    object Level {
        const val Hours = 1
        const val Weekdays = 37
        const val Monthdays = 73
    }

    fun insertProcess(arwenDatabaseAccess: ArwenDataAccessObject,
                      linkElementOne: FloatingDataStructure, linkElementTwo: FloatingDataStructure) = CoroutineScope(Dispatchers.IO).async {
        Log.d(this@Filters.javaClass.simpleName, "Processing Links: ${linkElementOne.applicationPackageName} & ${linkElementTwo.applicationPackageName}")

        val calendar = Calendar.getInstance()

        val arwenLink = arwenDatabaseAccess.specificLink(linkElementOne.applicationPackageName, linkElementTwo.applicationPackageName)

        if (arwenLink != null) {
            Log.d(this@Filters.javaClass.simpleName, "Updating Link")

            arwenLink.Counter = arwenLink.Counter + 1

            arwenLink.TimeDay = "${calendar.get(Calendar.HOUR_OF_DAY)}${calendar.get(Calendar.MINUTE)}".toInt()
            arwenLink.TimeWeek = calendar.get(Calendar.DAY_OF_WEEK)
            arwenLink.TimeMonth = calendar.get(Calendar.DAY_OF_MONTH)

            arwenDatabaseAccess.update(arwenLink)

        } else {
            Log.d(this@Filters.javaClass.simpleName, "Inserting Link")

            val databaseIndex = arwenDatabaseAccess.rowCount()

            arwenDatabaseAccess.insert(ArwenDataStructure(
                Id = databaseIndex,

                Links = "${linkElementOne.applicationPackageName}-${linkElementTwo.applicationPackageName}",

                PackageOne = linkElementOne.applicationPackageName,
                ClassOne = linkElementOne.applicationClassName,
                PackageTwo = linkElementTwo.applicationPackageName,
                ClassTwo = linkElementTwo.applicationClassName,

                Counter = 1,

                TimeDay = "${calendar.get(Calendar.HOUR_OF_DAY)}${calendar.get(Calendar.MINUTE)}".toInt(),
                TimeWeek = calendar.get(Calendar.DAY_OF_WEEK),
                TimeMonth = calendar.get(Calendar.DAY_OF_MONTH)
            ))

        }

    }

    fun retrieveProcess(arwenDatabaseAccess: ArwenDataAccessObject, applicationsData: ApplicationsData, priorElement: FloatingDataStructure) : Deferred<ArrayList<FloatingDataStructure>> = CoroutineScope(Dispatchers.IO).async {

        val floatingDataStructures = ArrayList<FloatingDataStructure>()

        val inputDataSet = arwenDatabaseAccess.queryRelatedLinks(priorElement.applicationPackageName)

        if (inputDataSet != null) {

            floatingDataStructures.clear()

            if (inputDataSet.size > 73) {

                identifyNearestTime(filterPriority(inputDataSet, priorElement.applicationPackageName).await()).await().forEach {

                    floatingDataStructures.add(FloatingDataStructure(
                            applicationPackageName = it.PackageTwo,
                            applicationClassName = it.ClassTwo,
                            applicationName = applicationsData.activityName(it.PackageTwo, it.ClassTwo),
                            applicationIcon = applicationsData.activityIcon(it.PackageTwo, it.ClassTwo)
                    ))

                }

                floatingDataStructures

            } else {

                identifyNearestTime(inputDataSet).await().forEach {

                    val packageName = if (it.PackageOne == priorElement.applicationPackageName) {

                        it.PackageTwo

                    } else {

                        it.PackageOne

                    }

                    val className = if (it.PackageOne == priorElement.applicationClassName) {

                        it.ClassTwo

                    } else {

                        it.ClassOne

                    }

                    floatingDataStructures.add(FloatingDataStructure(
                        applicationPackageName = packageName,
                        applicationClassName = null,
                        applicationName = applicationsData.activityName(packageName, className),
                        applicationIcon = applicationsData.activityIcon(packageName, className)
                    ))

                }

                floatingDataStructures

            }

        } else {

            InitialDataSet(context).generate()

        }

    }

    fun generateInitials(arwenDatabaseAccess: ArwenDataAccessObject, applicationsData: ApplicationsData) : Deferred<ArrayList<FloatingDataStructure>> = CoroutineScope(Dispatchers.IO).async {

        val relatedLinksDayTime = arwenDatabaseAccess.queryRelatedDayTime(Calendar.getInstance().get(Calendar.DAY_OF_WEEK))

        if (relatedLinksDayTime != null) {

            val floatingDataStructures = ArrayList<FloatingDataStructure>()

            relatedLinksDayTime.slice(IntRange(0, 5)).forEach {

                val floatingDataStructure = FloatingDataStructure(
                    applicationPackageName = it.PackageTwo,
                    applicationClassName = it.ClassTwo,
                    applicationName = applicationsData.activityName(it.PackageTwo, it.ClassTwo),
                    applicationIcon = applicationsData.activityIcon(it.PackageTwo, it.ClassTwo)
                )

                if (!floatingDataStructures.contains(floatingDataStructure)) {

                    floatingDataStructures.add(floatingDataStructure)

                }

            }

            floatingDataStructures

        } else {

            InitialDataSet(context).generate()

        }

    }

    /**
     * @param priorElement Current Clicked Element
     **/
    private fun filterPriority(inputDataSet: List<ArwenDataStructure>, priorElement: String) : Deferred<List<ArwenDataStructure>> = CoroutineScope(Dispatchers.IO).async {

        inputDataSet.filter {

            (it.Links.split("-").first().contains(priorElement))
        }
    }

    /**
     * @param inputDataSet Data Set After Being Prioritize
     **/
    private fun identifyNearestTime(inputDataSet: List<ArwenDataStructure>) : Deferred<ArrayList<ArwenDataStructure>> = CoroutineScope(Dispatchers.IO).async {

        when (inputDataSet.size) {
            Filters.Level.Hours -> {

                nearestHours(inputDataSet)

            }
            Filters.Level.Weekdays -> {

                nearestWeekdays(inputDataSet)

            }
            Filters.Level.Monthdays -> {

                nearestMonthdays(inputDataSet)

            }
            else -> {

                nearestHours(inputDataSet)

            }
        }

    }

    private fun nearestHours(inputDataSet: List<ArwenDataStructure>) : ArrayList<ArwenDataStructure> {

        val calendar = Calendar.getInstance()

        val currentTime = "${calendar.get(Calendar.HOUR_OF_DAY)}${calendar.get(Calendar.MINUTE)}".toInt()

        val inputMap = HashMap<ArwenDataStructure, Int>()

        inputDataSet.forEach {

            inputMap[it] = abs(it.TimeDay - currentTime)

        }

        val nearElements = ArrayList<ArwenDataStructure>()

        val sortHashMap = inputMap.entries.sortedBy {

            it.value
        }

        sortHashMap.slice(IntRange(0, 5)).forEach {

            nearElements.add(it.key)

        }

        return nearElements

    }

    private fun nearestWeekdays(inputDataSet: List<ArwenDataStructure>) : ArrayList<ArwenDataStructure> {

        val calendar = Calendar.getInstance()

        val currentWeekday = "${calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH)}".toInt()

        val inputMap = HashMap<ArwenDataStructure, Int>()

        inputDataSet.forEach {

            inputMap[it] = abs(it.TimeWeek - currentWeekday)

        }

        val nearElements = ArrayList<ArwenDataStructure>()

        val sortHashMap = inputMap.entries.sortedBy {

            it.value
        }

        sortHashMap.slice(IntRange(0, 5)).forEach {

            nearElements.add(it.key)

        }

        return nearestHours(nearElements)

    }

    private fun nearestMonthdays(inputDataSet: List<ArwenDataStructure>) : ArrayList<ArwenDataStructure> {

        val calendar = Calendar.getInstance()

        val currentMonthday = "${calendar.get(Calendar.DAY_OF_MONTH)}".toInt()

        val inputMap = HashMap<ArwenDataStructure, Int>()

        inputDataSet.forEach {

            inputMap[it] = abs(it.TimeMonth - currentMonthday)

        }

        val nearElements = ArrayList<ArwenDataStructure>()

        val sortHashMap = inputMap.entries.sortedBy {

            it.value
        }

        sortHashMap.slice(IntRange(0, 5)).forEach {

            nearElements.add(it.key)

        }

        return nearestHours(nearestWeekdays(nearElements))

    }

}