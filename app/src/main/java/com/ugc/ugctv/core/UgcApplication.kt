package com.ugc.ugctv.core

import android.app.Application

class UgcApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        WebsocketManager.instance
    }


    override fun onTerminate() {
        super.onTerminate()
        WebsocketManager.instance.disconnect()
    }
}
