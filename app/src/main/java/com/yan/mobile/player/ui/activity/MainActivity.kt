package com.yan.mobile.player.ui.activity

import android.support.v7.widget.Toolbar
import com.yan.mobile.player.R
import com.yan.mobile.player.base.BaseActivity
import com.yan.mobile.player.utils.FragmentUtil
import com.yan.mobile.player.utils.ToolbarManager
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.find

/**
 *  @author      : 楠GG
 *  @date        : 2017/11/11 15:44
 *  @description ：主界面
 */
class MainActivity : BaseActivity(), ToolbarManager {
    override val toolbar by lazy { find<Toolbar>(R.id.toolbar) }

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun initListener() {
        bottomBar.setOnTabSelectListener {
            val fragment = FragmentUtil.instance.getFragment(it)
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.container, fragment, it.toString())
            transaction.commit()
        }
    }

    override fun initData() {
        initMainToolbar()
    }

}
