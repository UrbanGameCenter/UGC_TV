package com.ugc.ugctv.common.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.ugc.ugctv.R
import kotlinx.android.synthetic.main.setting_item_view.view.*

class SettingItemView : LinearLayout {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    {
        LayoutInflater.from(context)
            .inflate(R.layout.setting_item_view, this, true)

        orientation = VERTICAL

        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it,
                R.styleable.settings_itemview_attributes, 0, 0)

            setting_title.text = resources.getText(typedArray
                .getResourceId(R.styleable
                    .settings_itemview_attributes_settings_itemview_title,0), "")

            setting_value.text = resources.getText(typedArray
                .getResourceId(R.styleable
                    .settings_itemview_attributes_settings_itemview_value,0),"")

            typedArray.recycle()
        }
    }

    public fun setValue(value : String){
        setting_value.text = value;
    }

    public fun setTitle(title : String){
        setting_title.text = title;
    }


}