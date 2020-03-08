package com.ugc.ugctv.core

import android.os.Handler
import android.util.Log

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.socket.client.IO
import io.socket.emitter.Emitter

class WebsocketManager private constructor() {

    private object HOLDER {
        val INSTANCE = WebsocketManager()
    }

    companion object {
        val instance: WebsocketManager by lazy { HOLDER.INSTANCE }
    }

    private val WEBSOCKET_LOGGER_TAG = "WebsocketManager Logger"
    private var socket = IO.socket(WEB_SERVICE_BASE_URL)
    private val gson: Gson


    init {
        Log.d(
            WEBSOCKET_LOGGER_TAG,
            "init Websocket Manager "
        )
        gson = GsonBuilder()
            .setLenient()
            .create()

        initSocket()
    }


    public fun subscribeEvent(event: String, listener: Emitter.Listener) {
        Log.d(WEBSOCKET_LOGGER_TAG, "Event " + event + " now listened")
        socket.on(event, listener)
    }

    private fun initSocket() {
        Log.d(WEBSOCKET_LOGGER_TAG, "init Socket.io connection ... ")

        //create connection
        socket.connect()

        if (socket.connected()) {
            Log.d(
                WEBSOCKET_LOGGER_TAG,
                "Socket.io connection success !"
            )
        } else {
            Log.e(
                WEBSOCKET_LOGGER_TAG,
                "Socket.io connection failed, retry"
            )
            val handler = Handler()
            val r = Runnable { initSocket() }
            handler.postDelayed(r, 1000)
        }
    }


    fun disconnect() {
        socket.disconnect()
        Log.d(WEBSOCKET_LOGGER_TAG, "Socket is now disconnected")
    }
}