package com.yan.mobile.player.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.yan.mobile.player.model.AudioBean
import com.yan.mobile.player.widget.PopListItemView

/**
 *  @author      : 楠GG
 *  @date        : 2017/11/21 10:57
 *  @description : PopupWindow中ListView适配器
 */
class PopAdapter(var list: List<AudioBean>): BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var itemView: PopListItemView? = null
        if (convertView == null) {
            itemView = PopListItemView(parent?.context)
        } else {
            itemView = convertView as PopListItemView
        }
        itemView.setData(list[position])
        return itemView
    }

    override fun getItem(position: Int): Any {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return list.size
    }
}