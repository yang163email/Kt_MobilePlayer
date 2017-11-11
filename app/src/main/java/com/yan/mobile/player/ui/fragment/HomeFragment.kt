package com.yan.mobile.player.ui.fragment

import android.graphics.Color
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.yan.mobile.player.R
import com.yan.mobile.player.adapter.HomeAdapter
import com.yan.mobile.player.base.BaseFragment
import com.yan.mobile.player.model.HomeItemBean
import com.yan.mobile.player.utils.ThreadUtil
import com.yan.mobile.player.utils.URLProviderUtils
import kotlinx.android.synthetic.main.fragment_home.*
import okhttp3.*
import java.io.IOException

/**
 *  @author      : 楠GG
 *  @date        : 2017/11/11 16:59
 *  @description ：首页Fragment
 */
class HomeFragment: BaseFragment() {
    private val mAdapter by lazy { HomeAdapter() }

    override fun initView(): View? {
        return layoutInflater.inflate(R.layout.fragment_home, null)
    }

    override fun initListener() {
        recyclerView.layoutManager = LinearLayoutManager(context)
        //适配adapter
        recyclerView.adapter = mAdapter

        refreshLayout.setColorSchemeColors(Color.RED, Color.BLUE, Color.BLACK)
        refreshLayout.setOnRefreshListener {
            loadData()
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
                            loadMore(mAdapter.itemCount - 1)
                        }
                    }
                }
            }
        })
    }

    override fun initData() {
        loadData()
    }

    /**
     * 加载网络数据
     */
    private fun loadData() {
        val homeUrl = URLProviderUtils.getHomeUrl(0, 20)
        val client = OkHttpClient()
        val request = Request.Builder()
                .url(homeUrl)
                .get()
                .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call?, e: IOException?) {
                ThreadUtil.runOnMainThread(Runnable {
                    refreshLayout.isRefreshing = false
                })
                myToast("获取数据失败")
            }

            override fun onResponse(call: Call?, response: Response?) {
                myToast("获取数据成功")
                val result = response?.body()?.string()

                //使用gson解析数据
                val gson = Gson()
                val list = gson.fromJson<List<HomeItemBean>>(result,
                        object : TypeToken<List<HomeItemBean>>() {}.type)

                //主线程中更新数据
                ThreadUtil.runOnMainThread(Runnable {
                    refreshLayout.isRefreshing = false
                    mAdapter.updateList(list)
                })
            }

        })
    }

    /**
     * 加载更多
     */
    private fun loadMore(offset: Int) {
        val homeUrl = URLProviderUtils.getHomeUrl(offset, 20)
        val client = OkHttpClient()
        val request = Request.Builder()
                .url(homeUrl)
                .get()
                .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call?, e: IOException?) {
                ThreadUtil.runOnMainThread(Runnable {
                    refreshLayout.isRefreshing = false
                })
                myToast("获取数据失败")
            }

            override fun onResponse(call: Call?, response: Response?) {
                myToast("获取数据成功")
                val result = response?.body()?.string()

                //使用gson解析数据
                val gson = Gson()
                val list = gson.fromJson<List<HomeItemBean>>(result,
                        object : TypeToken<List<HomeItemBean>>() {}.type)

                //主线程中更新数据
                ThreadUtil.runOnMainThread(Runnable {
                    refreshLayout.isRefreshing = false
                    mAdapter.loadMore(list)
                })
            }

        })
    }
}