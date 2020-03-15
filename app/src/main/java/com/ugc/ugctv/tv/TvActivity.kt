package com.ugc.ugctv.tv

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.PowerManager
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.ugc.ugctv.R
import com.ugc.ugctv.core.AbstractActivity
import com.ugc.ugctv.core.PreferenceManager
import com.ugc.ugctv.core.WebsocketManager
import com.ugc.ugctv.model.MessageFrom
import com.ugc.ugctv.model.Room
import com.ugc.ugctv.splashscreen.SplashScreenActivity
import com.ugc.ugctv.websocket.model.EventType
import io.socket.emitter.Emitter
import kotlinx.android.synthetic.main.tv_activity.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


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

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        setupRessource()

        WebsocketManager.instance.subscribeEvent(PreferenceManager(baseContext).getRoom().name, onSupervisorMessage)
        WebsocketManager.instance.subscribeEvent(EventType.stop.name, onStop)

        chronometer.start()
    }

    private fun setupRessource() {
        when(PreferenceManager(baseContext).getRoom()) {
            Room.HOWARD_CARTER -> {
                rootview.background = getDrawable(R.drawable.bg_howard_carter)
                text_message_tv.setTextColor(resources.getColor(R.color.lightTextColor))
                chronometer.setTextColor(resources.getColor(R.color.lightTextColor))
            }
            Room.JIG_SAW -> {
                rootview.background = getDrawable(R.drawable.bg_saw)
                text_message_tv.setTextColor(resources.getColor(R.color.lightTextColor))
                chronometer.setTextColor(resources.getColor(R.color.lightTextColor))
            }
            Room.PRISON_BREAKOUT -> {
                text_message_tv.setTextColor(resources.getColor(R.color.lightTextColor))
                chronometer.setTextColor(resources.getColor(R.color.lightTextColor))
            }
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

        text_message_tv.text = message
        text_message_tv.visibility = View.VISIBLE

        text_message_tv.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.fade_in))

        GlobalScope.launch(context = Dispatchers.Main) {
            delay(60000)
            text_message_tv.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.fade_out))
            text_message_tv.visibility = View.GONE
        }
    }

}