package com.yan.mobile.player.ui.fragment

import android.graphics.Color
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.itheima.player.model.bean.YueDanBean
import com.yan.mobile.player.R
import com.yan.mobile.player.adapter.YueDanAdapter
import com.yan.mobile.player.base.BaseFragment
import com.yan.mobile.player.presenter.impl.YueDanPresenterImpl
import com.yan.mobile.player.view.IYueDanView
import kotlinx.android.synthetic.main.fragment_list.*

/**
 *  @author      : 楠GG
 *  @date        : 2017/11/11 16:59
 *  @description ：悦单界面
 */
class YueDanFragment : BaseFragment(), IYueDanView {

    private val mAdapter by lazy { YueDanAdapter() }
    private val mPresenter by lazy { YueDanPresenterImpl(this) }

    override fun initView(): View? {
        return layoutInflater.inflate(R.layout.fragment_list, null)
    }

    override fun initListener() {
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = mAdapter

        refreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLACK)
        refreshLayout.setOnRefreshListener {
            mPresenter.loadData()
        }
        //监听加载更多
        recyclerView.addOnScrollListener(object :RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                val layoutManager = recyclerView?.layoutManager
                if (layoutManager is LinearLayoutManager) {
                    val lastPosition = layoutManager.findLastVisibleItemPosition()
                    if (lastPosition == mAdapter.itemCount - 1){
                        //加载更多数据
                        mPresenter.loadMore(mAdapter.itemCount - 1)
                    }
                }
            }
        })
    }

    override fun initData() {
        mPresenter.loadData()
    }

    override fun onError(message: String?) {
        refreshLayout.isRefreshing = false
        myToast("加载数据失败")
    }

    override fun loadSuccess(response: YueDanBean?) {
        refreshLayout.isRefreshing = false
        mAdapter.updateList(response?.playLists)
    }

    override fun loadMore(response: YueDanBean?) {
        mAdapter.loadMore(response?.playLists)
    }
}