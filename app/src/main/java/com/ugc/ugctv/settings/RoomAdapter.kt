package com.ugc.ugctv.settings

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ugc.ugctv.R
import com.ugc.ugctv.model.Room
import com.ugc.ugctv.settings.callback.SelectRoomCallback
import kotlinx.android.synthetic.main.simple_item_view.view.*

class RoomAdapter(val callback: SelectRoomCallback, val context : Context)
    : RecyclerView.Adapter<RoomAdapter.ViewHolder>() {

    private val roomList: Array<Room> = Room.values()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.simple_item_view, parent, false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val room: Room = roomList[position]

        holder.textview.text = room.readableName

        holder.itemView.setOnClickListener{
            callback.onRoomSelected(room)
        }
    }


    override fun getItemCount(): Int {
        return Room.values().size;
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textview = itemView.simple_textview

    }
}