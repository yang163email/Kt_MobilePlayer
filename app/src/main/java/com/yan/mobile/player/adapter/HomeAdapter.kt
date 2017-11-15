package com.yan.mobile.player.adapter

import android.content.Context
import com.yan.mobile.player.base.BaseListAdapter
import com.yan.mobile.player.model.HomeItemBean
import com.yan.mobile.player.widget.HomeItemView

/**
 *  @author      : 楠GG
 *  @date        : 2017/11/11 17:42
 *  @description ：HomeFragment中RecyclerView的适配器
 */
class HomeAdapter: BaseListAdapter<HomeItemBean, HomeItemView>() {
    override fun refreshItemView(itemView: HomeItemView, itemBean: HomeItemBean) {
        itemView.setData(itemBean)
    }

    override fun getItemView(context: Context?): HomeItemView {
        return HomeItemView(context)
    }
}