/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 2/27/23, 11:09 AM
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

        nearElements
    }

}