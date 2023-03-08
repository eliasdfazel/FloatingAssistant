/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 3/8/23, 7:35 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.floating.smart.panel.Database.Process

import co.geeksempire.floating.smart.panel.Database.ArwenDataStructure
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import java.util.*
import kotlin.math.abs

class Filters {

    object Level {
        const val Hours = 1
        const val Weekdays = 37
        const val Monthdays = 73
    }

    /**
     * @param priorElement Current Clicked Element
     **/
    fun filterPriority(inputDataSet: ArrayList<ArwenDataStructure>, priorElement: String) : Deferred<List<ArwenDataStructure>> = CoroutineScope(Dispatchers.IO).async {

        inputDataSet.filter {

            (it.Links.split("-").first().contains(priorElement))
        }
    }

    /**
     * @param inputDataSet Data Set After Being Prioritize
     **/
    fun identifyNearestTime(inputDataSet: ArrayList<ArwenDataStructure>) : Deferred<List<ArwenDataStructure>> = CoroutineScope(Dispatchers.IO).async {

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

                inputDataSet

            }
        }

    }

    fun nearestHours(inputDataSet: ArrayList<ArwenDataStructure>) : ArrayList<ArwenDataStructure> {

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

    fun nearestWeekdays(inputDataSet: ArrayList<ArwenDataStructure>) : ArrayList<ArwenDataStructure> {

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

    fun nearestMonthdays(inputDataSet: ArrayList<ArwenDataStructure>) : ArrayList<ArwenDataStructure> {

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