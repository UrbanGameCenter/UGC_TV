package com.ugc.ugctv.tv

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.animation.AnimationUtils
import com.github.nkzawa.emitter.Emitter
import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.ugc.ugctv.R
import com.ugc.ugctv.core.AbstractActivity
import com.ugc.ugctv.core.LOGGER_TAG
import com.ugc.ugctv.core.PreferenceManager
import com.ugc.ugctv.core.WEB_SERVICE_BASE_URL
import com.ugc.ugctv.model.MessageFrom
import com.ugc.ugctv.websocket.model.EventType
import kotlinx.android.synthetic.main.tv_activity.*
import org.json.JSONObject


class TvActivity : AbstractActivity() {

    companion object {

        public fun newIntent(context: Context): Intent {
            return Intent(context, TvActivity::class.java)
        }
    }

    private var hasSubsribeToSocketEvent: Boolean = false
    private var socket: Socket = IO.socket(WEB_SERVICE_BASE_URL)

    private val gson: Gson

    init {
        gson = GsonBuilder()
            .setLenient()
            .create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.tv_activity)
        setTranslucideStatusBar()

        if (!hasSubsribeToSocketEvent) {
            subscribeEvent();
        }

        initSocket()

        chronometer.start()
    }

    private fun subscribeEvent() {
        socket.on(PreferenceManager(baseContext).getRoom().name, onSupervisorMessage)
        socket.on(EventType.serverMessage.name, onServerMessage)
        hasSubsribeToSocketEvent = true
    }

    private fun initSocket() {
        Log.d(
            LOGGER_TAG,
            "init Socket.io connection ... "
        )
        //create connection
        socket.connect()

        if (socket.connected()) {
            Log.d(
                LOGGER_TAG,
                "Socket.io connection success !")
            socket.emit(
                EventType.join.name,
                PreferenceManager(baseContext).getRoom().name)
        } else {
            Log.e(
                LOGGER_TAG,
                "Socket.io connection failed, retry"
            )
            val handler = Handler()
            val r = Runnable { initSocket() }
            handler.postDelayed(r, 1000)
        }
    }


    private val onSupervisorMessage =
        Emitter.Listener { args: Array<Any> ->
            runOnUiThread {
                showMessage(Gson()
                    .fromJson(args[0] as String, MessageFrom::class.java)
                    .message
                )
            }
        }

    private fun showMessage(message: String) {

        MediaPlayer.create(this, R.raw.message_arrived_notification_sound).start();

        val aniFade =
            AnimationUtils.loadAnimation(applicationContext, R.anim.fade_in)

        text_message_tv.text = message

        text_message_tv.startAnimation(aniFade)
    }

    private val onServerMessage =
        Emitter.Listener { args: Array<Any?> ->
           //TODO
        }

    private fun getMessage(data: JSONObject): String {
        return data["message"] as String
    }
}