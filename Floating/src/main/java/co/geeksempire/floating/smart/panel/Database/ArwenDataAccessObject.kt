/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 3/8/23, 7:51 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.floating.smart.panel.Database

import androidx.room.*

@Dao
interface ArwenDataAccessObject {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg arrayOfArwenDataStructure: ArwenDataStructure)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(vararg arrayOfArwenDataStructure: ArwenDataStructure)

    @Delete
    suspend fun delete(arwenDataStructure: ArwenDataStructure)

    @Query("SELECT * FROM ARWEN ORDER BY Counter DESC")
    suspend fun allLinks() : List<ArwenDataStructure>

    @Query("SELECT * FROM ARWEN WHERE Links IN (:PackageName) ORDER BY Counter DESC")
    suspend fun queryRelatedLinks(PackageName: String) : List<ArwenDataStructure>?

    @Query("SELECT * FROM ARWEN WHERE TimeWeek IN (:dayOfWeek) ORDER BY Counter DESC")
    suspend fun queryRelatedDayTime(dayOfWeek: Int) : List<ArwenDataStructure>?

    @Query("SELECT * FROM ARWEN WHERE PackageOne IN (:PackageNameOne) AND PackageTwo IN (:PackageNameTwo)")
    suspend fun specificLink(PackageNameOne: String, PackageNameTwo: String) : ArwenDataStructure?

    @Query("SELECT COUNT(Id) FROM ARWEN")
    suspend fun rowCount() : Int

}