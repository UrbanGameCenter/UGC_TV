package com.ugc.ugctv.settings

import androidx.leanback.preference.LeanbackSettingsFragment
import androidx.preference.Preference
import androidx.preference.PreferenceFragment
import androidx.preference.PreferenceScreen

class UgcSettingsFragment : LeanbackSettingsFragment() {

    override fun onPreferenceStartInitialScreen() {
        startPreferenceFragment(UgcPreferenceFragment());

    }

    override fun onPreferenceStartScreen(
        caller: PreferenceFragment?,
        pref: PreferenceScreen?
    ): Boolean {
        TODO("Not yet implemented")}

    override fun onPreferenceStartFragment(
        caller: PreferenceFragment?,
        pref: Preference?
    ): Boolean {
        TODO("Not yet implemented")
    }

}