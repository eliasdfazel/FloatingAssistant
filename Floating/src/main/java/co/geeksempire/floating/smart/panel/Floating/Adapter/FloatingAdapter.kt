/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 3/13/23, 10:23 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.floating.smart.panel.Floating.Adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import co.geeksempire.floating.smart.panel.Database.ArwenDatabase
import co.geeksempire.floating.smart.panel.Database.Process.Filters
import co.geeksempire.floating.smart.panel.Floating.Data.FloatingDataStructure
import co.geeksempire.floating.smart.panel.Floating.Data.QueriesInterface
import co.geeksempire.floating.smart.panel.Launch.OpenApplicationsLaunchPad
import co.geeksempire.floating.smart.panel.Utils.Operations.ApplicationsData
import co.geeksempire.floating.smart.panel.databinding.FloatingItemBinding
import com.bumptech.glide.Glide

class FloatingAdapter (private val context: Context, private val layoutInflater: LayoutInflater, private val floatingShield: AppCompatImageView,
                       private val filters: Filters, private val applicationsData: ApplicationsData,
                       private val queriesInterface: QueriesInterface) : RecyclerView.Adapter<FloatingViewHolder>() {

    val floatingDataStructures = ArrayList<FloatingDataStructure>()


    override fun getItemCount() : Int {

        return floatingDataStructures.size
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) : FloatingViewHolder {

        return FloatingViewHolder(FloatingItemBinding.inflate(layoutInflater, viewGroup, false))
    }

    override fun onBindViewHolder(floatingViewHolder: FloatingViewHolder, position: Int) {
        Log.d(this@FloatingAdapter.javaClass.simpleName, "Application: ${floatingDataStructures[position].applicationName} | Package: ${floatingDataStructures[position].applicationPackageName}")

        Glide.with(context)
            .load(floatingDataStructures[position].applicationIcon)
            .into(floatingViewHolder.applicationIcon)

        floatingViewHolder.rootViewItem.setOnClickListener {
            Log.d(this@FloatingAdapter.javaClass.simpleName, "Application: ${floatingDataStructures[position].applicationPackageName}")

            val floatingDataStructure = FloatingDataStructure(
                applicationPackageName = floatingDataStructures[position].applicationPackageName,
                applicationClassName = floatingDataStructures[position].applicationClassName,
                applicationName = floatingDataStructures[position].applicationName,
                applicationIcon = floatingDataStructures[position].applicationIcon
            )

            if (filters.validateEntry(ArwenDatabase.clickedApplicationData, floatingDataStructure, applicationsData)) {

                ArwenDatabase.DatabaseHandled = true

                ArwenDatabase.clickedApplicationData.add(floatingDataStructure)

                context.startActivity(Intent(context, OpenApplicationsLaunchPad::class.java).apply {
                    putExtra("packageName", floatingDataStructures[position].applicationPackageName)
                    floatingDataStructures[position].applicationClassName?.let {
                        putExtra("className", floatingDataStructures[position].applicationClassName)
                    }
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                })

                floatingShield.visibility = View.VISIBLE

                queriesInterface.notifyDataSetUpdate(floatingDataStructures[position])

                if (ArwenDatabase.clickedApplicationData.size == 2) {
                    Log.d(this@FloatingAdapter.javaClass.simpleName, "Start Database Queries")

                    queriesInterface.insertDatabaseQueries(ArwenDatabase.clickedApplicationData[0], ArwenDatabase.clickedApplicationData[1])

                    ArwenDatabase.clickedApplicationData.removeFirst()

                }

            }

        }

    }

}