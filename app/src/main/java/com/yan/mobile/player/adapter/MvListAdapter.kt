package com.yan.mobile.player.adapter

import android.content.Context
import com.itheima.player.model.bean.VideosBean
import com.yan.mobile.player.base.BaseListAdapter
import com.yan.mobile.player.widget.MvItemView

/**
 *  @author      : 楠GG
 *  @date        : 2017/11/14 18:00
 *  @description : mv列表adapter
 */
class MvListAdapter: BaseListAdapter<VideosBean, MvItemView>() {
    override fun refreshItemView(itemView: MvItemView, itemBean: VideosBean) {
        itemView.setData(itemBean)
    }

    override fun getItemView(context: Context?): MvItemView {
        return MvItemView(context)
    }
}