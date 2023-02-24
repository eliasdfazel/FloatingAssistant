/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 2/24/23, 12:03 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.floating.smart.panel.Floating.Adapter

import androidx.recyclerview.widget.RecyclerView
import co.geeksempire.floating.smart.panel.databinding.FloatingItemBinding

class FloatingViewHolder (rootItemView: FloatingItemBinding) : RecyclerView.ViewHolder(rootItemView.root) {
    val rootViewItem = rootItemView.rootViewItem

    val applicationIcon = rootItemView.applicationIcon
}