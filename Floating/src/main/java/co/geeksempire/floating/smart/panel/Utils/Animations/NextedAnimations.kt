/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 3/10/23, 7:23 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.floating.smart.panel.Utils.Animations

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.animation.*
import android.view.animation.Animation.AnimationListener
import androidx.appcompat.widget.AppCompatImageView
import co.geeksempire.floating.smart.panel.Utils.Display.displayX
import co.geeksempire.floating.smart.panel.Utils.Display.dpToInteger
import co.geeksempire.floating.smart.panel.databinding.FloatingLayoutBinding

interface AnimationStatus {
    fun animationFinished() {
        Log.d(this@AnimationStatus.javaClass.simpleName, "Animation Finished")
    }
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

    val rotateAnimationY = view.animate().apply {
        rotationY(toY)
        duration = initialDuration
        startDelay = repeatDelay
        interpolator = AccelerateDecelerateInterpolator()
    }
    rotateAnimationY.setUpdateListener {



    }
    rotateAnimationY.start()

    Handler(Looper.getMainLooper()).postDelayed({

        animationStatus.animationFinished()

    }, initialDuration)

}

fun moveFloatingTo(context: Context, windowManager: WindowManager,
                        floatingLayoutBinding: FloatingLayoutBinding, layoutParameters: WindowManager.LayoutParams, toPosition: Int) {


    val standByPosition = ValueAnimator.ofInt(layoutParameters.x, toPosition)
    standByPosition.duration = 1337
    standByPosition.interpolator = AccelerateDecelerateInterpolator()
    standByPosition.addUpdateListener {

        layoutParameters.x = it.animatedValue as Int

        try {

            windowManager.updateViewLayout(floatingLayoutBinding.root, layoutParameters)

        } catch (e: Exception) {
            e.printStackTrace()

            standByPosition.cancel()

        }

    }
    standByPosition.start()

}
fun moveFloatingToRight(context: Context, windowManager: WindowManager,
                        floatingLayoutBinding: FloatingLayoutBinding, layoutParameters: WindowManager.LayoutParams) {

    val safeAreaX = displayX(context) - dpToInteger(context, 19)

    val standByPosition = ValueAnimator.ofInt(layoutParameters.x, safeAreaX)
    standByPosition.duration = 1337
    standByPosition.interpolator = AccelerateDecelerateInterpolator()
    standByPosition.addUpdateListener {

        layoutParameters.x = it.animatedValue as Int

        try {

            windowManager.updateViewLayout(floatingLayoutBinding.root, layoutParameters)

        } catch (e: Exception) {
            e.printStackTrace()

            standByPosition.cancel()

        }

    }
    standByPosition.start()

}

fun moveFloatingToLeft(context: Context, windowManager: WindowManager,
                       floatingLayoutBinding: FloatingLayoutBinding, layoutParameters: WindowManager.LayoutParams) {


    val safeAreaX = dpToInteger(context, 19)

    val standByPosition = ValueAnimator.ofInt(layoutParameters.x, safeAreaX)
    standByPosition.duration = 1337
    standByPosition.interpolator = AccelerateDecelerateInterpolator()
    standByPosition.addUpdateListener {

        layoutParameters.x = it.animatedValue as Int

        try {

            windowManager.updateViewLayout(floatingLayoutBinding.root, layoutParameters)

        } catch (e: Exception) {
            e.printStackTrace()

            standByPosition.cancel()

        }

    }
    standByPosition.start()

}

fun multipleColorsRotation(instanceOfView: AppCompatImageView, allColors: Array<Int>) {

    instanceOfView.setImageDrawable(GradientDrawable(GradientDrawable.Orientation.TR_BL, allColors.toIntArray()))

    val rotateAnimation = RotateAnimation(0f, 360f,
        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f).apply {
        duration = 3333
        repeatCount = Animation.INFINITE
        interpolator = OvershootInterpolator()
        repeatMode = Animation.REVERSE
    }

    instanceOfView.startAnimation(rotateAnimation)

}