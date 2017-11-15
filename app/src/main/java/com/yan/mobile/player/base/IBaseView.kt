package com.yan.mobile.player.base

/**
 *  @author      : 楠GG
 *  @date        : 2017/11/14 14:10
 *  @description : 所有带下拉刷新，上拉加载更多view的接口
 */
interface IBaseView<RP> {
    //获取数据失败
    fun onError(message: String?)
    //初始化数据、刷新数据成功
    fun loadSuccess(response: RP?)
    //加载更多数据成功
    fun loadMore(response: RP?)
}