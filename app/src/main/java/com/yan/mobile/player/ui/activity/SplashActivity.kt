package com.yan.mobile.player.ui.activity

import android.support.v4.view.ViewCompat
import android.support.v4.view.ViewPropertyAnimatorListener
import android.view.View
import com.yan.mobile.player.R
import com.yan.mobile.player.base.BaseActivity
import kotlinx.android.synthetic.main.activity_splash.*

/**
 *  @author      : 楠GG
 *  @date        : 2017/11/11 9:52
 *  @description ：欢迎界面
 */
class SplashActivity: BaseActivity(), ViewPropertyAnimatorListener {
    override fun onAnimationEnd(view: View?) {
        startActivityAndFinish<MainActivity>()
    }

    override fun onAnimationCancel(view: View?) {
    }

    override fun onAnimationStart(view: View?) {
    }

    override fun getLayoutId(): Int = R.layout.activity_splash

    override fun initData() {
        ViewCompat.animate(imageView)
                .scaleX(1f)
                .scaleY(1f)
                .setListener(this)
                .duration = 2000
    }
}