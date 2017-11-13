package com.yan.mobile.player.net

import com.yan.mobile.player.utils.ThreadUtil
import okhttp3.*
import java.io.IOException

/**
 *  @author      : 楠GG
 *  @date        : 2017/11/13 13:57
 *  @description ：发送网络请求类
 */
class NetManager private constructor() {
    private val client by lazy { OkHttpClient() }

    companion object {
        val manager by lazy { NetManager() }
    }

    /**
     * 发送请求
     */
    fun <T> sendRequest(req: MRequest<T>) {
        val request = Request.Builder()
                .url(req.url)
                .get()
                .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call?, e: IOException?) {
                ThreadUtil.runOnMainThread(Runnable {
                    //错误回调
                    req.handler.onError(req.type, e?.message)
                })
            }

            override fun onResponse(call: Call?, response: Response?) {
                val result = response?.body()?.string()

                //使用gson解析数据
                val list = req.parseResult(result)

                //主线程中更新数据
                ThreadUtil.runOnMainThread(Runnable {
                    req.handler.onSuccess(req.type, list)
                })
            }
        })
    }
}