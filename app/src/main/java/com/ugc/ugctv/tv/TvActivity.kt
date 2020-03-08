package com.ugc.ugctv.tv

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.animation.AnimationUtils

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.ugc.ugctv.R
import com.ugc.ugctv.core.*
import com.ugc.ugctv.model.MessageFrom
import com.ugc.ugctv.splashscreen.SplashScreenActivity
import com.ugc.ugctv.websocket.model.EventType
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import kotlinx.android.synthetic.main.tv_activity.*
import org.json.JSONObject


class TvActivity : AbstractActivity() {

    companion object {

        fun newIntent(context: Context): Intent {
            return Intent(context, TvActivity::class.java)
        }
    }

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

        WebsocketManager.instance.subscribeEvent(PreferenceManager(baseContext).getRoom().name, onSupervisorMessage)
        WebsocketManager.instance.subscribeEvent(EventType.stop.name, onStop)

        chronometer.start()
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

    private val onStop =
        Emitter.Listener { args: Array<Any> ->
            runOnUiThread {
                Log.d(SplashScreenActivity::class.java.simpleName, "Stop event intercepted, with room : " + args[0] as String)
                if(PreferenceManager(baseContext).getRoom().name
                        .equals(args[0] as String)){
                    startActivity(SplashScreenActivity.newIntent(baseContext))
                    finishAffinity()
                }
            }
        }

    private fun showMessage(message: String) {

        MediaPlayer.create(this, R.raw.message_arrived_notification_sound).start();

        val aniFade =
            AnimationUtils.loadAnimation(applicationContext, R.anim.fade_in)

        text_message_tv.text = message

        text_message_tv.startAnimation(aniFade)
    }

}