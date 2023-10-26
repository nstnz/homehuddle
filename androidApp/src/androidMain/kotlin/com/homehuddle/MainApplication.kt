package com.homehuddle

import Android
import android.app.Application
import com.homehuddle.common.base.di.SharedDI
import createDriver

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Android.init(this)
        Thread.setDefaultUncaughtExceptionHandler(TopExceptionHandler())
        SharedDI.initializeWithParams(
            databaseDriver = createDriver()
        )
    }
}

private class TopExceptionHandler : Thread.UncaughtExceptionHandler {
    override fun uncaughtException(t: Thread, e: Throwable) {
        println(e.toString())
    }
}