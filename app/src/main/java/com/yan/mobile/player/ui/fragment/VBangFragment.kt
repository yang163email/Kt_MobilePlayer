package com.yan.mobile.player.ui.fragment

import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.widget.TextView
import com.yan.mobile.player.base.BaseFragment
import org.jetbrains.anko.textColor

/**
 *  @author      : 楠GG
 *  @date        : 2017/11/11 16:59
 *  @description ：TODO
 */
class VBangFragment : BaseFragment() {
    override fun initView(): View? {
        val tv = TextView(context)
        tv.gravity = Gravity.CENTER
        tv.text = javaClass.simpleName
        tv.textColor = Color.RED
        return tv
    }
}