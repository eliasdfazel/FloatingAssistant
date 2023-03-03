/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 3/3/23, 7:03 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.floating.smart.panel.Floating

import android.content.Context
import android.graphics.PixelFormat
import android.util.TypedValue
import android.view.Gravity
import android.view.WindowManager
import co.geeksempire.floating.smart.panel.Utils.Display.dpToInteger

fun generateLayoutParameters(context: Context, height: Int, width: Int, xPosition: Int, yPosition: Int) : WindowManager.LayoutParams {

    val marginClear = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5f, context.resources.displayMetrics).toInt()

    val layoutParams: WindowManager.LayoutParams = WindowManager.LayoutParams(
        dpToInteger(context, width) + marginClear,
        dpToInteger(context, height) + marginClear,
        WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
        PixelFormat.TRANSLUCENT
    )
    layoutParams.gravity = Gravity.TOP or Gravity.START
    layoutParams.x = xPosition
    layoutParams.y = yPosition
    layoutParams.windowAnimations = android.R.style.Animation_Dialog

    return layoutParams
}