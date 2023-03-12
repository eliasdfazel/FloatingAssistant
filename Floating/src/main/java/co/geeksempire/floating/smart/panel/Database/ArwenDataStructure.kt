/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 3/12/23, 8:50 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.floating.smart.panel.Database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import co.geeksempire.floating.smart.panel.Floating.Data.FloatingDataStructure

object ArwenDatabase {
        const val DatabaseName = "ARWEN"

        var DatabaseHandled = false

        val clickedApplicationData = ArrayList<FloatingDataStructure>()
}

/**
 * @param Id: Index Of Row (Get Database Row Count Before Insert)
 * @param Links: PackageName1-PackageName2
 * @param TimeDay: 24hrs -> 1319 (No Symbol)
 **/
@Entity(tableName = ArwenDatabase.DatabaseName)
data class ArwenDataStructure(
        @PrimaryKey var Id: Int,

        @ColumnInfo(name = "Links") var Links: String,

        @ColumnInfo(name = "PackageOne") var PackageOne: String,
        @ColumnInfo(name = "PackageTwo") var PackageTwo: String,

        @ColumnInfo(name = "Counter") var Counter: Int,

        @ColumnInfo(name = "TimeDay") var TimeDay: Int,
        @ColumnInfo(name = "TimeWeek") var TimeWeek: Int,
        @ColumnInfo(name = "TimeMonth") var TimeMonth: Int,
)