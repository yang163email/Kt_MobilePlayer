package com.yan.mobile.player.adapter

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.yan.mobile.player.ui.fragment.VideoPagerFragment

/**
 *  @author      : 楠GG
 *  @date        : 2017/11/15 16:34
 *  @description : 视频播放界面ViewPager适配器
 */
class VideoPagerAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int) = VideoPagerFragment()

    override fun getCount() = 3
}