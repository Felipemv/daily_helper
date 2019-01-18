package com.felipe.dailyhelper

import android.app.Application
import com.facebook.stetho.Stetho

class JApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
    }
}