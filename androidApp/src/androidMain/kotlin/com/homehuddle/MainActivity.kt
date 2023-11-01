package com.homehuddle

import GoogleAuthApi
import MainView
import android.content.Intent
import android.os.Bundle
import com.homehuddle.common.base.di.SharedDI
import moe.tlaster.precompose.lifecycle.PreComposeActivity
import moe.tlaster.precompose.lifecycle.setContent
import org.kodein.di.instance

class MainActivity : PreComposeActivity() {

    private val googleAuthApi: GoogleAuthApi by SharedDI.di.instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        googleAuthApi.activity = this
        setContent {
            MainView()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        googleAuthApi.requestGoogleLogin(data)
    }
}