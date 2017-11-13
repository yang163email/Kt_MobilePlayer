package com.yan.mobile.player.net

import com.google.gson.Gson
import java.lang.reflect.ParameterizedType

/**
 *  @author      : 楠GG
 *  @date        : 2017/11/13 13:54
 *  @description ：所有请求基类
 */
open class MRequest<T>(val type: Int, val url: String, val handler: ResponseHandler<T>) {
    /**
     * 将json数据解析
     */
    fun parseResult(result: String?): T {
        val gson = Gson()
        val type = (javaClass.genericSuperclass as ParameterizedType)
                .actualTypeArguments[0]
        val list = gson.fromJson<T>(result, type)

        return list
    }

    fun execute() {
        NetManager.manager.sendRequest(this)
    }

}