package com.yan.mobile.player.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

/**
 *  @author      : 楠GG
 *  @date        : 2017/11/11 9:47
 *  @description ：所有Activity的基类
 */
abstract class BaseActivity: AppCompatActivity(), AnkoLogger {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        initListener()
        initData()
//        debug { "haha" }
    }

    abstract fun getLayoutId(): Int

    open protected fun initListener() { }

    open protected fun initData() { }

    fun myToast(msg: String) {
        runOnUiThread {
            toast(msg)
        }
    }

    fun myToast(msg: Int) {
        runOnUiThread { toast(msg) }
    }

    inline fun <reified T: BaseActivity> startActivityAndFinish() {
        startActivity<T>()
        finish()
    }

}