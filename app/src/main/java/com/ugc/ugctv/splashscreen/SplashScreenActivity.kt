package com.ugc.ugctv.splashscreen

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import com.ugc.ugctv.core.AbstractActivity
import com.ugc.ugctv.core.RequestCallBack
import com.ugc.ugctv.model.Config
import com.ugc.ugctv.model.UgcError
import com.ugc.ugctv.services.TechnicalService
import com.ugc.ugctv.R
import com.ugc.ugctv.core.PreferenceManager
import com.ugc.ugctv.model.Room
import com.ugc.ugctv.settings.SettingsActivity
import com.ugc.ugctv.tv.TvActivity
import kotlinx.android.synthetic.main.splashscreen_activity.*
import kotlinx.android.synthetic.main.tv_activity.*

class SplashScreenActivity : AbstractActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.splashscreen_activity)
        setTranslucideStatusBar()

        retry_button.setOnClickListener { getConfig() }

        settings.setOnClickListener { startActivity(SettingsActivity.newIntent(baseContext))}
    }

    override fun onResume() {
        super.onResume()

            getConfig()
    }

    private fun getConfig() {

        showLoader()

        TechnicalService(baseContext).getConfig(
            object : RequestCallBack<Config> {

                override fun onSuccess(response: Config) {
                    showStartButton()
                }

                override fun onError(error: UgcError) {
                    showError(error.message)
                    showRetry()
                }
            }
        )
    }

    private fun showStartButton() {
        progress_wheel.hide()

        if(!PreferenceManager(baseContext).getRoom().equals(Room.EMPTY)){
            start_button.startAnimation(
                AnimationUtils.loadAnimation(applicationContext, R.anim.fade_in))
            start_button.visibility = View.VISIBLE

            start_button.setOnClickListener {
                startActivity(TvActivity.newIntent(baseContext))
                finishAffinity()
            }
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

}
