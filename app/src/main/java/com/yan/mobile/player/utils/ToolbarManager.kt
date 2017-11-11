package com.yan.mobile.player.utils

import android.content.Intent
import android.support.v7.widget.Toolbar
import com.yan.mobile.player.R
import com.yan.mobile.player.ui.activity.SettingActivity

/**
 *  @author      : 楠GG
 *  @date        : 2017/11/11 14:59
 *  @description ：所有toolbar管理类
 */
interface ToolbarManager {
    val toolbar: Toolbar

    /**
     * 设置MainActivity的toolbar
     */
    fun initMainToolbar() {
        toolbar.title = "手机影音"
        toolbar.inflateMenu(R.menu.main)
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.setting -> {
                    toolbar.context.startActivity(Intent(toolbar.context, SettingActivity::class.java))
                }
            }
            true
        }
    }

    /**
     * 设置设置界面的toolbar
     */
    fun initSettingToolbar() {
        toolbar.title = "设置界面"
    }

}