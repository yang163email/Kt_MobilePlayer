package com.yan.mobile.player.view

import com.itheima.player.model.bean.YueDanBean

/**
 *  @author      : 楠GG
 *  @date        : 2017/11/13 15:35
 *  @description ：悦单界面回调接口
 */
interface IYueDanView {
    //获取数据失败
    fun onError(message: String?)
    //初始化数据、刷新数据成功
    fun loadSuccess(response: YueDanBean?)
    //加载更多数据成功
    fun loadMore(response: YueDanBean?)
}