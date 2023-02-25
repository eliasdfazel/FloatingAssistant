/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 2/25/23, 9:53 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.floating.smart.panel

import android.app.Application
import android.os.Bundle
import co.geeksempire.floating.smart.panel.Preferences.PreferencesIO
import com.google.firebase.FirebaseApp
import com.google.firebase.analytics.FirebaseAnalytics

class FloatingApplication : Application() {

    val firebaseAnalytics: FirebaseAnalytics by lazy {
        FirebaseAnalytics.getInstance(applicationContext)
    }

    val preferencesIO: PreferencesIO by lazy {
        PreferencesIO(context = applicationContext)
    }

    override fun onCreate() {
        super.onCreate()

        FirebaseApp.initializeApp(applicationContext)

        firebaseAnalytics.logEvent(this@FloatingApplication.javaClass.simpleName, Bundle().apply { putString(this@FloatingApplication.javaClass.simpleName, "Started") })

    }

}