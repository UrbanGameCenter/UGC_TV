package com.ugc.ugctv.core

import android.content.Context
import android.content.SharedPreferences
import com.ugc.ugctv.model.Room
import java.util.*

class PreferenceManager(private val context: Context?) {


    internal enum class Keys(val key: String) {
        KEY_ROOM_NAME("KEY_ROOM_NAME");
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

    fun setRoomName(room: Room) {
        getDefaultSharedPreferences(context).edit()
            .putString(Keys.KEY_ROOM_NAME.key, room.name).apply()
    }

    fun hasRoom(): Boolean {
        return !getDefaultSharedPreferences(context)
            .getString(Keys.KEY_ROOM_NAME.key, "")!!.isEmpty()

    }

}