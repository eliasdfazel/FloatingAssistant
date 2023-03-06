/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 3/6/23, 10:16 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.floating.smart.panel.Floating

import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.geeksempire.floating.smart.panel.Database.Database
import co.geeksempire.floating.smart.panel.Database.Process.InitialDataSet
import co.geeksempire.floating.smart.panel.Floating.Adapter.FloatingAdapter
import co.geeksempire.floating.smart.panel.Floating.Extensions.registerFloatingBroadcasts
import co.geeksempire.floating.smart.panel.Floating.Extensions.setupUserInterface
import co.geeksempire.floating.smart.panel.Preferences.Floating.FloatingIO
import co.geeksempire.floating.smart.panel.Preferences.UI.ColorsIO
import co.geeksempire.floating.smart.panel.Utils.Animations.AnimationStatus
import co.geeksempire.floating.smart.panel.Utils.Animations.alphaAnimation
import co.geeksempire.floating.smart.panel.Utils.Animations.rotateAnimationY
import co.geeksempire.floating.smart.panel.Utils.Display.displayX
import co.geeksempire.floating.smart.panel.Utils.Display.dpToInteger
import co.geeksempire.floating.smart.panel.Utils.Notifications.NotificationsCreator
import co.geeksempire.floating.smart.panel.databinding.FloatingLayoutBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FloatingPanelServices : Service() {

    companion object {
        var Floating = false
    }

    private val notificationsCreator = NotificationsCreator()

    private val layoutInflater: LayoutInflater by lazy {
        getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    private val floatingAdapter: FloatingAdapter by lazy {
        FloatingAdapter(applicationContext, layoutInflater)
    }

    private var layoutParameters = WindowManager.LayoutParams()

    val floatingIO: FloatingIO by lazy {
        FloatingIO(applicationContext)
    }

    val colorsIO: ColorsIO by lazy {
        ColorsIO(applicationContext)
    }

    val safeAreaRight: Int by lazy {
        displayX(applicationContext) - dpToInteger(applicationContext, 19)
    }

    val safeAreaLeft: Int by lazy {
        dpToInteger(applicationContext, 19)
    }

    val sideLeftRight = FloatingIO.FloatingSide.RightSide

    override fun onBind(intent: Intent?) : IBinder? { return null }

    @SuppressLint("ClickableViewAccessibility")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int) : Int {
        super.onStartCommand(intent, flags, startId)

        CoroutineScope(Dispatchers.Main).launch {

            val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager

            val floatingLayoutBinding = FloatingLayoutBinding.inflate(layoutInflater)

            val xPosition: Int = floatingIO.positionX()
            val yPosition: Int = floatingIO.positionY()
            Log.d(this@FloatingPanelServices.javaClass.simpleName, "X -> $xPosition : Y  -> $yPosition")

            layoutParameters = generateLayoutParameters(applicationContext, 73, 301, xPosition, yPosition)

            windowManager.addView(floatingLayoutBinding.root, layoutParameters)

            val linearLayoutManager = LinearLayoutManager(applicationContext, RecyclerView.HORIZONTAL, false)
            floatingLayoutBinding.floatingRecyclerView.layoutManager = linearLayoutManager

            floatingLayoutBinding.floatingRecyclerView.adapter = floatingAdapter

            floatingLayoutBinding.floatingHandheld.setOnClickListener {
                Log.d(this@FloatingPanelServices.javaClass.simpleName, "Floating Handheld Clicked")

                floatingLayoutBinding.floatingHandheldGlow.let {

                    it.visibility = View.VISIBLE

                    if (it.rotationY == 0f) {

                        rotateAnimationY(it, animationStatus =  object : AnimationStatus {

                            override fun animationFinished() {

                                alphaAnimation(view = it, repeatCounter = 3, animationStatus =  object : AnimationStatus {

                                    override fun animationFinished() {

                                    }

                                })

                            }

                        })

                    } else {

                        rotateAnimationY(view = it, toY = 0f, animationStatus =  object : AnimationStatus {

                            override fun animationFinished() {



                            }

                        })

                    }

                }

                floatingLayoutBinding.floatingHandheld.let {

                    if (it.rotationY == 0f) {

                        rotateAnimationY(it, animationStatus =  object : AnimationStatus {

                            override fun animationFinished() {

                                alphaAnimation(view = it, repeatCounter = 3, animationStatus =  object : AnimationStatus {

                                    override fun animationFinished() {

                                    }

                                })

                            }

                        })

                    } else {

                        rotateAnimationY(view = it, toY = 0f, animationStatus =  object : AnimationStatus {

                            override fun animationFinished() {

                            }

                        })

                    }

                }

            }

            floatingLayoutBinding.floatingHandheld.setOnTouchListener(object : View.OnTouchListener {

                var initialX: Int = 0
                var initialY: Int = 0

                var initialTouchX: Float = 0f
                var initialTouchY: Float = 0f

                override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {

                    when (motionEvent.action) {
                        MotionEvent.ACTION_DOWN -> {

                            initialX = layoutParameters.x
                            initialY = layoutParameters.y

                            initialTouchX = motionEvent.rawX
                            initialTouchY = motionEvent.rawY

                        }
                        MotionEvent.ACTION_UP -> {

                            val moveX = initialX + ((motionEvent.rawX - initialTouchX)).toInt()
                            val moveY = initialY + ((motionEvent.rawY - initialTouchY)).toInt()

                            when (sideLeftRight) {
                                FloatingIO.FloatingSide.LeftSide -> {

                                    if (moveX > safeAreaLeft) {

                                        layoutParameters.x = moveX
                                        layoutParameters.y = moveY

                                        floatingIO.storePositionX(layoutParameters.x)
                                        floatingIO.storePositionY(layoutParameters.y)

                                    }

                                }
                                FloatingIO.FloatingSide.RightSide -> {

                                    if (moveX < safeAreaRight) {

                                        layoutParameters.x = moveX
                                        layoutParameters.y = moveY

                                        floatingIO.storePositionX(layoutParameters.x)
                                        floatingIO.storePositionY(layoutParameters.y)

                                    }

                                }
                            }


                        }
                        MotionEvent.ACTION_MOVE -> {

                            val moveX = initialX + ((motionEvent.rawX - initialTouchX)).toInt()
                            val moveY = initialY + ((motionEvent.rawY - initialTouchY)).toInt()

                            when (sideLeftRight) {
                                FloatingIO.FloatingSide.LeftSide -> {



                                }
                                FloatingIO.FloatingSide.RightSide -> {

                                    if (moveX < safeAreaRight) {

                                        layoutParameters.x = moveX
                                        layoutParameters.y = moveY

                                        try {

                                            windowManager.updateViewLayout(floatingLayoutBinding.root, layoutParameters)

                                        } catch (e: WindowManager.InvalidDisplayException) { e.printStackTrace() }

                                    }

                                }
                            }

                        }
                    }

                    return false
                }
            })

            if (!FloatingPanelServices.Floating) {

                FloatingPanelServices.Floating = true

                setupUserInterface(floatingLayoutBinding)

                registerFloatingBroadcasts(floatingLayoutBinding)

                prepareInitialData()

            }

        }

        return START_STICKY
    }

    override fun onCreate() {
        super.onCreate()

        startForeground(333, notificationsCreator.bindNotification(applicationContext))

    }

    override fun onDestroy() {
        super.onDestroy()

        FloatingPanelServices.Floating = false

    }

    private fun prepareInitialData() {

        if (getDatabasePath(Database.DatabaseName).exists()) {



        } else {

            val initialDataSet = InitialDataSet(applicationContext).generate()

            floatingAdapter.applicationsData.clear()
            floatingAdapter.applicationsData.addAll(initialDataSet)

            floatingAdapter.notifyDataSetChanged()

        }

    }

}