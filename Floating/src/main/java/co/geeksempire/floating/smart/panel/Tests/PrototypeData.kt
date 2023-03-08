/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 3/8/23, 7:19 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.floating.smart.panel.Tests

import android.content.Context
import android.util.Log
import androidx.room.Room
import co.geeksempire.floating.smart.panel.Database.ArwenDataInterface
import co.geeksempire.floating.smart.panel.Database.ArwenDataStructure
import co.geeksempire.floating.smart.panel.Database.Database
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PrototypeData (private val context: Context) {

    fun generate() = CoroutineScope(Dispatchers.IO).launch {

        val arwenDataInterface = Room.databaseBuilder(context, ArwenDataInterface::class.java, Database.DatabaseName)

        val arwenDatabaseAccess = arwenDataInterface.build().initializeDataAccessObject()

        for (i in 0..2000) {

            delay(13)

            val arwenDataStructure = ArwenDataStructure(
                Id = i,

                Links = "co.geeksempire.floating.smart.panel-co.geeksempire.premium.storefront.android",
                PackageOne = "co.geeksempire.floating.smart.panel",
                PackageTwo = "co.geeksempire.premium.storefront.android",
                Counter = 1,

                TimeDay = 1337,
                TimeWeek = 3,
                TimeMonth = 7
            )

            arwenDatabaseAccess.insert(arwenDataStructure)

            Log.d(this@PrototypeData.javaClass.simpleName, "Index: $i")
        }

        delay(3579)

        Log.d(this@PrototypeData.javaClass.simpleName, "Database Finished")

        val databaseIndexes = arwenDatabaseAccess.rowCount()
        Log.d(this@PrototypeData.javaClass.simpleName, "Row Count; $databaseIndexes")

    }

}