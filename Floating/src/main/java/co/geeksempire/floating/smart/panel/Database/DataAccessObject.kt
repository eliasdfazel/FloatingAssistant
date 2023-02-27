/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 2/27/23, 8:23 AM
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


    //@Query("SELECT * FROM user WHERE first_name LIKE :first AND last_name LIKE :last LIMIT 1")
    //fun findByName(first: String, last: String): User
    @Query("SELECT * FROM ARWEN WHERE Links IN (:PackageName)")
    suspend fun queryRelatedLinks(PackageName: String) : List<DataStructure>

    @Query("SELECT COUNT(Index) FROM ARWEN")
    suspend fun getRowCount() : Int
}