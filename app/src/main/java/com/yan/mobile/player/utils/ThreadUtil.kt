package com.yan.mobile.player.utils

import android.os.Handler
import android.os.Looper

/**
 *  @author      : 楠GG
 *  @date        : 2017/11/11 20:10
 *  @description ：TODO
 */
object ThreadUtil {

    private val handler = Handler(Looper.getMainLooper())

    /**
     * 运行在主线程中
     */
    fun runOnMainThread(runnable: Runnable) {
        handler.post(runnable)
    }
}