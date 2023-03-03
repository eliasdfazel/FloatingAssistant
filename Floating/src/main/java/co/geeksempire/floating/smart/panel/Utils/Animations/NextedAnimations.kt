/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 3/3/23, 7:40 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.floating.smart.panel.Utils.Animations

import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AlphaAnimation
import android.view.animation.Animation

fun alphaAnimation(view: View,
                   initialDuration: Long = 999,
                   repeatDelay: Long = 777,
                   repeatCounter: Int = Animation.INFINITE,
                   initialRepeatMode: Int = Animation.REVERSE) {

    val alphaAnimation = AlphaAnimation(0.1f, 1f).apply {
        duration = initialDuration
        startOffset = repeatDelay
        repeatCount = repeatCounter
        repeatMode = initialRepeatMode
        interpolator = AccelerateDecelerateInterpolator()
    }
    view.startAnimation(alphaAnimation)
    alphaAnimation.start()

}