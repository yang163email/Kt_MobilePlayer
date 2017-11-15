package com.yan.mobile.player.adapter

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.itheima.player.model.bean.MvAreaBean
import com.yan.mobile.player.ui.fragment.MvPagerFragment

/**
 *  @author      : 楠GG
 *  @date        : 2017/11/14 16:11
 *  @description : mvPager适配器
 */
class MvPagerAdapter(val context: Context, fm: FragmentManager, var list: List<MvAreaBean>?): FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
//        val fragment = MvPagerFragment()
        val bundle = Bundle()
        bundle.putString("args", list?.get(position)?.code)
//        fragment.arguments = bundle
        val fragment = Fragment.instantiate(context, MvPagerFragment::class.java.name, bundle)
        return fragment
    }

    override fun getCount(): Int {
        return list?.size?:0
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return list?.get(position)?.name
    }
}