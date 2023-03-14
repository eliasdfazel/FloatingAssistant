/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 3/14/23, 8:31 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.floating.smart.panel.Dashboard.UI

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import co.geeksempire.floating.smart.panel.BuildConfig
import co.geeksempire.floating.smart.panel.Dashboard.Extensions.setupProfile
import co.geeksempire.floating.smart.panel.Dashboard.Extensions.setupUserInterface
import co.geeksempire.floating.smart.panel.Database.ArwenDataInterface
import co.geeksempire.floating.smart.panel.Database.ArwenDatabase
import co.geeksempire.floating.smart.panel.Preferences.UI.ColorsIO
import co.geeksempire.floating.smart.panel.R
import co.geeksempire.floating.smart.panel.Tests.PrototypeData
import co.geeksempire.floating.smart.panel.Utils.Animations.AnimationStatus
import co.geeksempire.floating.smart.panel.Utils.Animations.multipleColorsRotation
import co.geeksempire.floating.smart.panel.Utils.Colors.Palettes
import co.geeksempire.floating.smart.panel.Utils.Notifications.NotificationsCreator
import co.geeksempire.floating.smart.panel.Utils.Settings.SystemSettings
import co.geeksempire.floating.smart.panel.databinding.DashboardLayoutBinding
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.identity.SignInCredential
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Dashboard : AppCompatActivity(), AnimationStatus {

    var firebaseUser = Firebase.auth.currentUser

    val systemSettings: SystemSettings by lazy {
        SystemSettings(applicationContext)
    }

    val colorsIO: ColorsIO by lazy {
        ColorsIO(applicationContext)
    }

    val notificationsCreator = NotificationsCreator()

    val palettes: Palettes by lazy {
        Palettes(applicationContext)
    }

    private val googleSignInClient: SignInClient by lazy {
        Identity.getSignInClient(this@Dashboard)
    }

    private object RequestId {
        const val Permissions = 0
    }

    lateinit var dashboardLayoutBinding: DashboardLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dashboardLayoutBinding = DashboardLayoutBinding.inflate(layoutInflater)
        setContentView(dashboardLayoutBinding.root)

        window.decorView.setBackgroundColor(getColor(R.color.black))

        val allPermissions: ArrayList<String> = ArrayList<String>()
        allPermissions.add(Manifest.permission.INTERNET)
        allPermissions.add(Manifest.permission.VIBRATE)
        allPermissions.add(Manifest.permission.SYSTEM_ALERT_WINDOW)
        allPermissions.add(Manifest.permission.PACKAGE_USAGE_STATS)
        allPermissions.add(Manifest.permission.RECEIVE_BOOT_COMPLETED)
        allPermissions.add(Manifest.permission.WAKE_LOCK)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            allPermissions.add(Manifest.permission.USE_BIOMETRIC)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            allPermissions.add(Manifest.permission.POST_NOTIFICATIONS)
        }

        requestPermissions(allPermissions.toTypedArray(), Dashboard.RequestId.Permissions)

    }

    override fun onStart() {
        super.onStart()

        dashboardLayoutBinding.profileImage.setOnClickListener {

            if (firebaseUser != null) {

                // Go To Profile

            } else {

                googleAccountAuthentication()

            }

        }

        if (BuildConfig.DEBUG) {

            dashboardLayoutBinding.prototypeGenerator.visibility = View.VISIBLE

            dashboardLayoutBinding.prototypeGenerator.setOnClickListener {

                PrototypeData(applicationContext)
                    .generate()

            }

            dashboardLayoutBinding.prototypeGenerator.setOnLongClickListener {

                val arwenDataInterface = Room.databaseBuilder(applicationContext, ArwenDataInterface::class.java, ArwenDatabase.DatabaseName)

                val arwenDatabaseAccess = arwenDataInterface.build().initializeDataAccessObject()

                CoroutineScope(Dispatchers.IO).launch {

                    val allLinks = arwenDatabaseAccess.allLinks()

                    println(allLinks)

                }

                true
            }
        }

    }

    override fun onResume() {
        super.onResume()

        setupUserInterface()

    }

    override fun animationFinished() {



    }

    private fun googleAccountAuthentication() {

        dashboardLayoutBinding.waitingAnimation.visibility = View.VISIBLE

        multipleColorsRotation(dashboardLayoutBinding.waitingAnimation, arrayOf(
            getColor(R.color.red),
            getColor(R.color.white_transparent),
            getColor(R.color.blue),
        ), animationDuration = 1357, animationStatus = object : AnimationStatus {})

        val beginSignInRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(getString(R.string.webClientId))
                    .setFilterByAuthorizedAccounts(false)
                    .build()
            )
            .setAutoSelectEnabled(true)
            .build()

        googleSignInClient.beginSignIn(beginSignInRequest)
            .addOnSuccessListener(this@Dashboard) { result ->

                try {

                    googleSignInResult.launch(IntentSenderRequest.Builder(result.pendingIntent.intentSender).build())

                } catch (e: Exception) {
                    e.printStackTrace()

                }

            }.addOnFailureListener(this@Dashboard) { e ->
                e.printStackTrace()
            }

    }

    private val googleSignInResult = registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) {

        it.data?.let { intentResult ->

            val signInCredential: SignInCredential = googleSignInClient.getSignInCredentialFromIntent(intentResult)

            val authenticationCredential: AuthCredential = GoogleAuthProvider.getCredential(signInCredential.googleIdToken, null)

            Firebase.auth.signInWithCredential(authenticationCredential)
                .addOnSuccessListener { authenticationResult ->

                    authenticationResult.user?.let { firebaseUser ->

                        this@Dashboard.firebaseUser = firebaseUser

                        setupProfile()

                    }

                }.addOnFailureListener { exception ->
                    exception.printStackTrace()

                }



        }

    }

}