package com.ugc.ugctv.settings

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import com.ugc.ugctv.R
import com.ugc.ugctv.core.AbstractActivity
import com.ugc.ugctv.core.PreferenceManager
import com.ugc.ugctv.model.Room
import com.ugc.ugctv.settings.callback.SelectRoomCallback
import kotlinx.android.synthetic.main.settings_activity.*

class SettingsActivity : AbstractActivity() {

    companion object {

        public fun newIntent(context: Context): Intent {
            return Intent(context, SettingsActivity::class.java)
        }
    }
    lateinit var selectRoomDialogfragment : SelectRoomDialogfragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.settings_activity)

        if(PreferenceManager(baseContext).hasRoom()){
            room_settingview.setValue(getString(R.string.empty_room))
        }else{
            room_settingview.setValue(PreferenceManager(baseContext).getRoom().readableName)
        }

        room_settingview.setOnClickListener{
            selectRoomDialogfragment = SelectRoomDialogfragment()
                .setCallBack(object :SelectRoomCallback{
                    override fun onRoomSelected(room: Room) {
                        PreferenceManager(baseContext).setRoomName(room)
                        selectRoomDialogfragment.dismiss()
                        room_settingview.setValue(PreferenceManager(baseContext).getRoom().readableName)
                    }
                })

            selectRoomDialogfragment.show(supportFragmentManager, SUCCESS_DIALOG)
        }

        try {
            val version =
                packageManager.getPackageInfo(packageName, 0).versionName
            val versionText: String = getString(R.string.version_string).plus(version)
            app_version_settingview.setValue(versionText)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
    }
}