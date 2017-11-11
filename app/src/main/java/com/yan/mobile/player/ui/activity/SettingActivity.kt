package com.yan.mobile.player.ui.activity

import android.support.v7.widget.Toolbar
import com.yan.mobile.player.R
import com.yan.mobile.player.base.BaseActivity
import com.yan.mobile.player.utils.ToolbarManager
import org.jetbrains.anko.find

/**
 *  @author      : 楠GG
 *  @date        : 2017/11/11 15:29
 *  @description ：设置界面
 */
class SettingActivity: BaseActivity(), ToolbarManager {
    override val toolbar by lazy { find<Toolbar>(R.id.toolbar) }

    override fun getLayoutId(): Int {
        return R.layout.activity_setting
    }

    override fun initData() {
        initSettingToolbar()
    }
}