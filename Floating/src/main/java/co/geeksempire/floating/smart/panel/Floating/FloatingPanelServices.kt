/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 3/8/23, 7:45 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.floating.smart.panel.Floating

import android.annotation.SuppressLint
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import co.geeksempire.floating.smart.panel.Database.ArwenDataAccessObject
import co.geeksempire.floating.smart.panel.Database.ArwenDataInterface
import co.geeksempire.floating.smart.panel.Database.ArwenDataStructure
import co.geeksempire.floating.smart.panel.Database.Database
import co.geeksempire.floating.smart.panel.Database.Process.InitialDataSet
import co.geeksempire.floating.smart.panel.Floating.Adapter.FloatingAdapter
import co.geeksempire.floating.smart.panel.Floating.Data.FloatingDataStructure
import co.geeksempire.floating.smart.panel.Floating.Data.QueriesInterface
import co.geeksempire.floating.smart.panel.Floating.Extensions.registerFloatingBroadcasts
import co.geeksempire.floating.smart.panel.Floating.Extensions.setupUserInterface
import co.geeksempire.floating.smart.panel.Preferences.Floating.FloatingIO
import co.geeksempire.floating.smart.panel.Preferences.UI.ColorsIO
import co.geeksempire.floating.smart.panel.Utils.Animations.*
import co.geeksempire.floating.smart.panel.Utils.Display.displayX
import co.geeksempire.floating.smart.panel.Utils.Display.dpToInteger
import co.geeksempire.floating.smart.panel.Utils.Notifications.NotificationsCreator
import co.geeksempire.floating.smart.panel.databinding.FloatingLayoutBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.*

class FloatingPanelServices : Service(), QueriesInterface {

    companion object {
        var Floating = false
    }

    private val notificationsCreator = NotificationsCreator()

    val windowManager: WindowManager by lazy {
        getSystemService(Context.WINDOW_SERVICE) as WindowManager
    }

