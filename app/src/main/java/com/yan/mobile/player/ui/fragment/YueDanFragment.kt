package com.yan.mobile.player.ui.fragment

import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.widget.TextView
import com.yan.mobile.player.base.BaseFragment

/**
 *  @author      : 楠GG
 *  @date        : 2017/11/11 16:59
 *  @description ：TODO
 */
class YueDanFragment : BaseFragment() {
    override fun initView(): View? {
        val tv = TextView(context)
        tv.gravity = Gravity.CENTER
        tv.text = javaClass.simpleName
        tv.setTextColor(Color.RED)
        return tv
    }
}