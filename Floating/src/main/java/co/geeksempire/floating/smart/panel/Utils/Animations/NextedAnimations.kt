/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 3/1/23, 10:51 AM
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
                   duration: Long = 999,
                   repeatDelay: Long = 777,
                   repeatCounter: Int = Animation.INFINITE,
                   repeatMode: Int = Animation.REVERSE) {

    val alphaAnimation = AlphaAnimation(0.1f, 1f)
    alphaAnimation.duration = duration
    alphaAnimation.repeatCount = repeatCounter
    alphaAnimation.repeatMode = repeatMode
    alphaAnimation.startOffset = repeatDelay
    alphaAnimation.interpolator = AccelerateDecelerateInterpolator()
    view.startAnimation(alphaAnimation)
    alphaAnimation.start()

}