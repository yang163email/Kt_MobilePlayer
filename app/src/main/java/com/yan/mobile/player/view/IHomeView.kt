package com.yan.mobile.player.view

import com.yan.mobile.player.model.HomeItemBean

/**
 *  @author      : 楠GG
 *  @date        : 2017/11/12 21:59
 *  @description ：home界面回调接口
 */
interface IHomeView {
    //获取数据失败
    fun onError(message: String?)
    //初始化数据、刷新数据成功
    fun loadSuccess(list: List<HomeItemBean>?)
    //加载更多数据成功
    fun loadMore(list: List<HomeItemBean>?)
}