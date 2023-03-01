/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 3/1/23, 10:12 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.floating.smart.panel.Database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ArwenDataStructure::class], version = 1000, exportSchema = false)
abstract class ArwenDataInterface : RoomDatabase() {
    abstract fun initializeDataAccessObject() : ArwenDataAccessObject

}