    private val layoutInflater: LayoutInflater by lazy {
        getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    val floatingLayoutBinding: FloatingLayoutBinding by lazy {
        FloatingLayoutBinding.inflate(layoutInflater)
    }

    private val floatingAdapter: FloatingAdapter by lazy {
        FloatingAdapter(applicationContext, layoutInflater, floatingLayoutBinding.floatingShield, this@FloatingPanelServices)
    }

    private var layoutParameters = WindowManager.LayoutParams()

    val arwenDatabaseAccess: ArwenDataAccessObject by lazy {
        Room.databaseBuilder(applicationContext, ArwenDataInterface::class.java, Database.DatabaseName).build().initializeDataAccessObject()
    }

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

    var sideLeftRight = FloatingIO.FloatingSide.RightSide

    var inStandBy = false

    var moveIt = false

    lateinit var delayRunnable: Runnable

    var delayHandler: Handler = Handler(Looper.getMainLooper())

    var broadcastReceiver: BroadcastReceiver? = null

    override fun onBind(intent: Intent?) : IBinder? { return null }

    @SuppressLint("ClickableViewAccessibility")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int) : Int {
        super.onStartCommand(intent, flags, startId)

        CoroutineScope(Dispatchers.Main).launch {

            sideLeftRight = floatingIO.floatingSide()

            when (sideLeftRight) {
                FloatingIO.FloatingSide.LeftSide -> {



                }
                FloatingIO.FloatingSide.RightSide -> {

                    floatingLayoutBinding.floatingHandheldGlow.rotationY = 180f
                    floatingLayoutBinding.floatingHandheld.rotationY = 180f

                }
            }

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

                floatingLayoutBinding.floatingHandheld.isEnabled = false

                when (sideLeftRight) {
                    FloatingIO.FloatingSide.LeftSide -> {



                    }
                    FloatingIO.FloatingSide.RightSide -> {

                        floatingLayoutBinding.floatingHandheldGlow.let {

                            it.visibility = View.VISIBLE

                            if (it.rotationY == 0f) {

                                rotateAnimationY(it, animationStatus =  object : AnimationStatus {

                                    override fun animationFinished() {

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

                                inStandBy = true

                                rotateAnimationY(it, animationStatus =  object : AnimationStatus {

                                    override fun animationFinished() {

                                        floatingLayoutBinding.floatingHandheld.isEnabled = true

                                        alphaAnimation(view = it, repeatCounter = 3, animationStatus =  object : AnimationStatus {

                                            override fun animationFinished() {


                                            }

                                        })

                                    }

                                })

                                moveFloatingTo(applicationContext, windowManager, floatingLayoutBinding, layoutParameters, floatingIO.positionX())

                                floatingPanelStandBy(windowManager, floatingLayoutBinding, standByDelay = 13579)

                            } else {

                                inStandBy = false

                                rotateAnimationY(view = it, toY = 0f, animationStatus =  object : AnimationStatus {

                                    override fun animationFinished() {

                                        floatingLayoutBinding.floatingHandheld.isEnabled = true

                                    }

                                })

                                moveFloatingToRight(applicationContext, windowManager, floatingLayoutBinding, layoutParameters)

                            }

                        }

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

                            delayRunnable = Runnable {
                                Log.d(this@FloatingPanelServices.javaClass.simpleName, "Action Down: Moving Permission Granted")

                                moveIt = true

                            }

                            delayHandler.postDelayed(delayRunnable, 777)

                        }
                        MotionEvent.ACTION_UP -> {

                            delayHandler.removeCallbacks(delayRunnable)

                            if (moveIt) {

                                val moveX = initialX + ((motionEvent.rawX - initialTouchX)).toInt()
                                val moveY = initialY + ((motionEvent.rawY - initialTouchY)).toInt()

                                when (sideLeftRight) {
                                    FloatingIO.FloatingSide.LeftSide -> {



                                    }
                                    FloatingIO.FloatingSide.RightSide -> {

                                        if (moveX < safeAreaRight) {

                                            layoutParameters.x = moveX
                                            layoutParameters.y = moveY

                                            floatingIO.storePositionX(layoutParameters.x)
                                            floatingIO.storePositionY(layoutParameters.y)

                                            if (floatingLayoutBinding.floatingHandheld.rotationY == 0f) {

                                                rotateAnimationY(floatingLayoutBinding.floatingHandheldGlow, toY = 180f, animationStatus =  object : AnimationStatus {

                                                    override fun animationFinished() {

                                                    }

                                                })

                                                rotateAnimationY(floatingLayoutBinding.floatingHandheld, toY = 180f, animationStatus =  object : AnimationStatus {

                                                    override fun animationFinished() {

                                                    }

                                                })

                                            }

                                        } else {

                                            if (floatingLayoutBinding.floatingHandheld.rotationY == 180f) {

                                                rotateAnimationY(floatingLayoutBinding.floatingHandheldGlow, toY = 0f, animationStatus =  object : AnimationStatus {

                                                    override fun animationFinished() {

                                                    }

                                                })

                                                rotateAnimationY(floatingLayoutBinding.floatingHandheld, toY = 0f, animationStatus =  object : AnimationStatus {

                                                    override fun animationFinished() {

                                                    }

                                                })

                                            }

                                        }

                                    }
                                }

                                moveIt = false

                            }

                        }
                        MotionEvent.ACTION_MOVE -> {

                            if (moveIt) {
                                Log.d(this@FloatingPanelServices.javaClass.simpleName, "Action Move: Moving...")

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

                                            } catch (e: Exception) { e.printStackTrace() }

                                        }

                                    }
                                }

                            }

                        }
                    }

                    return false
                }

            })

            floatingLayoutBinding.floatingShield.setOnClickListener { }

            if (!FloatingPanelServices.Floating) {

                FloatingPanelServices.Floating = true

                floatingPanelStandBy(windowManager, floatingLayoutBinding)

                setupUserInterface(floatingLayoutBinding)

                registerFloatingBroadcasts(floatingLayoutBinding)

                prepareInitialData(floatingAdapter)

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

        windowManager.removeView(floatingLayoutBinding.root)

        broadcastReceiver?.let {
            unregisterReceiver(it)
        }

    }

    override fun insertDatabaseQueries(linkElementOne: FloatingDataStructure, linkElementTwo: FloatingDataStructure) {

        CoroutineScope(Dispatchers.IO).async {

            val calendar = Calendar.getInstance()

            val arwenLink = arwenDatabaseAccess.specificLink(linkElementOne.applicationPackageName, linkElementTwo.applicationPackageName)

            if (arwenLink != null) {


                arwenLink.Counter = arwenLink.Counter + 1

                arwenLink.TimeDay = "${calendar.get(Calendar.HOUR_OF_DAY)}${calendar.get(Calendar.MINUTE)}".toInt()
                arwenLink.TimeWeek = calendar.get(Calendar.DAY_OF_WEEK)
                arwenLink.TimeMonth = calendar.get(Calendar.DAY_OF_MONTH)

                arwenDatabaseAccess.update(arwenLink)

            } else {

                val databaseIndex = arwenDatabaseAccess.rowCount()

                arwenDatabaseAccess.insert(ArwenDataStructure(
                    Id = databaseIndex,

                    Links = "${linkElementOne.applicationPackageName}-${linkElementTwo.applicationPackageName}",

                    PackageOne = linkElementOne.applicationPackageName,
                    PackageTwo = linkElementTwo.applicationPackageName,

                    Counter = 1,

                    TimeDay = "${calendar.get(Calendar.HOUR_OF_DAY)}${calendar.get(Calendar.MINUTE)}".toInt(),
                    TimeWeek = calendar.get(Calendar.DAY_OF_WEEK),
                    TimeMonth = calendar.get(Calendar.DAY_OF_MONTH)
                ))

            }

        }

    }

    override fun notifyDataSetUpdate(priorElement: FloatingDataStructure) {

        CoroutineScope(Dispatchers.IO).async {

            arwenDatabaseAccess.queryRelatedLinks(priorElement.applicationPackageName)?.let {



            }

        }

    }

    private fun prepareInitialData(floatingAdapter: FloatingAdapter) {

        if (getDatabasePath(Database.DatabaseName).exists()) {

            // query all based on day of week
            // sort with counter
            //queryRelatedDayTime

        } else {

            val initialDataSet = InitialDataSet(applicationContext).generate()

            floatingAdapter.applicationsData.clear()
            floatingAdapter.applicationsData.addAll(initialDataSet)

            floatingAdapter.notifyDataSetChanged()

        }

    }

    private fun floatingPanelStandBy(windowManager: WindowManager, floatingLayoutBinding: FloatingLayoutBinding,
        standByDelay: Long = 7531) {

        if (!inStandBy) {

            Handler(Looper.getMainLooper()).postDelayed({

                if (!inStandBy) {

                    when (sideLeftRight) {
                        FloatingIO.FloatingSide.LeftSide -> {



                        }
                        FloatingIO.FloatingSide.RightSide -> {

                            rotateAnimationY(floatingLayoutBinding.floatingHandheldGlow, toY = 0f, animationStatus =  object : AnimationStatus {

                                override fun animationFinished() {



                                }

                            })

                            rotateAnimationY(floatingLayoutBinding.floatingHandheld, toY = 0f, animationStatus =  object : AnimationStatus {

                                override fun animationFinished() {



                                }

                            })

                            moveFloatingToRight(applicationContext, windowManager, floatingLayoutBinding, layoutParameters)

                        }
                    }

                    inStandBy = true

                }

            }, standByDelay)

        }

    }

}