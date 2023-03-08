/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 3/8/23, 7:15 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.floating.smart.panel.Floating.Data

import android.graphics.drawable.Drawable

interface QueriesInterface {
    /**
     * Insert / Update Link
     **/
    fun insertDatabaseQueries(linkElementOne: FloatingDataStructure, linkElementTwo: FloatingDataStructure)

    /**
     * Retrieve Link Data
     **/
    fun notifyDataSetUpdate(priorElement: FloatingDataStructure)
}

data class FloatingDataStructure (val applicationPackageName: String, val applicationClassName: String?,
                                  val applicationName: String, val applicationIcon: Drawable)