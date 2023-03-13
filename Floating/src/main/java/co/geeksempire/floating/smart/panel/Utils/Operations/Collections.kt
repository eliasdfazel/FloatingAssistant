/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 3/13/23, 9:56 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.floating.smart.panel.Utils.Operations

import co.geeksempire.floating.smart.panel.Floating.Data.FloatingDataStructure

fun ArrayList<FloatingDataStructure>.isContains(floatingDataStructure: FloatingDataStructure) : Boolean {

    var isContain = false

    this@isContains.forEach {

        if (it.applicationPackageName == floatingDataStructure.applicationPackageName) {

            isContain = true

        }

    }

    return isContain
}