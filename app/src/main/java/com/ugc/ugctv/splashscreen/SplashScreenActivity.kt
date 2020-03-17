package com.ugc.ugctv.splashscreen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
import com.ugc.ugctv.R
import com.ugc.ugctv.core.AbstractActivity
import com.ugc.ugctv.core.PreferenceManager
import com.ugc.ugctv.core.RequestCallBack
import com.ugc.ugctv.core.WebsocketManager
import com.ugc.ugctv.model.Config
import com.ugc.ugctv.model.UgcError
import com.ugc.ugctv.services.TechnicalService
import com.ugc.ugctv.settings.SettingsActivity
import com.ugc.ugctv.tv.TvActivity
import com.ugc.ugctv.websocket.model.EventType
import io.socket.emitter.Emitter
import kotlinx.android.synthetic.main.splashscreen_activity.*

class SplashScreenActivity : AbstractActivity() {

    companion object {

        fun newIntent(context: Context): Intent {
            return Intent(context, SplashScreenActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        setContentView(R.layout.splashscreen_activity)
        setTranslucideStatusBar()

        retry_button.setOnClickListener { getConfig() }

        settings.setOnClickListener { startActivity(SettingsActivity.newIntent(baseContext))}
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        when(keyCode) {
            KeyEvent.KEYCODE_DPAD_RIGHT -> settings.isSelected
        }

        return true
    }


    override fun onResume() {
        super.onResume()

        getConfig()
    }

    private fun getConfig() {

        showLoader()

        TechnicalService(baseContext).getConfig(
            object : RequestCallBack<Config> {

                override fun onSuccess(result: Config) {
                    showReadyState()
                }

                override fun onError(error: UgcError) {
                    showError(error.message)
                    showRetry()
                }
            }
        )
    }

    private fun showReadyState() {
        progress_wheel.hide()

        if(PreferenceManager(baseContext).hasRoom()){
            ready_indicator.startAnimation(
                AnimationUtils.loadAnimation(applicationContext, R.anim.fade_in))
            ready_indicator.visibility = View.VISIBLE

            WebsocketManager.instance.subscribeEvent(EventType.start.name, onStart)
        }
    }

    private fun showLoader() {
        progress_wheel.show()
        retry_button.visibility = View.GONE
    }

    private fun showRetry(){
        retry_button.visibility = View.VISIBLE
        progress_wheel.hide()
    }

    private val onStart = Emitter.Listener { args: Array<Any?> ->
        runOnUiThread {
            Log.d(SplashScreenActivity::class.java.simpleName, "Start event intercepted, with room : " + args[0] as String)
            if(PreferenceManager(baseContext).getRoom().name
                    .equals(args[0] as String)){
                startActivity(TvActivity.newIntent(baseContext))
                finishAffinity()
            }
        }
    }


}
