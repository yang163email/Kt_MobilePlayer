package com.yan.mobile.player.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.itheima.player.model.bean.YueDanBean
import com.yan.mobile.player.widget.LoadMoreView
import com.yan.mobile.player.widget.YueDanItemView

/**
 *  @author      : 楠GG
 *  @date        : 2017/11/13 15:21
 *  @description ：悦单界面adapter
 */
class YueDanAdapter: RecyclerView.Adapter<YueDanAdapter.YueDanHolder>() {
    private val list = ArrayList<YueDanBean.PlayListsBean>()

    fun updateList(list: List<YueDanBean.PlayListsBean>?) {
        list?.let {
            this.list.clear()
            this.list.addAll(list)
            notifyDataSetChanged()
        }
    }

    fun loadMore(list: List<YueDanBean.PlayListsBean>?) {
        list?.let {
            this.list.addAll(list)
            notifyDataSetChanged()
        }
    }

    override fun getItemViewType(position: Int): Int {
        //如果是最后一个，则为1
        return if (position == list.size) 1 else 0
    }

    override fun getItemCount(): Int {
        if (list.size == 0) return 0
        return list.size + 1
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): YueDanHolder {
        return if (viewType == 1) {
            YueDanHolder(LoadMoreView(parent?.context))
        } else {
            YueDanHolder(YueDanItemView(parent?.context))
        }
    }

    override fun onBindViewHolder(holder: YueDanHolder, position: Int) {
        if (position == list.size) return
        val data = list[position]
        val itemView = holder.itemView as YueDanItemView
        itemView.setData(data)
    }

    class YueDanHolder(itemView: View?): RecyclerView.ViewHolder(itemView)
}