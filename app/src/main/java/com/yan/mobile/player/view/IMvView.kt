package com.yan.mobile.player.view

import com.itheima.player.model.bean.MvAreaBean

/**
 *  @author      : 楠GG
 *  @date        : 2017/11/14 15:48
 *  @description : mv界面回调接口
 */
interface IMvView {
    fun onError(msg: String?)
    fun onSuccess(result: List<MvAreaBean>)
}