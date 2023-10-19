package com.example.supplementsonlineshopproject

import android.app.Application
import com.example.supplementsonlineshopproject.di.myModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class MyApp:Application() {
    override fun onCreate() {
        super.onCreate()

        // Initialize Koin
        startKoin {
            androidContext(this@MyApp)
            modules(myModules) // Define your Koin modules here
        }

    }
}