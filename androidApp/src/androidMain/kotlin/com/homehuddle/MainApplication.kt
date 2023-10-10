package com.homehuddle

import Android
import android.app.Application
import com.homehuddle.common.base.di.SharedDI

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Android.init(this)
        Thread.setDefaultUncaughtExceptionHandler(TopExceptionHandler())
        SharedDI.initializeWithParams()
    }
}

private class TopExceptionHandler : Thread.UncaughtExceptionHandler {
    override fun uncaughtException(t: Thread, e: Throwable) {
        println(e.toString())
    }
}