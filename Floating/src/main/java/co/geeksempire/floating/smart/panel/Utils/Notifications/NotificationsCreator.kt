/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 3/1/23, 10:12 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.floating.smart.panel.Utils.Notifications

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.PixelFormat
import android.media.MediaPlayer
import android.os.*
import android.util.TypedValue
import android.view.Gravity
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import co.geeksempire.floating.smart.panel.R
import co.geeksempire.floating.smart.panel.Utils.Display.dpToInteger


class NotificationsCreator {

    fun bindNotification(context: Context) : Notification {

        val notificationBuilder = NotificationCompat.Builder(context, context.packageName)

        notificationBuilder.setContentTitle(context.resources.getString(R.string.applicationName))
        notificationBuilder.setContentText(context.resources.getString(R.string.applicationDescription))
        notificationBuilder.setTicker(context.resources.getString(R.string.applicationName))
        notificationBuilder.setSmallIcon(R.drawable.notification_icon)
        notificationBuilder.setAutoCancel(false)
        notificationBuilder.color = context.getColor(R.color.primaryColorPurple)

        val notificationChannel = NotificationChannel(context.packageName, context.resources.getString(R.string.applicationName), NotificationManager.IMPORTANCE_LOW)
        notificationChannel.description = context.resources.getString(R.string.applicationName)

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(notificationChannel)

        return notificationBuilder.build()

    }

    fun generateLayoutParameters(context: Context, height: Int, width: Int, xPosition: Int, yPosition: Int) : WindowManager.LayoutParams {

        val marginClear = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5f, context.resources.displayMetrics).toInt()

        val layoutParams: WindowManager.LayoutParams = WindowManager.LayoutParams(
            dpToInteger(context, width) + marginClear,
            dpToInteger(context, height) + marginClear,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        )
        layoutParams.gravity = Gravity.TOP or Gravity.START
        layoutParams.x = xPosition
        layoutParams.y = yPosition
        layoutParams.windowAnimations = android.R.style.Animation_Dialog

        return layoutParams
    }

    fun playNotificationSound(activity: AppCompatActivity, soundId: Int) {

        val mediaPlayer: MediaPlayer = MediaPlayer.create(activity, soundId)
        mediaPlayer.setVolume(0.13f, 0.13f)
        mediaPlayer.setOnCompletionListener { aMediaPlayer ->

            aMediaPlayer.reset()
            aMediaPlayer.release()

        }
        mediaPlayer.start()

    }

    fun doVibrate(context: Context, millisecondVibrate: Long) {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {

            val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager

            vibratorManager.vibrate(CombinedVibration.createParallel(VibrationEffect.createOneShot(millisecondVibrate, VibrationEffect.DEFAULT_AMPLITUDE)))

        } else {

            val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

            vibrator.vibrate(VibrationEffect.createOneShot(millisecondVibrate, VibrationEffect.DEFAULT_AMPLITUDE))

        }

    }

}