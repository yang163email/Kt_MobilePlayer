package com.yan.mobile.player.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.runOnUiThread
import org.jetbrains.anko.support.v4.toast

/**
 *  @author      : 楠GG
 *  @date        : 2017/11/11 9:49
 *  @description ：所有Fragment的基类
 */
abstract class BaseFragment: Fragment(),AnkoLogger {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return initView()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initListener()
        initData()
    }

    /**
     * fragment初始化
     */
    open protected fun init() { }

    /**
     * 初始化Fragment界面
     */
    abstract fun initView(): View?

    open protected fun initListener() { }

    /**
     * 设置数据
     */
    open protected fun initData() { }

    fun myToast(msg: String) {
        context.runOnUiThread {
            toast(msg)
        }
    }

    fun myToast(msg: Int) {
        context.runOnUiThread { toast(msg) }
    }
}