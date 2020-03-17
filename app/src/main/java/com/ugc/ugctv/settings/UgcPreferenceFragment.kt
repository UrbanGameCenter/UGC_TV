package com.ugc.ugctv.settings

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.leanback.preference.LeanbackPreferenceFragment
import androidx.preference.Preference
import com.ugc.ugctv.BuildConfig
import com.ugc.ugctv.R
import com.ugc.ugctv.core.PreferenceManager
import com.ugc.ugctv.model.Room


class UgcPreferenceFragment : LeanbackPreferenceFragment() {

    lateinit var preferenceManager : PreferenceManager


    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        preferenceManager = PreferenceManager(activity!!.getBaseContext())

    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        findPreference(PreferenceManager.Keys.KEY_ROOM_NAME.key)
            .setOnPreferenceChangeListener{ preference: Preference, newValue: Any ->

                Log.d(UgcPreferenceFragment::class.java.simpleName, "New value : " + newValue)
                preference.setSummary(Room.valueOf(newValue as String).readableName)
                true
            }


        if(preferenceManager.hasRoom()) {
                    findPreference(PreferenceManager.Keys.KEY_ROOM_NAME.key)
                        .setSummary(preferenceManager.getRoom().readableName)
        }

        findPreference(PreferenceManager.Keys.KEY_APP_VERSION.key).setSummary(BuildConfig.VERSION_NAME)

    }
}