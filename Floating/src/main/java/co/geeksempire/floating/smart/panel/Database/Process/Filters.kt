/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 2/27/23, 9:55 AM
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

    fun identifyNearestTime(input: ArrayList<ArwenDataStructure>) : Deferred<List<ArwenDataStructure>> = CoroutineScope(Dispatchers.IO).async {

        val calendar = Calendar.getInstance()

        val currentTime = "${calendar.get(Calendar.HOUR_OF_DAY)}${calendar.get(Calendar.MINUTE)}".toInt()

        val inputMap = HashMap<ArwenDataStructure, Int>()

        input.forEach {

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