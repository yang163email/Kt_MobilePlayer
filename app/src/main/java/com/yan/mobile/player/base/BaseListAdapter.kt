package com.yan.mobile.player.base

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.yan.mobile.player.widget.LoadMoreView

/**
 *  @author      : 楠GG
 *  @date        : 2017/11/14 14:26
 *  @description : 所有带有下拉刷新，上拉加载更多的adapter
 */
abstract class BaseListAdapter<IB, IV: View>: RecyclerView.Adapter<BaseListAdapter.BaseListHolder>() {
    private var list = ArrayList<IB>()

    fun updateList(list: List<IB>?) {
        list?.let {
            this.list.clear()
            this.list.addAll(list)
            notifyDataSetChanged()
        }
    }

    fun loadMore(list: List<IB>?) {
        list?.let {
            this.list.addAll(list)
            notifyDataSetChanged()
        }
    }

    override fun getItemViewType(position: Int): Int {
        //如果position为最后一个的话，则为加载更多类型
        return if (position == list.size) 1 else 0
    }
    override fun onBindViewHolder(holder: BaseListHolder, position: Int) {
        //最后一条不刷新数据
        if (position == list.size) return
        val itemView = holder.itemView as IV
        val data = list[position]
        refreshItemView(itemView, data)
        //设置条目点击事件
        itemView.setOnClickListener {
//            mListener.let {
//                it(data)
//            }
            //invoke代表调用自身
            mListener.invoke(data)
        }
    }

    lateinit var mListener: (itemBean: IB) -> Unit

    fun setListener(listener: (itemBean: IB) -> Unit) {
        mListener = listener
    }

    override fun getItemCount(): Int {
        //多一个为加载更多的条目
        if (list.size == 0) return 0
        return list.size + 1
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BaseListHolder {
        return if (viewType == 1) {
            //加载更多布局
            BaseListHolder(LoadMoreView(parent?.context))
        } else {
            //正常布局
            BaseListHolder(getItemView(parent?.context))
        }
    }

    class BaseListHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    /**
     * 刷新条目数据
     */
    abstract fun refreshItemView(itemView: IV, itemBean: IB)

    /**
     * 获取条目view
     */
    abstract fun getItemView(context: Context?): IV

}