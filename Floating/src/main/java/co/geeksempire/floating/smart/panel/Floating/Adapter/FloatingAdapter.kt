/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 3/8/23, 6:27 AM
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
import co.geeksempire.floating.smart.panel.Floating.Data.FloatingDataStructure
import co.geeksempire.floating.smart.panel.Floating.Data.QueriesInterface
import co.geeksempire.floating.smart.panel.Launch.OpenApplicationsLaunchPad
import co.geeksempire.floating.smart.panel.databinding.FloatingItemBinding
import com.bumptech.glide.Glide

class FloatingAdapter (private val context: Context, private val layoutInflater: LayoutInflater, private val floatingShield: AppCompatImageView,
                       private val queriesInterface: QueriesInterface) : RecyclerView.Adapter<FloatingViewHolder>() {

    val applicationsData = ArrayList<FloatingDataStructure>()

    private val clickedApplicationData = ArrayList<FloatingDataStructure>()

    override fun getItemCount() : Int {

        return applicationsData.size
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) : FloatingViewHolder {

        return FloatingViewHolder(FloatingItemBinding.inflate(layoutInflater, viewGroup, false))
    }

    override fun onBindViewHolder(floatingViewHolder: FloatingViewHolder, position: Int) {
        Log.d(this@FloatingAdapter.javaClass.simpleName, "Application: ${applicationsData[position].applicationName} | Package: ${applicationsData[position].applicationPackageName}")

        Glide.with(context)
            .load(applicationsData[position].applicationIcon)
            .into(floatingViewHolder.applicationIcon)

        floatingViewHolder.rootViewItem.setOnClickListener {
            Log.d(this@FloatingAdapter.javaClass.simpleName, applicationsData[position].applicationPackageName)

            clickedApplicationData.add(
                FloatingDataStructure(
                    applicationPackageName = applicationsData[position].applicationPackageName,
                    applicationClassName = applicationsData[position].applicationClassName,
                    applicationName = applicationsData[position].applicationName,
                    applicationIcon = applicationsData[position].applicationIcon
            ))

            context.startActivity(Intent(context, OpenApplicationsLaunchPad::class.java).apply {
                putExtra("packageName", applicationsData[position].applicationPackageName)
                applicationsData[position].applicationClassName?.let {
                    putExtra("className", applicationsData[position].applicationClassName)
                }
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            })

            floatingShield.visibility = View.VISIBLE

            queriesInterface.notifyDataSetUpdate(applicationsData[position])

            if (clickedApplicationData.size == 2) {
                Log.d(this@FloatingAdapter.javaClass.simpleName, "Start Database Queries")

                clickedApplicationData.clear()

                queriesInterface.startDatabaseQueries(clickedApplicationData[0], clickedApplicationData[1])

            }

        }

    }

}