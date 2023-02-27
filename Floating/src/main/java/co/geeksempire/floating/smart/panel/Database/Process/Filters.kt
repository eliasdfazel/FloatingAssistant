/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 2/27/23, 9:52 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.floating.smart.panel.Database.Process

import co.geeksempire.floating.smart.panel.Database.ArwenDataStructure
import java.util.*
import kotlin.math.abs

class Filters {

    fun identifyNearestTime(input: ArrayList<ArwenDataStructure>) : List<ArwenDataStructure> {

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

        return nearElements
    }

}