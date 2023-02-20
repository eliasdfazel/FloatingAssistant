/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 2/20/23, 6:07 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.floating.smart.panel

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import co.geeksempire.floating.smart.panel.databinding.EntryConfigurationsLayoutBinding

class EntryConfigurations : AppCompatActivity() {

    lateinit var entryConfigurationsLayoutBinding: EntryConfigurationsLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        entryConfigurationsLayoutBinding = EntryConfigurationsLayoutBinding.inflate(layoutInflater)
        setContentView(entryConfigurationsLayoutBinding.root)



    }

}