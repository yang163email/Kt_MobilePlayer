package com.yan.mobile.player.adapter

import android.content.Context
import com.itheima.player.model.bean.YueDanBean
import com.yan.mobile.player.base.BaseListAdapter
import com.yan.mobile.player.widget.YueDanItemView

/**
 *  @author      : 楠GG
 *  @date        : 2017/11/13 15:21
 *  @description ：悦单界面adapter
 */
class YueDanAdapter: BaseListAdapter<YueDanBean.PlayListsBean, YueDanItemView>() {
    override fun refreshItemView(itemView: YueDanItemView, itemBean: YueDanBean.PlayListsBean) {
        itemView.setData(itemBean)
    }

    override fun getItemView(context: Context?): YueDanItemView {
        return YueDanItemView(context)
    }

}