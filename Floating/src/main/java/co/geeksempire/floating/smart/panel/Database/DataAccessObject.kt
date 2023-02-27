/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 2/27/23, 8:26 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.floating.smart.panel.Database

import androidx.room.*

@Dao
interface DataAccessObject {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg arrayOfDataStructure: DataStructure)


    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(vararg arrayOfDataStructure: DataStructure)


    @Delete
    suspend fun delete(dataStructure: DataStructure)

    @Query("SELECT * FROM ARWEN ORDER BY Counter DESC")
    suspend fun allLinks() : List<DataStructure>

    @Query("SELECT * FROM ARWEN WHERE Links IN (:PackageName) ORDER BY Counter DESC")
    suspend fun queryRelatedLinks(PackageName: String) : List<DataStructure>

    @Query("SELECT COUNT(Index) FROM ARWEN")
    suspend fun rowCount() : Int
}