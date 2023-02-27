/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 2/27/23, 9:49 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.floating.smart.panel.Database.Process

import co.geeksempire.floating.smart.panel.Database.DataStructure
import java.util.*
import kotlin.math.abs

class Filters {

    fun identifyNearestTime(input: ArrayList<DataStructure>) : List<DataStructure> {

        val calendar = Calendar.getInstance()

        val currentTime = "${calendar.get(Calendar.HOUR_OF_DAY)}${calendar.get(Calendar.MINUTE)}".toInt()

        val inputMap = HashMap<DataStructure, Int>()

        input.forEach {

            inputMap[it] = abs(it.TimeDay - currentTime)

        }

        val nearElements = ArrayList<DataStructure>()

        val sortHashMap = inputMap.entries.sortedBy {

            it.value
        }

        sortHashMap.slice(IntRange(0, 5)).forEach {

            nearElements.add(it.key)

        }

        return nearElements
    }

}