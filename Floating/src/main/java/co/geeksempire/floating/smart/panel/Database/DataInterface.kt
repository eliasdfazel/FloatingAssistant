/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 2/27/23, 9:52 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.floating.smart.panel.Database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ArwenDataStructure::class], version = 1000, exportSchema = false)
abstract class DataInterface : RoomDatabase() {
    abstract fun initializeDataAccessObject(): DataAccessObject

}