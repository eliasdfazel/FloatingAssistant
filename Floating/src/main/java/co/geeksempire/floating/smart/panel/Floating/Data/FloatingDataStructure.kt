/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 3/8/23, 6:48 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.floating.smart.panel.Floating.Data

import android.graphics.drawable.Drawable

interface QueriesInterface {
    fun insertDatabaseQueries(linkElementOne: FloatingDataStructure, linkElementTwo: FloatingDataStructure)
    fun notifyDataSetUpdate(priorElement: FloatingDataStructure)
}

data class FloatingDataStructure (val applicationPackageName: String, val applicationClassName: String?,
                                  val applicationName: String, val applicationIcon: Drawable)