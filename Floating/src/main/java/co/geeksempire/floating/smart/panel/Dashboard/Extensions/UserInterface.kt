/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 2/24/23, 4:58 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.floating.smart.panel.Dashboard.Extensions

import android.content.res.ColorStateList
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import co.geeksempire.floating.smart.panel.Dashboard.UI.Dashboard
import co.geeksempire.floating.smart.panel.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

fun Dashboard.setupUserInterface() {

    fireaseUser?.let {

        dashboardLayoutBinding.indicatorText.text = getString(R.string.profile)

        Glide.with(applicationContext)
            .asDrawable()
            .load(it.photoUrl)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .listener(object : RequestListener<Drawable> {

                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {

                    return false
                }

                override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {

                    runOnUiThread {

                        val profileColor = palettes.extractDominantColor(drawable = resource)

                        dashboardLayoutBinding.profileImageGlow.imageTintList = ColorStateList.valueOf(profileColor)
                        dashboardLayoutBinding.profileImageBackground.setImageDrawable(ColorDrawable(profileColor))

                    }

                    return false
                }

            })
            .submit()

    }

}