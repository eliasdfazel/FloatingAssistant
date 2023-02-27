/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 2/27/23, 7:49 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.floating.smart.panel.Database

import androidx.room.*

@Dao
interface DataAccessObject {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewWidgetDataSuspend(vararg arrayOfWidgetDataModels: DataStructure)


    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateWidgetDataSuspend(vararg arrayOfWidgetDataModels: DataStructure)


    @Delete
    suspend fun deleteSuspend(widgetDataModel: DataStructure)


    @Query("SELECT * FROM WidgetData ORDER BY AppName ASC")
    suspend fun getAllWidgetDataSuspend() : List<DataStructure>


    @Query("SELECT * FROM WidgetData WHERE PackageName IN (:PackageName) AND ClassNameProvider IN (:ClassNameWidgetProvider)")
    suspend fun loadWidgetByClassNameProviderWidgetSuspend(PackageName: String, ClassNameWidgetProvider: String) : DataStructure


    @Query("UPDATE WidgetData SET WidgetId = :WidgetId WHERE PackageName = :PackageName AND ClassNameProvider == :ClassNameProvider")
    suspend fun updateWidgetIdByPackageNameClassNameSuspend(PackageName: String, ClassNameProvider: String, WidgetId: Int) : Int


    @Query("UPDATE WidgetData SET WidgetLabel = :WidgetLabel WHERE WidgetId = :WidgetId")
    suspend fun updateWidgetLabelByWidgetIdSuspend(WidgetId: Int, WidgetLabel: String) : Int


    @Query("UPDATE WidgetData SET Recovery = :AddedWidgetRecovery WHERE PackageName= :PackageName AND ClassNameProvider = :ClassNameWidgetProvider")
    suspend fun updateRecoveryByClassNameProviderWidgetSuspend(PackageName: String, ClassNameWidgetProvider: String, AddedWidgetRecovery: Boolean) : Int


    @Query("DELETE FROM WidgetData WHERE PackageName = :PackageName AND ClassNameProvider = :ClassNameWidgetProvider")
    suspend fun deleteByWidgetClassNameProviderWidgetSuspend(PackageName: String, ClassNameWidgetProvider: String)

    @Query("SELECT COUNT(WidgetNumber) FROM WidgetData")
    suspend fun getRowCountSuspend() : Int
}