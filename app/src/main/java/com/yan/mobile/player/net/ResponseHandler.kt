package com.yan.mobile.player.net

/**
 *  @author      : 楠GG
 *  @date        : 2017/11/13 13:55
 *  @description ：响应回调
 */
interface ResponseHandler<in T> {
    fun onSuccess(type: Int, result: T)
    fun onError(type: Int, msg: String?)
}