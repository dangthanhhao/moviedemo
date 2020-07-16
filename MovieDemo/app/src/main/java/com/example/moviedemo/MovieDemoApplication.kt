package com.example.moviedemo

import android.app.Application
import timber.log.Timber

class MovieDemoApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant((Timber.DebugTree()))
    }
}