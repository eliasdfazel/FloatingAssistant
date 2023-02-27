/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 2/27/23, 9:25 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.floating.smart.panel.Database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

object Database {
        const val DatabaseName = "ARWEN"
}

/**
 * @param Links: PackageName1-PackageName2
 * @param TimeDay: 24hrs -> 1319 (No Symbol)
 **/
@Entity(tableName = Database.DatabaseName)
data class DataStructure(
        @PrimaryKey var Id: Int,

        @ColumnInfo(name = "Links") var Links: String,
        @ColumnInfo(name = "Counter") var Counter: Int,

        @ColumnInfo(name = "TimeDay") var TimeDay: Int,
        @ColumnInfo(name = "TimeWeek") var TimeWeek: Int,
        @ColumnInfo(name = "TimeMonth") var TimeMonth: Int,
)