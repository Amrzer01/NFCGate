package com.example.nfcgate

import android.app.Application
import com.example.nfcgate.core.di.AppContainer

class NFCGateApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
    }
}
