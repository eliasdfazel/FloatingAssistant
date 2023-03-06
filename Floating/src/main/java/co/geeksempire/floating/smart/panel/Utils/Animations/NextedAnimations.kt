/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 3/6/23, 10:17 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.floating.smart.panel.Utils.Animations

import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener

interface AnimationStatus {
    fun animationFinished()
}

fun alphaAnimation(view: View,
                   initialDuration: Long = 999,
                   repeatDelay: Long = 777,
                   repeatCounter: Int = Animation.INFINITE,
                   initialRepeatMode: Int = Animation.REVERSE,
                   animationStatus: AnimationStatus) {

    val alphaAnimation = AlphaAnimation(0.1f, 1.0f).apply {
        duration = initialDuration
        startOffset = repeatDelay
        repeatCount = repeatCounter
        repeatMode = initialRepeatMode
        interpolator = AccelerateDecelerateInterpolator()
    }
    view.startAnimation(alphaAnimation)
    alphaAnimation.start()

    alphaAnimation.setAnimationListener(object : AnimationListener {
        override fun onAnimationStart(animation: Animation?) {

        }

        override fun onAnimationEnd(animation: Animation?) {

            animationStatus.animationFinished()

        }

        override fun onAnimationRepeat(animation: Animation?) {}

    })

}

fun rotateAnimationY(view: View,
                     toY: Float = 180f,
                     initialDuration: Long = 999,
                     repeatDelay: Long = 73,
                     animationStatus: AnimationStatus) {

    view.animate()
        .rotationY(toY)
        .setDuration(initialDuration)
        .setStartDelay(repeatDelay)
        .setInterpolator(AccelerateDecelerateInterpolator())
        .setUpdateListener {

            if ((it.animatedValue as Float) == toY) {

                animationStatus.animationFinished()

            }

        }
        .start()

}