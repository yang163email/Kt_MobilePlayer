package com.yan.mobile.player.utils

import com.yan.mobile.player.R
import com.yan.mobile.player.base.BaseFragment
import com.yan.mobile.player.ui.fragment.HomeFragment
import com.yan.mobile.player.ui.fragment.MvFragment
import com.yan.mobile.player.ui.fragment.VBangFragment
import com.yan.mobile.player.ui.fragment.YueDanFragment

/**
 *  @author      : 楠GG
 *  @date        : 2017/11/11 17:04
 *  @description ：首页四个Fragment的管理类，单例模式
 */
class FragmentUtil private constructor(){
    private val homeFragment by lazy { HomeFragment() }
    private val mvFragment by lazy { MvFragment() }
    private val vBangFragment by lazy { VBangFragment() }
    private val yueDanFragment by lazy { YueDanFragment() }

    companion object {
        val instance by lazy { FragmentUtil() }
    }

    fun getFragment(tabId: Int): BaseFragment? {
        return when (tabId) {
            R.id.tab_home -> homeFragment
            R.id.tab_mv -> mvFragment
            R.id.tab_vbang -> vBangFragment
            R.id.tab_yuedan -> yueDanFragment
            else -> null
        }
    }
}
