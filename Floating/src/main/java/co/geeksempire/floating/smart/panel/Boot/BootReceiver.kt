/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 3/6/23, 11:34 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.floating.smart.panel.Boot

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class BootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        if (context != null) {
            Log.d(this@BootReceiver.javaClass.simpleName.toString(), "Boot Completed")

            context.startActivity(Intent(context, OpenOnBoot::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            })

        }

    }

}