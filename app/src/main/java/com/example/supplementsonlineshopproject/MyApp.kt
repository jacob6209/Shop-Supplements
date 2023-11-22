package com.example.supplementsonlineshopproject

import android.app.Application
import com.example.supplementsonlineshopproject.di.myModules
import com.google.firebase.analytics.FirebaseAnalytics
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class MyApp:Application() {
    private lateinit var analytics: FirebaseAnalytics
    override fun onCreate() {
        super.onCreate()

        // Obtain the FirebaseAnalytics instance.
         analytics = FirebaseAnalytics.getInstance(this)

        // Initialize Koin
        startKoin {
            androidContext(this@MyApp)
            modules(myModules) // Define your Koin modules here
        }



    }
}