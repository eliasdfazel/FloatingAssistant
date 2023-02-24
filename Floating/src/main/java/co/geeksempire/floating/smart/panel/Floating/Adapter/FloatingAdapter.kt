/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 2/24/23, 12:01 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.floating.smart.panel.Floating.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.geeksempire.floating.smart.panel.Floating.Data.FloatingDataStructure
import co.geeksempire.floating.smart.panel.databinding.FloatingItemBinding

class FloatingAdapter (private val layoutInflater: LayoutInflater) : RecyclerView.Adapter<FloatingViewHolder>() {

    val applicationsData = ArrayList<FloatingDataStructure>()

    override fun getItemCount(): Int {

        return applicationsData.size
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): FloatingViewHolder {

        return FloatingViewHolder(FloatingItemBinding.inflate(layoutInflater, viewGroup, false))
    }

    override fun onBindViewHolder(floatingViewHolder: FloatingViewHolder, position: Int) {



    }

}