package com.yan.mobile.player.adapter

import android.content.Context
import android.database.Cursor
import android.support.v4.widget.CursorAdapter
import android.view.View
import android.view.ViewGroup
import com.yan.mobile.player.model.AudioBean
import com.yan.mobile.player.widget.VBangItemView

/**
 *  @author      : 楠GG
 *  @date        : 2017/11/16 12:46
 *  @description : v榜页面适配器
 */
class VBangAdapter(context: Context?, c: Cursor?) : CursorAdapter(context, c) {
    override fun newView(context: Context?, cursor: Cursor?, parent: ViewGroup?): View {
        return VBangItemView(context)
    }

    override fun bindView(view: View?, context: Context?, cursor: Cursor?) {
        //获取view
        val itemView = view as VBangItemView
        //获取数据
        val itemBean = AudioBean.getAudioBean(cursor)
        //设置数据
        itemView.setData(itemBean)
    }
}