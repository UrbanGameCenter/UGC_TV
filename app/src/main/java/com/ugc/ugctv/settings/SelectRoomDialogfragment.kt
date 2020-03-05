package com.ugc.ugctv.settings

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ugc.ugctv.R
import com.ugc.ugctv.settings.callback.SelectRoomCallback
import kotlinx.android.synthetic.main.list_dialog.*

class SelectRoomDialogfragment : DialogFragment()  {

    lateinit var callback: SelectRoomCallback


    fun setCallBack(callBack: SelectRoomCallback): SelectRoomDialogfragment {
        this.callback = callBack
        return this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, state: Bundle?): View? {

        super.onCreateView(inflater, parent, state)
        val thisView: View =
            inflater.inflate(R.layout.list_dialog, parent, false)

        if (dialog != null && dialog!!.window != null) {
            dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog!!.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        }

        val roomAdapter = RoomAdapter(callback, activity!!.baseContext)

        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = RecyclerView.VERTICAL

        val simple_recyclerview : RecyclerView =  thisView.findViewById(R.id.simple_recyclerview)

        simple_recyclerview.setLayoutManager(linearLayoutManager)

        simple_recyclerview.addItemDecoration(
            DividerItemDecoration(
                simple_recyclerview.getContext(),
                DividerItemDecoration.VERTICAL
            )
        )

        simple_recyclerview.setAdapter(roomAdapter)

        return thisView
    }


}