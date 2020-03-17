package com.ugc.ugctv.core

import android.content.Context
import android.content.SharedPreferences
import com.ugc.ugctv.model.Room

class PreferenceManager(private val context: Context?) {


    internal enum class Keys(val key: String) {
        KEY_ROOM_NAME("KEY_ROOM_NAME"),
        KEY_APP_VERSION("KEY_APP_VERSION"),
        KEY_SESSION_DURATION("KEY_SESSION_DURATION"),
        KEY_MESSAGE_DURATION("KEY_MESSAGE_DURATION")
    }


    fun getDefaultSharedPreferences(context: Context?): SharedPreferences {
        return context!!.getSharedPreferences(
            context.packageName + "_preferences",
            Context.MODE_PRIVATE
        )
    }

    fun getRoom(): Room {
        return Room.valueOf(
            getDefaultSharedPreferences(context).getString(
                Keys.KEY_ROOM_NAME.key, null).toString()
        )
    }

    fun hasRoom(): Boolean {
        return !getDefaultSharedPreferences(context)
            .getString(Keys.KEY_ROOM_NAME.key, "")!!.isEmpty()

    }

    fun getSessionDuration(): Long {
        return getDefaultSharedPreferences(context).getLong(
                Keys.KEY_SESSION_DURATION.key, 3600000L)

    }

    fun getMessageDuration(): Long {
        return getDefaultSharedPreferences(context).getLong(
            Keys.KEY_MESSAGE_DURATION.key, 60000L)

    }

}