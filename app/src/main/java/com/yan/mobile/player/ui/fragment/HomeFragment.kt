package com.yan.mobile.player.ui.fragment

import android.graphics.Color
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.yan.mobile.player.R
import com.yan.mobile.player.adapter.HomeAdapter
import com.yan.mobile.player.base.BaseFragment
import com.yan.mobile.player.model.HomeItemBean
import com.yan.mobile.player.presenter.impl.HomePresenterImpl
import com.yan.mobile.player.view.IHomeView
import kotlinx.android.synthetic.main.fragment_home.*

/**
 *  @author      : 楠GG
 *  @date        : 2017/11/11 16:59
 *  @description ：首页Fragment
 */
class HomeFragment: BaseFragment(), IHomeView {

    private val mAdapter by lazy { HomeAdapter() }

    private val mPresenter by lazy { HomePresenterImpl(this) }

    override fun initView(): View? {
        return layoutInflater.inflate(R.layout.fragment_home, null)
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

    override fun loadSuccess(list: List<HomeItemBean>?) {
        refreshLayout.isRefreshing = false
        mAdapter.updateList(list)
    }

    override fun loadMore(list: List<HomeItemBean>?) {
        refreshLayout.isRefreshing = false
        mAdapter.loadMore(list)
    }

}