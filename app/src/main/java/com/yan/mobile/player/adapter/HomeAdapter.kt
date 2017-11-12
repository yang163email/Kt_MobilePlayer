package com.yan.mobile.player.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.yan.mobile.player.model.HomeItemBean
import com.yan.mobile.player.widget.HomeItemView
import com.yan.mobile.player.widget.LoadMoreView

/**
 *  @author      : 楠GG
 *  @date        : 2017/11/11 17:42
 *  @description ：HomeFragment中RecyclerView的适配器
 */
class HomeAdapter: RecyclerView.Adapter<HomeAdapter.HomeHolder>() {
    private var list = ArrayList<HomeItemBean>()

    fun updateList(list: List<HomeItemBean>?) {
        list?.let {
            this.list.clear()
            this.list.addAll(list)
            notifyDataSetChanged()
        }
    }

    fun loadMore(list: List<HomeItemBean>?) {
        list?.let {
            this.list.addAll(list)
            notifyDataSetChanged()
        }
    }

    override fun getItemViewType(position: Int): Int {
        //如果position为最后一个的话，则为加载更多类型
        return if (position == list.size) 1 else 0
    }
    override fun onBindViewHolder(holder: HomeHolder, position: Int) {
        //最后一条不刷新数据
        if (position == list.size) return
        val itemView = holder.itemView as HomeItemView
        itemView.setData(list[position])
    }

    override fun getItemCount(): Int {
        //多一个为加载更多的条目
        if (list.size == 0) return 0
        return list.size + 1
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): HomeHolder {
        return if (viewType == 1) {
            //加载更多布局
            HomeHolder(LoadMoreView(parent?.context))
        } else {
            //正产布局
            HomeHolder(HomeItemView(parent?.context))
        }
    }

    class HomeHolder(itemView: View): RecyclerView.ViewHolder(itemView)

}