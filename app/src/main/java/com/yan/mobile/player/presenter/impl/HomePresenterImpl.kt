package com.yan.mobile.player.presenter.impl

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.yan.mobile.player.model.HomeItemBean
import com.yan.mobile.player.presenter.interf.HomePresenter
import com.yan.mobile.player.utils.ThreadUtil
import com.yan.mobile.player.utils.URLProviderUtils
import com.yan.mobile.player.view.IHomeView
import okhttp3.*
import java.io.IOException

/**
 *  @author      : 楠GG
 *  @date        : 2017/11/12 22:02
 *  @description ：HomePresenter实现类
 */
class HomePresenterImpl(var homeView: IHomeView): HomePresenter {
    /**
     * 初始化数据、刷新数据
     */
    override fun loadData() {
        val homeUrl = URLProviderUtils.getHomeUrl(0, 20)
        val client = OkHttpClient()
        val request = Request.Builder()
                .url(homeUrl)
                .get()
                .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call?, e: IOException?) {
                ThreadUtil.runOnMainThread(Runnable {
                    homeView.onError(e?.message)
                })
            }

            override fun onResponse(call: Call?, response: Response?) {
                val result = response?.body()?.string()

                //使用gson解析数据
                val gson = Gson()
                val list = gson.fromJson<List<HomeItemBean>>(result,
                        object : TypeToken<List<HomeItemBean>>() {}.type)

                //主线程中更新数据
                ThreadUtil.runOnMainThread(Runnable {
                    homeView.loadSuccess(list)
                })
            }
        })
    }

    override fun loadMore(offset: Int) {
        val homeUrl = URLProviderUtils.getHomeUrl(offset, 20)
        val client = OkHttpClient()
        val request = Request.Builder()
                .url(homeUrl)
                .get()
                .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call?, e: IOException?) {
                ThreadUtil.runOnMainThread(Runnable {
                    homeView.onError(e?.message)
                })
            }

            override fun onResponse(call: Call?, response: Response?) {
                val result = response?.body()?.string()

                //使用gson解析数据
                val gson = Gson()
                val list = gson.fromJson<List<HomeItemBean>>(result,
                        object : TypeToken<List<HomeItemBean>>() {}.type)

                //主线程中更新数据
                ThreadUtil.runOnMainThread(Runnable {
                    homeView.loadMore(list)
                })
            }
        })
    }


}