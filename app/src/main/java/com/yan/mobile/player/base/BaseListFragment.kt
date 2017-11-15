package com.yan.mobile.player.base

import android.graphics.Color
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.yan.mobile.player.R
import kotlinx.android.synthetic.main.fragment_list.*

/**
 *  @author      : 楠GG
 *  @date        : 2017/11/14 14:00
 *  @description ：所有带有下拉刷新、上拉加载更多列表的Fragment
 *
 *  View: 对应页面回调接口
 *  Adapter：
 *  Presenter：
 */
abstract class BaseListFragment<RP, IB, IV : View> : BaseFragment(), IBaseView<RP> {

    protected val mAdapter by lazy { getAdapter() }

    protected val mPresenter by lazy { getPresenter() }

    override fun initView(): View? {
        return layoutInflater.inflate(R.layout.fragment_list, null)
    }

    override fun initListener() {
        recyclerView.layoutManager = LinearLayoutManager(context)
        //适配adapter
        recyclerView.adapter = mAdapter

        refreshLayout.setColorSchemeColors(Color.RED, Color.BLUE, Color.BLACK)
        refreshLayout.setOnRefreshListener {
            mPresenter.loadData()
        }

        recyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //如果状态为闲置状态则往下一步执行
                    val layoutManager = recyclerView.layoutManager
                    if (layoutManager is LinearLayoutManager) {
                        //找到最后一个可见条目
                        val lastPosition = layoutManager.findLastVisibleItemPosition()
                        if (lastPosition == mAdapter.itemCount - 1) {
                            //加载更多数据
                            mPresenter.loadMore(mAdapter.itemCount - 1)
                        }
                    }
                }
            }
        })
    }

    override fun initData() {
        mPresenter.loadData()
    }

    override fun onError(message: String?) {
        myToast("加载数据失败")
    }

    override fun loadSuccess(list: RP?) {
        refreshLayout.isRefreshing = false
        mAdapter.updateList(getList(list))
    }

    override fun loadMore(list: RP?) {
        mAdapter.loadMore(getList(list))
    }

    /**
     * 获取适配器adapter
     */
    abstract fun getAdapter(): BaseListAdapter<IB, IV>

    /**
     * 获取presenter
     */
    abstract fun getPresenter(): BaseListPresenter

    /**
     * 从返回结果中获取列表数据集合
     */
    abstract fun getList(response: RP?): List<IB>?

}