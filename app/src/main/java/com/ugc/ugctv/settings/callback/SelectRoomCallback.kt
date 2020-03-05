package com.ugc.ugctv.settings.callback

import com.ugc.ugctv.model.Room

interface SelectRoomCallback {

    fun onRoomSelected(room: Room)
}